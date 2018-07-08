package com.rezzedup.gob;

import com.rezzedup.gob.commands.Argument;
import com.rezzedup.gob.commands.ArgumentStack;
import com.rezzedup.gob.commands.OptionProcessor;
import com.rezzedup.gob.commands.Flag;
import com.rezzedup.gob.commands.OptionValues;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Gob extends ListenerAdapter
{
    public static final OptionProcessor SETUP_OPTIONS = 
        OptionProcessor.using(Flag.SETTING.called("-d", "--discord", "--discord-token"))
            .and(Flag.QUERY.called("-?", "-h", "--help"))
            .and(Flag.SETTING.called("-y", "--yandex", "--yandex-token"))
        .build();
    
    private static boolean active = true;
    
    public static final String[] IDENTIFIERS = 
    {
        Emoji.JAPANESE_GOBLIN, ":gob:", ":gob", "gob:", "gob ", "gobbledygook"
    };
    
    public static void main(String[] input)
    {
        ArgumentStack args = new ArgumentStack(List.of(input));
        OptionValues options = SETUP_OPTIONS.processOptions(args);
        
        if (options.hasQuery("--help"))
        {
            // TODO: display help
            return;
        }
    
        Argument discordToken = options.getSetting("--discord");
        
        if (!discordToken.exists())
        {
            status("Missing bot's discord token (--discord <token>)");
            return;
        }
        
        JDA jda;
    
        try
        {
            jda = new JDABuilder(AccountType.BOT).setToken(discordToken.asText()).buildBlocking();
        }
        catch (LoginException | InterruptedException e)
        {
            status("Unable to log in.");
            e.printStackTrace();
            return;
        }
        
        Gob gob = new Gob(jda);

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
    
    public Gob(JDA jda)
    {
        
        jda.addEventListener(this);
    
        status("\n\n\n\n --- Gob --- \n Ready to go! \n\n\n");
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        //command.evaluate(event.getMessage());
    }
}
