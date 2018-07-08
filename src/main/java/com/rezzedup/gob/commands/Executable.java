package com.rezzedup.gob.commands;

@FunctionalInterface
public interface Executable
{
    void execute(Context context);
}
