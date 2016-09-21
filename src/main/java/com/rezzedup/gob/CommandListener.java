package com.rezzedup.gob;

import com.rezzedup.gob.command.CommandParser;
import com.rezzedup.gob.util.Text;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;

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
        
        //Gob.status(String.format("Recieved \"%s\" from %s in %s", msg, message.getAuthor().getName(), message.getChannel().getName()));
        
        try
        {
            if (message.getAuthor().getID().equalsIgnoreCase(client.getApplicationClientID()))
            {
                return;
            }
        }
        catch (DiscordException e)
        {
            e.printStackTrace();
            return;
        }
        
        for (String identifier : Gob.IDENTIFIERS)
        {
            if (identifier.length() >= msg.length())
            {
                continue;
            }
            
            if (msg.substring(0, identifier.length()).equalsIgnoreCase(identifier))
            {
                String command = msg.substring(identifier.length());
                parser.parse(Text.stripWhitespace(command), message).execute();
            }
        }
    }
}
