package com.rezzedup.gob.commands;

public enum Flag
{
    /**
     * Either the flag exists or it doesn't.
     */
    QUERY,
    
    /**
     * A boolean toggle.
     */
    TOGGLE,
    
    /**
     * Define a setting as an argument's value.
     */
    SETTING;
    
    public boolean isArgumentRequired()
    {
        return this.ordinal() > QUERY.ordinal();
    }
    
    public OptionDefinition called(String ... names)
    {
        return new OptionDefinition(this, names);
    }
}
