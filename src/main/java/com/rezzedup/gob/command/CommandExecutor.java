package com.rezzedup.gob.command;

import sx.blah.discord.handle.obj.IMessage;

public class CommandExecutor implements Executable
{
    private final Command command;
    private final String[] args;
    private final IMessage message;
    
    public CommandExecutor(Command command, String[] args, IMessage message)
    {
        this.command = command;
        this.args = args;
        this.message = message;
    }
    
    @Override
    public void execute()
    {
        command.execute(args, message);
    }
}
