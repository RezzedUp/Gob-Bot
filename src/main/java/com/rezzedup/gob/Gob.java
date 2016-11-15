package com.rezzedup.gob;

import com.rezzedup.gob.command.CommandParser;
import com.rezzedup.gob.command.usable.HelpCommand;
import com.rezzedup.gob.command.usable.InfoCommand;
import com.rezzedup.gob.command.usable.MathCommand;
import com.rezzedup.gob.command.usable.CleverBotCommand;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.Console;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Gob extends ListenerAdapter
{
    private static boolean active = true;
    
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
    
        JDA jda;
    
        try
        {
            jda = new JDABuilder(AccountType.BOT).setToken(args[0]).buildBlocking();
        }
        catch (LoginException | InterruptedException | RateLimitedException e)
        {
            status("Unable to log in.");
            e.printStackTrace();
            return;
        }
        if (jda == null)
        {
            status("JDA instance is null.");
            return;
        }
        
        new Gob(jda);
    
        Console console = System.console();
        
        while (true)
        {
            String command = console.readLine();
            
            switch (command.toLowerCase())
            {
                case "q":
                case "quit":
                case "shutdown":
                case "stop":
                    status("Shutting down...");
                    System.exit(0);
                    return;
                
                default:
                    status("Unknown command.");
            }
        }
    }
    
    public static void status(String message)
    {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"));
        System.out.println(String.format("[%s]: %s", time, message));
    }
    
    private final CommandEvaluator command;
    
    public Gob(JDA jda)
    {
        this.command = new CommandEvaluator(jda);
        
        jda.addEventListener(this);
        
        CommandParser parser = command.getCommandParser();
        
        parser.register(new HelpCommand(parser));
        parser.register(new InfoCommand());
        parser.register(new CleverBotCommand());
        parser.register(new MathCommand());
    
        status("\n\n\n\n --- Gob --- \n Ready to go! \n\n\n");
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        command.evaluate(event.getMessage());
    }
}
