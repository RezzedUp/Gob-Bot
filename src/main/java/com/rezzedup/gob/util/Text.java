package com.rezzedup.gob.util;

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
}
