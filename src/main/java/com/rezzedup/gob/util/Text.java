package com.rezzedup.gob.util;

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
}
