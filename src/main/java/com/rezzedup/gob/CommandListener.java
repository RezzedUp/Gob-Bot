package com.rezzedup.gob;

import com.rezzedup.gob.command.CommandParser;
import com.rezzedup.gob.util.Text;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class CommandListener
{
    private final IDiscordClient client;
    private final CommandParser parser;
    
    public CommandListener(IDiscordClient client)
    {
        this.client = client;
        this.parser = new CommandParser();
        
        client.getDispatcher().registerListener(this);
    }
    
    public CommandParser getCommandParser()
    {
        return parser;
    }
    
    @EventSubscriber
    public void onMessage(MessageReceivedEvent event)
    {
        IMessage message = event.getMessage();
        String msg = message.getContent();
        
        if (avoidSelfInvoke(message))
        {
            return;
        }
        
        if (!message.getChannel().isPrivate())
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
    
            String mention = "<@" + client.getOurUser().getID() + ">";
            
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
    
    private void command(String content, IMessage message)
    {
        String command = Text.stripWhitespace(content);
        
        log(message, command);
        parser.parse(command, message).execute();
    }
    
    private void log(IMessage message, String content)
    {
        IUser user = message.getAuthor();
    
        Gob.status(String.format
        (
            "%s#%s in %s sent: %s",
            user.getName(), user.getDiscriminator(), Text.formatGuildChannel(message), content
        ));
    }
    
    private boolean avoidSelfInvoke(IMessage message)
    {
        return (message.getAuthor().getID().equalsIgnoreCase(client.getOurUser().getID()));
    }
}
