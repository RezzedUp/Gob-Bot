package com.rezzedup.gob.commands.definitions;

import com.rezzedup.gob.Emoji;
import com.rezzedup.gob.commands.CommandRegistry;
import com.rezzedup.gob.commands.CommandSubmission;
import com.rezzedup.gob.commands.Commander;
import com.rezzedup.gob.commands.annotations.Aliases;
import com.rezzedup.gob.commands.annotations.Command;
import com.rezzedup.gob.commands.annotations.Description;

public class HelpCommand implements Commander
{
    private final CommandRegistry registry;
    
    public HelpCommand(CommandRegistry registry)
    {
        this.registry = registry;
    }
    
    @Command("help")
    @Aliases({"?", Emoji.QUESTION})
    @Description("Displays all usable commands.")
    public void onHelpCommand(CommandSubmission command)
    {
        command.channel.sendMessage("Coming soon!").queue();
    }
}
