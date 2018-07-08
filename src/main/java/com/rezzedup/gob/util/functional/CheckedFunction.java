package com.rezzedup.gob.util.functional;

@FunctionalInterface
public interface CheckedFunction<T, R, E extends Throwable>
{
    R apply(T arg) throws E;
}
