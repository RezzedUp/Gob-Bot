package com.rezzedup.gob;

import com.rezzedup.gob.command.CommandParser;
import com.rezzedup.gob.command.usable.HelpCommand;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Gob
{
    public static final String[] IDENTIFIERS = 
    {
        // :japanese_gobline:
        "\uD83D\uDC7A", ":gob:", ":gob", "gob:", "gob"
    };
    
    public static void main(String[] args)
    {
        if (args.length <= 0)
        {
            status("Expected bot's authentication token as the first argument.");
            return;
        }
        
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
    
        CommandParser parser = new CommandListener(client).getCommandParser();
        
        parser.register(new HelpCommand(client, parser));
    }
    
    public static void status(String message)
    {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"));
        System.out.println(String.format("[%s] %s", time, message));
    }
}
