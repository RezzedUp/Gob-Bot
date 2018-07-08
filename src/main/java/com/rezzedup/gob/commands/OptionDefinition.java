package com.rezzedup.gob.commands;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class OptionDefinition
{
    public static final Pattern OPTION_PATTERN = Pattern.compile("^(-[^-]|--.+)$");
    
    private final Flag mode;
    private final Set<String> aliases = new LinkedHashSet<>();
    
    public OptionDefinition(Flag mode, String ... names)
    {
        this.mode = Objects.requireNonNull(mode, "Flag mode is null.");
        Objects.requireNonNull(names, "Names array is null.");
        
        for (int i = 0; i < names.length; i++)
        {
            String alias = Objects.requireNonNull(names[i], "Name @ index: " + i + " is null.").toLowerCase();
            
            if (!OPTION_PATTERN.matcher(alias).matches())
            {
                throw new IllegalArgumentException(
                    "Name @ index: " + i + " does not match the required pattern " + OPTION_PATTERN + " for options: '" + alias + "'"
                );
            }
            
            aliases.add(alias);
        }
    }
    
    public Flag getMode()
    {
        return mode;
    }
    
    public Set<String> getAliases()
    {
        return Collections.unmodifiableSet(aliases);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof OptionDefinition)
        {
            OptionDefinition other = (OptionDefinition) obj;
            return Objects.equals(mode, other.mode) && Objects.equals(aliases, other.aliases);
        }
        return false;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(mode, aliases);
    }
}
