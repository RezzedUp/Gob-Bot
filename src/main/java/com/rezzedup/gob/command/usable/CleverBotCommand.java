package com.rezzedup.gob.command.usable;

import com.rezzedup.gob.command.Command;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

public class CleverBotCommand extends Command
{
    public CleverBotCommand(IDiscordClient client)
    {                                                   // :cl:            :robot:
        super(client, new String[]{"clever", "cleverbot", "\uD83C\uDD91", "\uD83E\uDD16"});
    }
    
    @Override
    public void execute(String[] args, IMessage message)
    {
        
    }
}
