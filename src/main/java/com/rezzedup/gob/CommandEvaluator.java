package com.rezzedup.gob;

import com.rezzedup.gob.command.CommandParser;
import com.rezzedup.gob.util.Text;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

public class CommandEvaluator
{
    private final CommandParser parser = new CommandParser();
    private final String id;
    private final String mention;
    
    public CommandEvaluator(JDA jda)
    {
        this.id = jda.getSelfUser().getId();
        this.mention = "<@" + this.id + ">";
    }
    
    public CommandParser getCommandParser()
    {
        return parser;
    }
    
    public void evaluate(Message message)
    {
        String msg = message.getContent();
        
        if (isSelfInvoke(message))
        {
            return;
        }
        
        if (!message.isFromType(ChannelType.PRIVATE))
        {
            for (String identifier : Gob.IDENTIFIERS)
            {
                if (identifier.length() >= msg.length())
                {
                    continue;
                }
        
                if (msg.substring(0, identifier.length()).equalsIgnoreCase(identifier))
                {
                    command(msg.substring(identifier.length()), message);
                    return;
                }
            }
    
            if (msg.startsWith(mention))
            {
                command(msg.substring(mention.length()), message);
            }
        }
        else 
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
    
    // If true, avoid evaluating the command.
    private boolean isSelfInvoke(Message message)
    {
        return (message.getAuthor().getId().equalsIgnoreCase(id));
    }
}
