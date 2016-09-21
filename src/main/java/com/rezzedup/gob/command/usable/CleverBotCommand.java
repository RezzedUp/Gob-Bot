package com.rezzedup.gob.command.usable;

import com.rezzedup.gob.command.Command;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

public class CleverBotCommand extends Command
{
    public CleverBotCommand(IDiscordClient client)
    {
        super(client, new String[]{"clever", ":cl:"});
    }
    
    @Override
    public void execute(String[] args, IMessage message)
    {
        
    }
}
