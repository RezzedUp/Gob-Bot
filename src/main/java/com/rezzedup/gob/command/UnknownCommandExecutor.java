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
        
    }
}
