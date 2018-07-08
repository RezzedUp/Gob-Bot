package com.rezzedup.gob.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArgumentStack implements Iterator<Argument>
{
    private final List<Argument> arguments = new ArrayList<>();
    
    private int position = -1;
    
    public ArgumentStack(List<String> args)
    {
        int total = Objects.requireNonNull(args, "Arguments are null.").size();
        
        for (int index = 0; index < total; index++)
        {
            arguments.add(new Argument(args.get(index), index));
        }
    }
    
    public boolean isEmpty()
    {
        return arguments.isEmpty();
    }
    
    public int size()
    {
        return arguments.size();
    }
    
    public Argument get(int index)
    {
        return (index >= 0 && index < arguments.size()) ? arguments.get(index) : Argument.EMPTY;
    }
    
    public int getPosition()
    {
        return position;
    }
    
    public void resetPosition()
    {
        position = -1;
    }
    
    @Override
    public boolean hasNext()
    {
        return position < (arguments.size() - 1);
    }
    
    @Override
    public Argument next()
    {
        if (hasNext())
        {
            position += 1;
            return arguments.get(position);
        }
        return Argument.EMPTY;
    }
    
    public Argument previous()
    {
        if (position > 0)
        {
            position -= 1;
            return arguments.get(position);
        }
        return Argument.EMPTY;
    }
    
    public Argument current()
    {
        return (position >= 0 && position < arguments.size()) ? arguments.get(position) : Argument.EMPTY;
    }
    
    public Argument peek()
    {
        if (hasNext())
        {
            return arguments.get(position + 1);
        }
        return Argument.EMPTY;
    }
    
    public List<Argument> collectRemaining()
    {
        List<Argument> args = new ArrayList<>();
        while (hasNext()) { args.add(next()); }
        return args;
    }
    
    public List<String> collectRemainingAsText()
    {
        return collectRemaining().stream().map(Argument::asText).collect(Collectors.toList());
    }
    
    public String joinRemaining()
    {
        return joinRemaining(" ");
    }
    
    public String joinRemaining(String delimiter)
    {
        return String.join(" ", collectRemainingAsText());
    }
    
    @Override
    public String toString()
    {
        return arguments.stream().map(Argument::asText).collect(Collectors.joining(" "));
    }
}
