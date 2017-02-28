package com.rezzedup.gob.commands;

import com.michaelwflaherty.cleverbotapi.CleverBotQuery;
import com.rezzedup.gob.Emoji;
import com.rezzedup.gob.Gob;
import com.rezzedup.gob.core.Command;
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
    private final Map<String, CleverBotSession> sessions = new HashMap<>();
    private final Timer timer = new Timer();
    
    private final String key;
    
    public CleverBotCommand(String key)
    {
        super(new String[]{"clever", "cleverbot", Emoji.CL.toString(), Emoji.ROBOT.toString()});
        setDescrption("Have a conversation with CleverBot.");
        
        this.key = key;
    
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
        String phrase = String.join(" ", args);
        CleverBotQuery query = null;
        
        if (session == null || session.isExpired())
        {
            try
            {
                channel.sendMessage("Starting a new **CleverBot** session!").queue();
    
                query = new CleverBotQuery(this.key, phrase);
                session = new CleverBotSession(query);
    
                sessions.put(id, session);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return;
            }
        }
        else 
        {
            query = session.getQuery();
            query.setPhrase(phrase);
        }
        
        try
        {
            query.sendRequest();
            
            Gob.status
            (
                "\n--- [CleverBot] ---\n" +
                " Phrase:          '" + query.getPhrase() +  "'\n" +
                " Response:        '" + query.getResponse() + "'\n" +
                " Conversation-ID: '" + query.getConversationID() + "'\n" +
                "--- [CleverBot] ---"
            );
            
            channel.sendMessage(query.getResponse()).queue();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void removeExpiredSessions()
    {
        Gob.status("[CleverBot] Removing expired sessions. There are currently " + sessions.size() + " active session(s).");
    
        List<String> expired = new ArrayList<>();
        
        sessions.entrySet().stream()
            .filter(entry -> entry.getValue().isExpired()).map(Map.Entry::getKey).forEach(expired::add);
        
        expired.forEach(sessions::remove);
    
        Gob.status("[CleverBot] Removed " + expired.size() + " session(s). There are now " + sessions.size() + " active session(s).");
    }
    
    private static class CleverBotSession
    {
        private final CleverBotQuery query;
        
        private final Date creation = new Date();
        private Date lastUse = creation;
        
        CleverBotSession(CleverBotQuery query)
        {
            this.query = query;
        }
        
        CleverBotQuery getQuery()
        {
            lastUse = new Date();
            return query;
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
