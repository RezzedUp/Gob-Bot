package com.rezzedup.gob.command.usable;

import com.rezzedup.gob.command.Command;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

public class YouTemporaryCommand extends Command
{
    public YouTemporaryCommand(IDiscordClient client)
    {
        super(client, new String[]{"you", "you're"});
    }
    
    @Override
    public void execute(String[] args, IMessage message)
    {
        try
        {
            message.getChannel().sendMessage("\uD83D\uDE18");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
