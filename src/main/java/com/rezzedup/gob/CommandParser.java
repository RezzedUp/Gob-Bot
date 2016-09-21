package com.rezzedup.gob;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

public class CommandParser
{
    private static final String[] identifiers = {":japanese_goblin:", ":gob", "gob"};
    
    @EventSubscriber
    public void onMessage(MessageReceivedEvent event)
    {
        
    }
}
