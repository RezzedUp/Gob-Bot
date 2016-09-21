package com.rezzedup.gob.command.usable;

import com.rezzedup.gob.command.Command;
import com.rezzedup.gob.util.Text;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

public class InfoCommand extends Command
{
    public InfoCommand(IDiscordClient client)
    {
        super(client, new String[]{"info", "about"});
        setDescrption("Displays information about Gob-bot.");
    }
    
    @Override
    public void execute(String[] args, IMessage message)
    {
        String send = Text.formattedResponse
        (
            "A little bit about me",
            "Created by: **RezzedUp**",
            "GitHub repository: <https://github.com/RezzedUp/Gob-Bot>",
            "Uses __Discord4J__: <https://github.com/austinv11/Discord4J>"
        );
        
        try
        {
            message.getChannel().sendMessage(send);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
