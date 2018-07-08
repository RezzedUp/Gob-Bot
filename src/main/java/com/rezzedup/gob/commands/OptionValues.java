package com.rezzedup.gob.commands;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class OptionValues
{
    public static OptionValues EMPTY = new OptionValues(Collections.emptyMap());
    
    private final Map<OptionDefinition, Argument> settings;
    
    public OptionValues(Map<OptionDefinition, Argument> settings)
    {
        this.settings = Objects.requireNonNull(settings, "Settings are null.");
    }
    
    private Optional<OptionDefinition> getOptionByName(String name)
    {
        String option = Objects.requireNonNull(name, "Name is null.").toLowerCase();
        return settings.keySet().stream().filter(def -> def.getAliases().contains(option)).findFirst();
    }
    
    private Optional<OptionDefinition> getOptionByFlaggedName(String name, Flag mode)
    {
        return getOptionByName(name).filter(def -> def.getMode() == mode);
    }
    
    public boolean hasOption(String option)
    {
        return getOptionByName(option).isPresent();
    }
    
    public boolean hasQuery(String option)
    {
        return getOptionByFlaggedName(option, Flag.QUERY).isPresent();
    }
    
    public boolean getToggle(String option, boolean defaultState)
    {
        return getOptionByFlaggedName(option, Flag.TOGGLE).map(settings::get).map(Argument::asBoolean).orElse(defaultState);
    }
    
    public Argument getSetting(String option)
    {
        return getOptionByFlaggedName(option, Flag.SETTING).map(settings::get).orElse(Argument.EMPTY);
    }
}
