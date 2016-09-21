package com.rezzedup.gob.command;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

public abstract class Command
{
    protected final String[] aliases;
    protected final IDiscordClient client;
    
    public Command(IDiscordClient client, String[] aliases) 
    {
        this.client = client;
        this.aliases = aliases;
    }
    
    public String[] getAliases()
    {
        return aliases;
    }
    
    public abstract void execute(String[] args, IMessage message);
}
