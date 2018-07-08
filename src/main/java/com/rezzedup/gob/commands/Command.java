package com.rezzedup.gob.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Command implements Executable
{
    protected final List<String> aliases;
    protected String description = "";
    
    public Command(String ... aliases)
    {
        if (aliases == null || aliases.length <= 0)
        {
            throw new IllegalArgumentException("Invalid aliases: empty or null");
        }
        
        this.aliases = Arrays.stream(aliases).map(String::toLowerCase).collect(Collectors.toList());
    }
    
    public final String getName()
    {
        return aliases.get(0);
    }
    
    public final List<String> getAliases()
    {
        return aliases;
    }
    
    protected final void setDescription(String description)
    {
        this.description = description;
    }
    
    public final String getDescription()
    {
        return description;
    }
}
