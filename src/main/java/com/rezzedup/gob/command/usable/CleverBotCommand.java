package com.rezzedup.gob.command.usable;

import com.google.code.chatterbotapi.ChatterBotFactory;

import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.rezzedup.gob.Emoji;
import com.rezzedup.gob.command.Command;
import com.rezzedup.gob.util.Text;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class CleverBotCommand extends Command
{
    // "Guild:Channel" -> Session
    private Map<String, CleverBotSession> sessions = new HashMap<>();
    private ChatterBotFactory factory = new ChatterBotFactory();
    private Timer timer = new Timer();
    
    public CleverBotCommand()
    {
        super(new String[]{"clever", "cleverbot", Emoji.CL.toString(), Emoji.ROBOT.toString()});
        setDescrption("Have a conversation with CleverBot.");
    
        long hour = 1000*60*60;
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                removeExpiredSessions();
            }
        };
        timer.schedule(task, hour, hour);
    }
    
    @Override
    public void execute(String[] args, Message message)
    {
        MessageChannel channel = message.getChannel();
        String id = Text.formatGuildChannel(message);
        CleverBotSession session = sessions.get(id);
        
        if (session == null || session.isExpired())
        {
            try
            {
                channel.sendMessage("Starting a new **CleverBot** session!");
                
                ChatterBotSession bot = factory.create(ChatterBotType.CLEVERBOT).createSession();
                session = new CleverBotSession(bot);
                
                sessions.put(id, session);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return;
            }
        }
        
        try
        {
            channel.sendMessage(session.getSession().think(String.join(" ", args)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void removeExpiredSessions()
    {
        List<String> remove = new ArrayList<>();
        
        for (String id : sessions.keySet())
        {
            if (sessions.get(id).isExpired())
            {
                remove.add(id);
            }
        }
        
        remove.forEach(id -> sessions.remove(id));
    }
    
    private static class CleverBotSession
    {
        private final ChatterBotSession session;
        
        private final Date creation = new Date();
        private Date lastUse = creation;
        
        CleverBotSession(ChatterBotSession session)
        {
            this.session = session;
        }
        
        ChatterBotSession getSession()
        {
            lastUse = new Date();
            return session;
        }
        
        Date getCreationDate()
        {
            return creation;
        }
        
        long minutesSinceLastUse()
        {
            return TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - lastUse.getTime());
        }
        
        boolean isExpired()
        {
            return minutesSinceLastUse() >= 60;
        }
    }
}
