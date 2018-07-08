package com.rezzedup.gob.commands.definitions;

import com.rezzedup.gob.Emoji;
import com.rezzedup.gob.commands.Command;
import com.rezzedup.gob.commands.CommandEvaluator;
import com.rezzedup.gob.commands.Context;
import com.rezzedup.gob.util.Text;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends Command
{
    private final CommandEvaluator.Registry parser;
    
    public HelpCommand(CommandEvaluator.Registry parser)
    {
        super("help", "?", Emoji.QUESTION);
        setDescription("Displays all usable commands.");
        this.parser = parser;
    }
    
    @Override
    public void execute(Context context)
    {
        MessageChannel channel = context.message.getChannel();
        List<String> msg = new ArrayList<>();
        
        for (Command command : parser.getRegisteredCommands())
        {
            String entry = "__" + command.getName() + "__ - ";
            
            if (command.getDescription().isEmpty())
            {
                entry += "`No description.`";
            }
            else 
            {
                entry += "`" + command.getDescription() + "`";
            }
            
            List<String> aliases = command.getAliases();
            
            if (aliases.size() >= 2)
            {
                entry += " **Aliases:** ";
                entry += String.join(", ", aliases.subList(1, aliases.size()));
            }
            
            msg.add(entry);
        }
        
        try
        {
            channel.sendMessage(Text.formattedResponse("Here's what I can do... \uD83D\uDC7A", msg)).queue();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
