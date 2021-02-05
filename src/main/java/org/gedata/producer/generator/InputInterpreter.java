package org.gedata.producer.generator;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@Component
public class InputInterpreter implements InputValueInterpreter {

    private final GeneratorProvider generatorProvider;

    private final SpelExpressionParser parser = new SpelExpressionParser();

    private final ThreadLocal<StandardEvaluationContext> currentContext =
            ThreadLocal.withInitial(() -> new StandardEvaluationContext(this));


    @Override
    public String eval(String text) {
        StandardEvaluationContext context = currentContext.get();
        text = text.replaceAll("\\$\\{quantity\\W\\d+\\W}", "");
        int location = 0;
        int start = 0;
        String initStr = "${";
        StringBuilder sb = new StringBuilder(text);

        try {
            while ((start = sb.indexOf(initStr, location)) > -1) {
                int close = sb.indexOf("}", start);
                String exoressionStr = sb.substring(start + initStr.length(), close);
                Expression expression = parser.parseExpression(exoressionStr);
                String evaluatedValue = expression.getValue(context, generatorProvider, String.class);
                sb.replace(start, close + "}".length(), evaluatedValue);
                location = start + evaluatedValue.length();
            }
        } catch (SpelParseException | SpelEvaluationException | StringIndexOutOfBoundsException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return sb.toString();
    }

    @Override
    public int evalQuantity(String text) {
        Pattern patter = Pattern.compile("\\d+");
        Matcher matcher = patter.matcher(text);
        if (!matcher.find()) {
            return 1;
        }
        return Integer.parseInt(matcher.group());
    }
}
