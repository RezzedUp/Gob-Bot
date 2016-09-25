package com.rezzedup.gob.command.usable.cleverbot;

import com.google.code.chatterbotapi.ChatterBotFactory;

import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.rezzedup.gob.command.Command;
import com.rezzedup.gob.util.Text;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CleverBotCommand extends Command
{
    // "Guild:Channel" -> Session
    private Map<String, CleverBotSession> sessions = new HashMap<>();
    private ChatterBotFactory factory = new ChatterBotFactory();
    private Timer timer = new Timer();
    
    public CleverBotCommand(IDiscordClient client)
    {                                                   // :cl:            :robot:
        super(client, new String[]{"clever", "cleverbot", "\uD83C\uDD91", "\uD83E\uDD16"});
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
    public void execute(String[] args, IMessage message)
    {
        IChannel channel = message.getChannel();
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
        for (String id : sessions.keySet())
        {
            if (sessions.get(id).isExpired())
            {
                sessions.remove(id);
            }
        }
    }
}
