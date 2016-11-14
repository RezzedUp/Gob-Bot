package com.rezzedup.gob.util;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;

import java.util.List;

public class Text
{
    
    public static String formattedResponse(String header, String content)
    {
        return String.format("%s\n - - -\n %s\n - - -", header, content);
    }
    
    public static String formattedResponse(String header, String... content)
    {
        return formattedResponse(header, String.join("\n ", content));
    }
    
    public static String formattedResponse(String header, List<String> content)
    {
        return formattedResponse(header, String.join("\n ", content));
    }
    
    public static String formattedCodeResponse(String header, String language, String content)
    {
        return String.format("%s\n - - -```%s%s``` - - -", header, language, content);
    }
    
    public static String formattedCodeResponse(String header, String language, String... content)
    {
        return formattedCodeResponse(header, language, String.join("\n", content));
    }
    
    public static String formattedCodeResponse(String header, String language, List<String> content)
    {
        return formattedCodeResponse(header, language, String.join("\n", content));
    }
    
    public static String formatGuild(Message message)
    {
        return (message.isFromType(ChannelType.PRIVATE)) ? "<PM>" : message.getGuild().getName();
    }
    
    public static String formatGuildChannel(Message message)
    {
        return "(" + formatGuild(message) + ")#" + message.getChannel().getName();
    }
}
