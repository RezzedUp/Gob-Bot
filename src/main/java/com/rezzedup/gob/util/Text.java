package com.rezzedup.gob.util;

import sx.blah.discord.handle.obj.IMessage;

import java.util.List;

public class Text
{
    public static String stripWhitespace(String text)
    {
        if (text.startsWith(" ") || text.endsWith(" "))
        {
            return text.replaceAll("^ +| +$", "");
        }
        else
        {
            return text;
        }
    }
    
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
    
    public static String formatGuild(IMessage message)
    {
        return (message.getChannel().isPrivate()) ? "<PM>" : message.getGuild().getName();
    }
    
    public static String formatGuildChannel(IMessage message)
    {
        return "(" + formatGuild(message) + ")#" + message.getChannel().getName();
    }
}
