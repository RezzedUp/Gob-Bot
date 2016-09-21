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
        String send;
        
        if (message.getChannel().isPrivate())
        {
            send = "I don't understand that command. \uD83D\uDE22";
        }
        else
        {
            send = "I'm not sure what that means " + message.getAuthor().mention();
        }
    
        try
        {
            message.getChannel().sendMessage(send);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
