package com.rezzedup.gob;

import net.dv8tion.jda.core.entities.Message;

public abstract class Command
{
    protected final String[] aliases;
    protected String descrption = "";
    
    public Command(String[] aliases)
    {
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
    
    public abstract void execute(String[] args, Message message);
}
