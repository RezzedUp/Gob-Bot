package com.rezzedup.gob;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;

public class UnknownCommandExecutor implements Executable
{
    private final Message message;
    
    public UnknownCommandExecutor(Message message)
    {
        this.message = message;
    }
    
    @Override
    public void execute()
    {
        if (message.isFromType(ChannelType.PRIVATE))
        {
            try
            {
                message.getChannel().sendMessage("I don't understand that command. " + Emoji.CRY).queue();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
