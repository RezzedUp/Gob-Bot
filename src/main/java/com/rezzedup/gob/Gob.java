package com.rezzedup.gob;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.util.Date;

public class Gob
{
    private static ClientBuilder client = null;
    
    public static void main(String[] args)
    {
        if (args.length <= 0)
        {
            status("Expected bot's authentication token as the first argument.");
            return;
        }
        
        ClientBuilder client = new ClientBuilder();
        client.withToken(args[0]);
        
        try
        {
            client.login();
        }
        catch (DiscordException e)
        {
            status("Unable to log in.");
            e.printStackTrace();
            return;
        }
    }
    
    public static void status(String message)
    {
        System.out.println(new Date().toString() + message);
    }
}
