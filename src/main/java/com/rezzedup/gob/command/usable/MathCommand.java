package com.rezzedup.gob.command.usable;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.rezzedup.gob.Emoji;
import com.rezzedup.gob.command.Command;
import com.rezzedup.gob.util.Text;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class MathCommand extends Command
{
    private static DoubleEvaluator evaluator = new DoubleEvaluator();
    
    public MathCommand()
    {
        super(new String[]{"math", "solve", "=", "equation", "equals", "eq", Emoji.NUMBER_1234.toString()});
        setDescrption("Solves a math expression");
    }
    
    @Override
    public void execute(String[] args, Message message)
    {
        MessageChannel channel = message.getChannel();
        String expression = String.join(" ", args);
        String msg;
        
        try
        {
            double result = evaluator.evaluate(expression);
            String answer = String.valueOf(result).replaceAll("[.]?0+$", "");
            
            msg = Text.formattedCodeResponse
            (
                "Solved it, " + message.getAuthor().getAsMention() + "! " + Emoji.NERD, "",
                String.format("%s = %s", expression, answer)
            );
        }
        catch (IllegalArgumentException e)
        {
            msg = Text.formattedCodeResponse
            (
                "I couldn't solve that, " + message.getAuthor().getAsMention() + " " + Emoji.CRY, "", e.getMessage()
            );
        }
        
        channel.sendMessage(msg);
    }
}
