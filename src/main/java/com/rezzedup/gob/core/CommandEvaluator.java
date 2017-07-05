package com.rezzedup.gob.core;

import com.rezzedup.gob.Gob;
import com.rezzedup.gob.util.Text;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandEvaluator
{
    private final CommandRegistry registry = new CommandRegistry();
    private final String id;
    private final String mention;
    
    public CommandEvaluator(JDA jda)
    {
        this.id = jda.getSelfUser().getId();
        this.mention = jda.getSelfUser().getAsMention();
    }
    
    public CommandRegistry getCommandParser()
    {
        return registry;
    }
    
    public void evaluate(Message message)
    {
        final String content = message.getContent();
        
        if (message.getAuthor().isBot())
        {
            return;
        }
        
        for (String identifier : Gob.IDENTIFIERS)
        {
            if (content.startsWith(identifier))
            {
                command(message, content.substring(identifier.length()));
                return;
            }
        }
        
        if (content.startsWith(mention))
        {
            command(message, content.substring(mention.length()));
        }
        
        if (message.isFromType(ChannelType.PRIVATE))
        {
            command(message, content);
        }
    }
    
    private void command(Message message, String content)
    {
        String command = content.trim();
        
        log(message, command);
    
        List<String> parts = Arrays.asList(command.split(" "));
    
        String name = parts.get(0);
    
        List<String> args = (parts.size() > 1)
            ? Collections.unmodifiableList(parts.subList(1, parts.size()))
            : Collections.emptyList();
        
        registry.getCommand(command).execute(new Context(message, name, args));
    }
    
    private void log(Message message, String content)
    {
        User user = message.getAuthor();
        Gob.status(String.format
        (
            "%s#%s in %s sent: %s",
            user.getName(), user.getDiscriminator(), Text.formatGuildChannel(message), content
        ));
    }
    
    public static class CommandRegistry
    {
        private final UnknownCommand unknown = new UnknownCommand();
        
        // Name (first alias) -> Command
        private final Map<String, Command> commands = new LinkedHashMap<>();
        
        // Alias -> Command
        private final Map<String, Command> aliases = new LinkedHashMap<>();
        
        public Command getCommand(String command)
        {
            Command cmd = aliases.get(command.toLowerCase());
            return (cmd != null) ? cmd : unknown;
        }
        
        public void register(Command command)
        {
            String name = command.getName().toLowerCase();
            
            if (commands.containsKey(name))
            {
                throw new IllegalStateException("A command by the name '" + name + "' already exists.");
            }
            
            commands.put(name, command);
            
            for (String alias : command.getAliases())
            {
                aliases.put(alias.toLowerCase(), command);
            }
        }
        
        public Collection<Command> getRegisteredCommands()
        {
            return commands.values();
        }
    }
}
