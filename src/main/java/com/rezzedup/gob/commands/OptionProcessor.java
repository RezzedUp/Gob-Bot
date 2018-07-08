package com.rezzedup.gob.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class OptionProcessor
{
    public static Builder using(OptionDefinition definition)
    {
        return new Builder().and(definition);
    }
    
    private final Set<OptionDefinition> definitions;
    
    private OptionProcessor(Set<OptionDefinition> definitions)
    {
        this.definitions = definitions;
    }
    
    public Optional<OptionDefinition> getOptionDefinition(String option)
    {
        String lowerInput = Objects.requireNonNull(option, "Option is null.");
        return definitions.stream().filter(def -> def.getAliases().contains(lowerInput)).findFirst();
    }
    
    public OptionValues processOptions(CommandSubmission command) throws IllegalStateException
    {
        return processOptions(command.args);
    }
    
    public OptionValues processOptions(ArgumentStack args) throws IllegalStateException
    {
        Objects.requireNonNull(args, "Argument stack is null.");
        
        Map<OptionDefinition, Argument> settings = new HashMap<>();
    
        if (!args.hasNext()) { return OptionValues.EMPTY; }
    
        while (args.peek().asText().startsWith("-"))
        {
            Argument optionArgument = args.next();
            Optional<OptionDefinition> possibleDefinition = getOptionDefinition(optionArgument.asText());
        
            if (!possibleDefinition.isPresent())
            {
                throw new IllegalStateException("Unknown option: " + optionArgument);
            }
        
            OptionDefinition definition = possibleDefinition.get();
        
            if (definition.getMode() == Flag.QUERY)
            {
                settings.put(definition, Argument.EMPTY);
                continue;
            }
        
            Argument parameter = args.next();
        
            if (!parameter.exists())
            {
                throw new IllegalStateException("Missing required value for option: " + optionArgument);
            }
        
            if (definition.getMode() == Flag.TOGGLE && !parameter.isBoolean())
            {
                throw new IllegalStateException(
                    "Incorrect value for toggleable option " + optionArgument + ": '" + parameter + "' (expected true or false)"
                );
            }
        
            settings.put(definition, parameter);
        }
        
        return new OptionValues(settings);
    }
    
    public static class Builder
    {
        private final Set<OptionDefinition> options = new HashSet<>();
        
        private Builder() {}
        
        public Builder and(OptionDefinition definition)
        {
            options.add(Objects.requireNonNull(definition, "Definition is null."));
            return this;
        }
        
        public OptionProcessor build()
        {
            return new OptionProcessor(options);
        }
    }
}
