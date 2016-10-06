package com.rezzedup.gob.command;

import com.rezzedup.gob.Emoji;
import sx.blah.discord.handle.obj.IMessage;

public class UnknownCommandExecutor implements Executable
{
    private final IMessage message;
    
    public UnknownCommandExecutor(IMessage message)
    {
        this.message = message;
    }
    
    @Override
    public void execute()
    {
        if (message.getChannel().isPrivate())
        {
            try
            {
                message.getChannel().sendMessage("I don't understand that command. " + Emoji.CRY);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
