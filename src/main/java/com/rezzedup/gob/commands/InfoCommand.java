package com.rezzedup.gob.commands;

import com.rezzedup.gob.core.Command;
import com.rezzedup.gob.core.Context;
import com.rezzedup.gob.util.Text;

public class InfoCommand extends Command
{
    public InfoCommand()
    {
        super("info", "about");
        setDescription("Displays information about Gob-bot.");
    }
    
    @Override
    public void execute(Context context)
    {
        String send = Text.formattedResponse
        (
            "A little bit about me",
            "Created by: **RezzedUp**",
            "GitHub repository: <https://github.com/RezzedUp/Gob-Bot>",
            "Uses __JDA__: <https://github.com/DV8FromTheWorld/JDA>"
        );
        context.message.getChannel().sendMessage(send).queue();
    }
}
