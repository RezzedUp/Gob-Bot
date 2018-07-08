package com.rezzedup.gob.commands;

import com.rezzedup.gob.util.functional.CheckedFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class Argument
{
    public static final Argument EMPTY = new Argument();
    
    private final Map<Class, Object> memoization = new HashMap<>();
    private final String text;
    private final int index;
    
    /**
     * Empty Argument constructor.
     */
    private Argument()
    {
        this.text = "<none>";
        this.index = -1;
    }
    
    protected Argument(String text, int index) throws NullPointerException
    {
        this.text = Objects.requireNonNull(text, "Argument text at index " + index + " is null.");
        this.index = index;
    }
    
    public String asText()
    {
        return text;
    }
    
    public int getIndex()
    {
        return index;
    }
    
    public boolean exists()
    {
        return index > -1;
    }
    
    /**
     * Map this argument to a value if {@link #exists()} returns true.
     * @see Optional#map(Function)
     */
    public <T> Optional<T> map(Function<String, T> mapper)
    {
        Objects.requireNonNull(mapper, "Mapper is null.");
        return (exists()) ? Optional.ofNullable(mapper.apply(text)) : Optional.empty();
    }
    
    /**
     * Flatly map this argument to a value if {@link #exists()} returns true.
     * @see Optional#flatMap(Function)
     */
    public <T> Optional<T> flatMap(Function<String, Optional<T>> mapper)
    {
        Objects.requireNonNull(mapper, "Mapper is null.");
        return (exists())
            ? Objects.requireNonNull(mapper.apply(text), "Mapper returned null: expected instance of Optional.")
            : Optional.empty();
    }
    
    private <T> T store(Class<T> type, T object)
    {
        if (object != null)
        {
            memoization.put(type, object);
        }
        return object;
    }
    
    @SuppressWarnings("unchecked")
    private <T> Optional<T> get(Class<T> type)
    {
        Object stored = memoization.get(type);
        return (stored != null) ? Optional.of((T) stored) : Optional.empty();
    }
    
    private <T> Optional<T> getOrParse(Class<T> type, Supplier<Optional<T>> parser)
    {
        Optional<T> existing = get(type);
        
        if (existing.isPresent())
        {
            return existing;
        }
        
        Optional<T> result = parser.get();
        result.ifPresent(value -> store(type, value));
        return result;
    }
    
    private <N extends Number> Optional<N> getOrParseNumber(Class<N> type, CheckedFunction<String, N, NumberFormatException> parser)
    {
        return getOrParse(type, () ->
        {
            try
            {
                return Optional.of(parser.apply(text));
            }
            catch (NumberFormatException ignored)
            {
                return Optional.empty();
            }
        });
    }
    
    public boolean isByte()
    {
        return getOrParseNumber(Byte.class, Byte::parseByte).isPresent();
    }
    
    public byte asByte()
    {
        return getOrParseNumber(Byte.class, Byte::parseByte).orElse((byte) 0);
    }
    
    public boolean isInteger()
    {
        return getOrParseNumber(Integer.class, Integer::parseInt).isPresent();
    }
    
    public int asInteger()
    {
        return getOrParseNumber(Integer.class, Integer::parseInt).orElse(0);
    }
    
    public boolean isLong()
    {
        return getOrParseNumber(Long.class, Long::parseLong).isPresent();
    }
    
    public long asLong()
    {
        return getOrParseNumber(Long.class, Long::parseLong).orElse(0L);
    }
    
    public boolean isFloat()
    {
        return getOrParseNumber(Float.class, Float::parseFloat).isPresent();
    }
    
    public float asFloat()
    {
        return getOrParseNumber(Float.class, Float::parseFloat).orElse(0F);
    }
    
    public boolean isDouble()
    {
        return getOrParseNumber(Double.class, Double::parseDouble).isPresent();
    }
    
    public double asDouble()
    {
        return getOrParseNumber(Double.class, Double::parseDouble).orElse(0.0);
    }
    
    private Optional<Boolean> parseAsBoolean()
    {
        switch (text.toLowerCase())
        {
            case "yes":
            case "y":
            case "true":
            case "enable":
            case "enabled":
                return Optional.of(true);
            
            case "no":
            case "n":
            case "false":
            case "disable":
            case "disabled":
                return Optional.of(false);
            
            default:
                return Optional.empty();
        }
    }
    
    public boolean isBoolean()
    {
        return getOrParse(Boolean.class, this::parseAsBoolean).orElse(false);
    }
    
    public boolean asBoolean()
    {
        return getOrParse(Boolean.class, this::parseAsBoolean).orElse(false);
    }
    
    @Override
    public boolean equals(Object object)
    {
        if (object instanceof Argument)
        {
            Argument other = (Argument) object;
            return Objects.equals(text, other.text)
                   && Objects.equals(index, other.index);
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        return text;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(text, index);
    }
}
