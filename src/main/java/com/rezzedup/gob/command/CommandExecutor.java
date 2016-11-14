package com.rezzedup.gob.command;

import net.dv8tion.jda.core.entities.Message;

public class CommandExecutor implements Executable
{
    private final Command command;
    private final String[] args;
    private final Message message;
    
    public CommandExecutor(Command command, String[] args, Message message)
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
