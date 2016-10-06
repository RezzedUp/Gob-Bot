package com.rezzedup.gob;

public enum Emoji
{
    JAPANESE_GOBLIN("\uD83D\uDC7A"),
    CRY("\uD83D\uDE22"),
    CL("\uD83C\uDD91"),
    NUMBER_1234("\uD83D\uDD22"),
    NERD("\uD83E\uDD13"),
    ROBOT("\uD83E\uDD16");
    
    private final String value;
    
    Emoji(String unicode)
    {
        value = unicode;
    }
    
    @Override
    public String toString()
    {
        return value;
    }
}
