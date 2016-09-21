package com.rezzedup.gob.command;

import sx.blah.discord.handle.obj.IMessage;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandParser
{
    // First alias -> Command
    private final Map<String, Command> commands = new LinkedHashMap<>();
    
    // First alias -> All aliases
    private final Map<String, String[]> aliases = new LinkedHashMap<>();
    
    public Executable parse(String command, IMessage message)
    {
        String[] parts = command.split(" ");
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);
        
        if (commands.containsKey(parts[0]))
        {
            return new CommandExecutor(commands.get(parts[0]), args, message);
        }
        
        for (String cmd : commands.keySet())
        {
            boolean root = true;
            
            for (String alias : aliases.get(cmd))
            {
                // Already checked first alias: avoids rechecking.
                if (root)
                {
                    root = false;
                    continue;
                }
                
                if (parts[0].equalsIgnoreCase(alias))
                {
                    return new CommandExecutor(commands.get(cmd), args, message);
                }
            }
        }
        
        return new UnknownCommandExecutor(message);
    }
    
    public void register(Command command)
    {
        String cmd = command.getAliases()[0];
        
        commands.put(cmd, command);
        aliases.put(cmd, command.getAliases());
    }
    
    public Collection<Command> getRegisteredCommands()
    {
        return commands.values();
    }
}
