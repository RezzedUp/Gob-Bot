package com.rezzedup.gob.command.usable;

import com.rezzedup.gob.command.Command;
import com.rezzedup.gob.command.CommandParser;

import com.rezzedup.gob.util.Text;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelpCommand extends Command
{
    private final CommandParser parser;
    
    public HelpCommand(IDiscordClient client, CommandParser parser)
    {                                        // :question:
        super(client, new String[]{"help", "?", "\u2753"});
        setDescrption("Displays all usable commands.");
        
        this.parser = parser;
    }
    
    @Override
    public void execute(String[] args, IMessage message)
    {
        IChannel channel = message.getChannel();
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
            channel.sendMessage(Text.formattedResponse("Here's what I can do... \uD83D\uDC7A", msg));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
