package com.rezzedup.gob.commands;

import com.rezzedup.gob.core.CommandEvaluator;
import com.rezzedup.gob.Emoji;
import com.rezzedup.gob.core.Command;
import com.rezzedup.gob.util.Text;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelpCommand extends Command
{
    private final CommandEvaluator.CommandParser parser;
    
    public HelpCommand(CommandEvaluator.CommandParser parser)
    {
        super("help", "?", Emoji.QUESTION);
        setDescrption("Displays all usable commands.");
        this.parser = parser;
    }
    
    @Override
    public void execute(String[] args, Message message)
    {
        MessageChannel channel = message.getChannel();
        List<String> msg = new ArrayList<>();
        
        for (Command command : parser.getRegisteredCommands())
        {
            String[] aliases = command.getAliases();
            String entry = "__" + aliases[0] + "__ - ";
            
            if (command.getDescrption().isEmpty())
            {
                entry += "`No description.`";
            }
            else 
            {
                entry += "`" + command.getDescrption() + "`";
            }
            
            if (aliases.length >= 2)
            {
                entry += " **Aliases:** ";
                entry += String.join(", ", Arrays.copyOfRange(aliases, 1, aliases.length));
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
