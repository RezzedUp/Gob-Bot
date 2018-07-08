package com.rezzedup.gob.commands.definitions;

import com.rezzedup.gob.commands.CommandSubmission;
import com.rezzedup.gob.commands.Commander;
import com.rezzedup.gob.commands.annotations.Aliases;
import com.rezzedup.gob.commands.annotations.Command;
import com.rezzedup.gob.commands.annotations.Description;
import com.rezzedup.gob.util.Text;

public class InfoCommand implements Commander
{
    @Command("info")
    @Aliases({"about"})
    @Description("Displays information about Gob-bot.")
    public void onInfoCommand(CommandSubmission command)
    {
        String send = Text.formattedResponse
        (
            "A little bit about me",
            "Created by: **Rezz#3333**",
            "GitHub repository: <https://github.com/RezzedUp/Gob-Bot>",
            "Uses __JDA__: <https://github.com/DV8FromTheWorld/JDA>"
        );
        command.channel.sendMessage(send).queue();
    }
}
