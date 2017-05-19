package com.rezzedup.gob;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class Response extends EmbedBuilder
{
    private final StringBuilder description = new StringBuilder();
    
    private final MessageChannel channel;
    private final User sender;
    
    public Response(MessageChannel channel, User sender)
    {
        this.channel = channel;
        this.sender = sender;
    }
    
    public Response(Message message)
    {
        this(message.getChannel(), message.getAuthor());
    }
    
    public Response setDescription(String description)
    {
        return append(description);
    }
    
    public Response setColor(ColorPalette color)
    {
        return (Response) super.setColor(color.getColor());
    }
    
    public Response append(String text)
    {
        description.append(text);
        return (Response) super.setDescription(description.toString());
    }
    
    public String mention()
    {
        return sender.getAsMention();
    }
    
    public Response send()
    {
        channel.sendMessage(build()).queue();
        return this;
    }
    
    public Response sendPrivately()
    {
        sender.getPrivateChannel().sendMessage(build()).queue();
        return this;
    }
}
