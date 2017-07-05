package com.rezzedup.gob;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Emoji
{
    public static final String CL = "\uD83C\uDD91";
    public static final String CRY = "\uD83D\uDE22";
    public static final String JAPANESE_GOBLIN = "\uD83D\uDC7A";
    public static final String NERD = "\uD83E\uDD13";
    public static final String NUMBER_1234 = "\uD83D\uDD22";
    public static final String QUESTION = "\u2753";
    public static final String ROBOT = "\uD83E\uDD16";
    public static final String SMILE = "\uD83D\uDE04";
    
    public static final Map<String, String> NAME_TO_EMOJI;
    public static final Map<String, String> EMOJI_TO_NAME;
    
    static 
    {
        Map<String, String> nameToEmoji = new HashMap<>();
        Map<String, String> emojiToName = new HashMap<>();
        
        for (Field field : Emoji.class.getDeclaredFields())
        {
            if (field.getType() == String.class)
            {
                try
                {
                    String name = field.getName();
                    String value = (String) field.get(null);
                    
                    nameToEmoji.put(name, value);
                    emojiToName.put(value, name);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        NAME_TO_EMOJI = Collections.unmodifiableMap(nameToEmoji);
        EMOJI_TO_NAME = Collections.unmodifiableMap(emojiToName);
    }
}
