package com.rezzedup.gob;

import com.rezzedup.gob.command.CommandParser;
import com.rezzedup.gob.command.usable.HelpCommand;
import com.rezzedup.gob.command.usable.InfoCommand;
import com.rezzedup.gob.command.usable.MathCommand;
import com.rezzedup.gob.command.usable.CleverBotCommand;

import sx.blah.discord.Discord4J;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.util.DiscordException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Gob
{
    public static final String[] IDENTIFIERS = 
    {
        Emoji.JAPANESE_GOBLIN.toString(), ":gob:", ":gob", "gob:", "gob "
    };
    
    public static void main(String[] args)
    {
        if (args.length <= 0)
        {
            status("Expected bot's authentication token as the first argument.");
            return;
        }
    
        Discord4J.disableChannelWarnings();
        
        ClientBuilder builder = new ClientBuilder();
        IDiscordClient client;
    
        try
        {
            builder.withToken(args[0]);
            
            client = builder.login();
        }
        catch (DiscordException e)
        {
            status("Unable to log in.");
            e.printStackTrace();
            return;
        }
        
        client.getDispatcher().registerListener(new Gob());
    
        CommandParser parser = new CommandListener(client).getCommandParser();
        
        parser.register(new HelpCommand(client, parser));
        parser.register(new InfoCommand(client));
        parser.register(new CleverBotCommand(client));
        parser.register(new MathCommand(client));
    }
    
    public static void status(String message)
    {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"));
        System.out.println(String.format("[%s]: %s", time, message));
    }
    
    @EventSubscriber
    public void onStart(ReadyEvent event)
    {
        status("\n\n\n\n --- Gob --- \n Ready to go! \n\n\n");
    }
}
