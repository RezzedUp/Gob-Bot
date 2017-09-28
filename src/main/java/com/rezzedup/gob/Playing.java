package com.rezzedup.gob;

import net.dv8tion.jda.core.entities.Game;

public enum Playing
{
    COLON_GOB_HELP(":gob help"),
    GOBLIN_HELP(Emoji.JAPANESE_GOBLIN + " help"),
    GOBLIN_ROBOT_HELLO(Emoji.JAPANESE_GOBLIN + " " + Emoji.ROBOT + " hello"),
    GOBLIN_EQUALS_MATH(Emoji.JAPANESE_GOBLIN + " = 1 + 1");
    
    private final Game game;
    
    Playing(String value)
    {
        this.game = Game.of(value);
    }
    
    public Game getGame()
    {
        return this.game;
    }
    
    public Playing next()
    {
        int next = (ordinal() < values().length - 1) ? ordinal() + 1 : 0;
        return values()[next];
    }
}
