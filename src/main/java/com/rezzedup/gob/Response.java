package com.rezzedup.gob;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import net.dv8tion.jda.core.entities.User;

import java.awt.Color;
import java.util.Deque;
import java.util.LinkedList;

public class Response
{
    private final EmbedBuilder embed = new EmbedBuilder();
    private final Appendable description = new Appendable(embed::setDescription);
    private final Appendable title = new Appendable(embed::setTitle);
    private final Appendable url = new Appendable(embed::setUrl);
    private final Appendable video = new Appendable(embed::setVideo);
    
    private final Deque<FieldBuilder> fields = new LinkedList<>();
    
    private final MessageChannel channel;
    private final User sender;
    
    private Appendable latest = null;
    
    public Response(MessageChannel channel, User sender)
    {
        this.channel = channel;
        this.sender = sender;
    }
    
    public Response(Message message)
    {
        this(message.getChannel(), message.getAuthor());
    }
    
    public Response color(ColorPalette color)
    {
        embed.setColor(color.getColor());
        return this;
    }
    
    public Response color(Color color)
    {
        embed.setColor(color);
        return this;
    }
    
    public Response describe(String content)
    {
        latest = description.append(content);
        return this;
    }
    
    public Response title(String content)
    {
        latest = title.append(content);
        return this;
    }
    
    public Response field(String name, String value, boolean inline)
    {
        FieldBuilder builder = new FieldBuilder();
        
        builder.name.append(name);
        latest = builder.value.append(value);
        builder.inline = inline;
        
        fields.add(builder);
        return this;
    }
    
    public Response field(String name, String value)
    {
        return field(name, value, false);
    }
    
    private void checkFields()
    {
        if (fields.size() <= 0)
        {
            throw new IllegalStateException("No fields to append.");
        }
    }
    
    public Response appendFieldName(String name)
    {
        checkFields();
        fields.peekLast().name.append(name);
        return this;
    }
    
    public Response appendFieldValue(String value)
    {
        checkFields();
        fields.peekLast().value.append(value);
        return this;
    }
    
    public Response append(String text)
    {
        if (latest == null)
        {
            throw new IllegalStateException("Nothing to append.");
        }
        latest.append(text);
        return this;
    }
    
    public String getMention()
    {
        return sender.getAsMention();
    }
    
    public Response mention()
    {
        return append(sender.getAsMention());
    }
    
    public MessageEmbed build()
    {
        for (FieldBuilder field : fields)
        {
            embed.addField(field.build());
        }
        return embed.build();
    }
    
    public Response send()
    {
        channel.sendMessage(embed.build()).queue();
        return this;
    }
    
    public Response sendPrivately()
    {
        sender.getPrivateChannel().sendMessage(embed.build()).queue();
        return this;
    }
    
    private interface Action
    {
        void run(String result);
    }
    
    private static class Appendable
    {
        final StringBuilder builder = new StringBuilder();
        
        final Action action;
        
        Appendable()
        {
            this.action = result -> {};
        }
        
        Appendable(Action action)
        {
            this.action = action;
        }
        
        Appendable append(String value)
        {
            if (value != null)
            {
                builder.append(value);
                action.run(builder.toString());
            }
            return this;
        }
    }
    
    private static class FieldBuilder
    {
        Appendable name = new Appendable();
        Appendable value = new Appendable();
        boolean inline;
    
        Field build()
        {
            return new Field(name.builder.toString(), value.builder.toString(), inline);
        }
    }
}
