package com.rezzedup.gob;

public enum Emoji
{
    CL("\uD83C\uDD91"),
    CRY("\uD83D\uDE22"),
    JAPANESE_GOBLIN("\uD83D\uDC7A"),
    NERD("\uD83E\uDD13"),
    NUMBER_1234("\uD83D\uDD22"),
    ROBOT("\uD83E\uDD16"),
    QUESTION("\u2753");
    
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
