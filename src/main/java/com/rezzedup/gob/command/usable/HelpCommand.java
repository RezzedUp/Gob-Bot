package com.rezzedup.gob.command.usable;

import com.rezzedup.gob.command.Command;
import com.rezzedup.gob.command.CommandParser;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class HelpCommand extends Command
{
    private final CommandParser parser;
    
    public HelpCommand(IDiscordClient client, CommandParser parser)
    {                                        // :question:
        super(client, new String[]{"help", "?", "\u2753"});
        this.parser = parser;
    }
    
    @Override
    public void execute(String[] args, IMessage message)
    {
        IChannel channel = message.getChannel();
        
        try
        {
            channel.sendMessage("I can't do anything yet...");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
