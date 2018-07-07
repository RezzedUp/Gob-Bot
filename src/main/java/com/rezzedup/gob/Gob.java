package com.rezzedup.gob;

import com.rezzedup.gob.commands.HelpCommand;
import com.rezzedup.gob.commands.InfoCommand;
import com.rezzedup.gob.core.CommandEvaluator;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class Gob extends ListenerAdapter
{
    private static boolean active = true;
    
    public static final String[] IDENTIFIERS = 
    {
        Emoji.JAPANESE_GOBLIN, ":gob:", ":gob", "gob:", "gob "
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
        
        Gob gob = new Gob(jda);
        
        if (args.length >= 2)
        {
            //gob.setCleverBotApiKey(args[1]);
        }
        else 
        {
            status("CleverBot features won't be enabled: expected CleverBot API Key as second argument.");
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            System.out.println("Gob: Bye!");
        }));
    
        while (true)
        {
            String line = System.console().readLine();

            switch (line.toLowerCase())
            {
                case "q":
                case "quit":
                case "shutdown":
                case "stop":
                    exit();
                    break;
    
                default:
                    status("Unknown command.");
                    break;
            }
        }
    }
    
    public static void status(String message)
    {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"));
        System.out.println(String.format("[%s]: %s", time, message));
    }
    
    private static void exit()
    {
        status("Shutting down...");
        System.exit(0);
    }
    
    private final CommandEvaluator command;
    
    private final Timer timer = new Timer();
    
    public Gob(JDA jda)
    {
        this.command = new CommandEvaluator(jda);
        
        jda.addEventListener(this);
        
        CommandEvaluator.Registry registry = command.getCommandRegistry();
        
        registry.register(new HelpCommand(registry));
        registry.register(new InfoCommand());
    
        status("\n\n\n\n --- Gob --- \n Ready to go! \n\n\n");
    
        TimerTask scrollStatus = new TimerTask() 
        {
            private Playing status = Playing.COLON_GOB_HELP;
            
            @Override
            public void run()
            {
                jda.getPresence().setGame(status.getGame());
                status = status.next();
            }
        };
    
        long seconds = 30L * 1000L;
        
        timer.schedule(scrollStatus, 0L, seconds);
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        command.evaluate(event.getMessage());
    }
}
