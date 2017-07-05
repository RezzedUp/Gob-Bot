package com.rezzedup.gob.core;

import com.rezzedup.gob.Emoji;
import net.dv8tion.jda.core.entities.ChannelType;

public class UnknownCommand extends Command
{
    public UnknownCommand()
    {
        super("");
    }
    
    @Override
    public void execute(Context context)
    {
        if (context.message.isFromType(ChannelType.PRIVATE))
        {
            context.message.getChannel().sendMessage("I don't understand that command. " + Emoji.CRY).queue();
        }
    }
}
