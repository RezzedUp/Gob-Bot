package com.rezzedup.gob;

import java.awt.Color;

public enum ColorPalette
{
    BLUE("#3CABE3"),
    GOB_RED("#DA2F47"),
    GOB_ACCENT_RED("#C42D47"),
    GOB_PINK("#E75A70"),
    GOB_PEACH("#FDD888"),
    GOB_DARK_GREY("#292F33"),
    GOB_NEARLY_WHITE("#F5F8FA");
    
    private final String hex;
    private final Color color;
    
    private ColorPalette(String hex)
    {
        this.hex = hex;
        this.color = Color.decode(hex);
    }
    
    public Color getColor()
    {
        return color;
    }
    
    @Override
    public String toString()
    {
        return hex;
    }
}
