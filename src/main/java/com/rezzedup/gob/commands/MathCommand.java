package com.rezzedup.gob.commands;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.rezzedup.gob.ColorPalette;
import com.rezzedup.gob.core.Command;
import com.rezzedup.gob.Emoji;
import com.rezzedup.gob.Response;
import com.rezzedup.gob.core.Context;

public class MathCommand extends Command
{
    private static DoubleEvaluator evaluator = new DoubleEvaluator();
    
    public MathCommand()
    {
        super("math", "solve", "=", "equation", "equals", "eq", Emoji.NUMBER_1234);
        setDescription("Solves a math expression");
    }
    
    @Override
    public void execute(Context context)
    {
        Response response = new Response(context.message);
        String expression = String.join(" ", context.arguments);
        
        try
        {
            double result = evaluator.evaluate(expression);
            String answer = String.valueOf(result).replaceAll("[.]?0+$", "");
            
            response
                .setColor(ColorPalette.BLUE)
                .setDescription("Solved it, " + context.message.getAuthor().getAsMention() + "! " + Emoji.SMILE)
                .addField("Result:", String.format("`%s` **=** `%s`", expression, answer), true);
        }
        catch (IllegalArgumentException e)
        {
            response
                .setColor(ColorPalette.GOB_RED)
                .setDescription("I couldn't solve that, " + context.message.getAuthor().getAsMention() + " " + Emoji.CRY)
                .addField("Error:", e.getMessage(), true);
        }
        
        response.send();
    }
}
