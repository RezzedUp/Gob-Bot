package com.rezzedup.gob.command;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

public abstract class Command
{
    protected final String[] aliases;
    protected final IDiscordClient client;
    protected String descrption = "";
    
    public Command(IDiscordClient client, String[] aliases)
    {
        this.client = client;
        this.aliases = aliases;
    }
    
    public final String[] getAliases()
    {
        return aliases;
    }
    
    protected final void setDescrption(String descrption)
    {
        this.descrption = descrption;
    }
    
    public final String getDescrption()
    {
        return descrption;
    }
    
    public abstract void execute(String[] args, IMessage message);
}
