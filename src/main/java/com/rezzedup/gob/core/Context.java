package com.rezzedup.gob.core;

import com.rezzedup.gob.Emoji;
import net.dv8tion.jda.core.entities.Message;

import java.util.List;

public class Context
{
    public final Message message;
    public final String command;
    public final List<String> arguments;
    
    public Context(Message message, String command, List<String> arguments)
    {
        this.message = message;
        this.command = command;
        this.arguments = arguments;
    }
    
    public boolean isEmoji()
    {
        return Emoji.EMOJI_TO_NAME.get(command) != null;
    }
}
