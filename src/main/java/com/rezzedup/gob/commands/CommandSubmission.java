package com.rezzedup.gob.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.List;
import java.util.Objects;

public class CommandSubmission
{
    public final String command;
    public final Message message;
    public final MessageChannel channel;
    public final User sender;
    public final ArgumentStack args;
    
    public CommandSubmission(Message message, String command, List<String> arguments)
    {
        this.command = Objects.requireNonNull(command, "Command is null.");
        this.message = Objects.requireNonNull(message, "Message is null.");
        this.channel = message.getTextChannel();
        this.sender = message.getAuthor();
        this.args = new ArgumentStack(arguments);
    }
}
