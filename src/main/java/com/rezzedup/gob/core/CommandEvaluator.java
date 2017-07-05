package com.rezzedup.gob.core;

import com.rezzedup.gob.Gob;
import com.rezzedup.gob.util.Text;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandEvaluator
{
    private final CommandParser parser = new CommandParser();
    private final String id;
    private final String mention;
    
    public CommandEvaluator(JDA jda)
    {
        this.id = jda.getSelfUser().getId();
        this.mention = jda.getSelfUser().getAsMention();
    }
    
    public CommandParser getCommandParser()
    {
        return parser;
    }
    
    public void evaluate(Message message)
    {
        String msg = message.getContent();
        
        if (message.getAuthor().isBot())
        {
            return;
        }
        
        for (String identifier : Gob.IDENTIFIERS)
        {
            if (msg.startsWith(identifier))
            {
                command(msg.substring(identifier.length()), message);
                return;
            }
        }
        
        if (msg.startsWith(mention))
        {
            command(msg.substring(mention.length()), message);
        }
        
        if (message.isFromType(ChannelType.PRIVATE))
        {
            command(msg, message);
        }
    }
    
    private void command(String content, Message message)
    {
        String command = content.trim();
        log(message, command);
        parser.parse(command, message).execute();
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
    
    public static class CommandParser
    {
        // First alias -> Command
        private final Map<String, Command> commands = new LinkedHashMap<>();
        
        // First alias -> All aliases
        private final Map<String, String[]> aliases = new LinkedHashMap<>();
        
        public Executable parse(String command, Message message)
        {
            String[] parts = command.split(" ");
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);
            
            if (commands.containsKey(parts[0]))
            {
                return new CommandExecutor(commands.get(parts[0]), args, message);
            }
            
            for (String cmd : commands.keySet())
            {
                boolean root = true;
                
                for (String alias : aliases.get(cmd))
                {
                    // Already checked first alias: avoids rechecking.
                    if (root)
                    {
                        root = false;
                        continue;
                    }
                    
                    if (parts[0].equalsIgnoreCase(alias))
                    {
                        return new CommandExecutor(commands.get(cmd), args, message);
                    }
                }
            }
            
            return new UnknownCommandExecutor(message);
        }
        
        public void register(Command command)
        {
            String cmd = command.getAliases()[0];
            
            commands.put(cmd, command);
            aliases.put(cmd, command.getAliases());
        }
        
        public Collection<Command> getRegisteredCommands()
        {
            return commands.values();
        }
    }
}
