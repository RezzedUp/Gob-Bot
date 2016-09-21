package com.rezzedup.gob.command;

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
                message.getChannel().sendMessage("I don't understand that command. \uD83D\uDE22");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
