package com.rezzedup.gob.commands;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.rezzedup.gob.ColorPalette;
import com.rezzedup.gob.Command;
import com.rezzedup.gob.Emoji;
import com.rezzedup.gob.Response;
import net.dv8tion.jda.core.entities.Message;

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
        Response response = new Response(message);
        String expression = String.join(" ", args);
        
        try
        {
            double result = evaluator.evaluate(expression);
            String answer = String.valueOf(result).replaceAll("[.]?0+$", "");
            
            response
                .setColor(ColorPalette.BLUE)
                .setDescription("Solved it, " + message.getAuthor().getAsMention() + "! " + Emoji.SMILE)
                .addField("Result:", String.format("```haskell\n%s = %s\n```", expression, answer), false);
        }
        catch (IllegalArgumentException e)
        {
            response
                .setColor(ColorPalette.GOB_RED)
                .setDescription("I couldn't solve that, " + message.getAuthor().getAsMention() + " " + Emoji.CRY)
                .addField("Error:", "```haskell\n" + e.getMessage() + "\n```", false);
        }
        
        response.send();
    }
}
