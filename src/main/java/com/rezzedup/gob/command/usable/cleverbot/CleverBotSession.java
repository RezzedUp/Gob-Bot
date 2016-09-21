package com.rezzedup.gob.command.usable.cleverbot;

import com.google.code.chatterbotapi.ChatterBotSession;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CleverBotSession
{
    private final ChatterBotSession session;
    
    private final Date creation = new Date();
    private Date lastUse = creation;
    
    public CleverBotSession(ChatterBotSession session)
    {
        this.session = session;
    }
    
    public ChatterBotSession getSession()
    {
        lastUse = new Date();
        return session;
    }
    
    public Date getCreationDate()
    {
        return creation;
    }
    
    public long minutesSinceLastUse()
    {
        return TimeUnit.MILLISECONDS.toMinutes(lastUse.getTime() - creation.getTime());
    }
    
    public boolean isExpired()
    {
        return minutesSinceLastUse() >= 60;
    }
}
