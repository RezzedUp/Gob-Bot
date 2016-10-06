package com.rezzedup.gob.command.usable;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.rezzedup.gob.Emoji;
import com.rezzedup.gob.command.Command;
import com.rezzedup.gob.util.Text;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class MathCommand extends Command
{
    private static DoubleEvaluator evaluator = new DoubleEvaluator();
    
    public MathCommand(IDiscordClient client)
    {
        super(client, new String[]{"math", "solve", "=", "equation", "equals", "eq", Emoji.NUMBER_1234.toString()});
        setDescrption("Solves a math expression");
    }
    
    @Override
    public void execute(String[] args, IMessage message)
    {
        IChannel channel = message.getChannel();
        String expresssion = String.join(" ", args);
        String msg;
        
        try
        {
            double result = evaluator.evaluate(expresssion);
            msg = Text.formattedCodeResponse
            (
                "Solved it, " + message.getAuthor().mention() + "! " + Emoji.NERD, "",
                String.format("%s = %s", expresssion, (result == (long) result) ? (long) result : result)
            );
        }
        catch (IllegalArgumentException e)
        {
            msg = Text.formattedCodeResponse
            (
                "I couldn't solve that, " + message.getAuthor().mention() + " " + Emoji.CRY, "", e.getMessage()
            );
        }
        
        try
        {
            channel.sendMessage(msg);
        }
        catch (MissingPermissionsException | RateLimitException | DiscordException e)
        {
            e.printStackTrace();
        }
    }
}
