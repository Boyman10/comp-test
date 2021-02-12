package org.example.utilities.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Map.entry;

public class MoneyDeserializer implements JsonDeserializer<Double> {

    private static final Logger L = LoggerFactory.getLogger(MoneyDeserializer.class);

    private static final Character BILLION = 'B';
    private static final Character MILLION = 'M';
    private static final Character THOUSANDS = 'k';

    private static final Map<Character, Double> unitTranslation = Map.ofEntries(
            entry(BILLION, 1_000_000_000d),
            entry(MILLION, 1_000_000d),
            entry(THOUSANDS, 1_000d)
    );

    @Override
    public Double deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return formatFunds(jsonElement.getAsString());
    }

    protected Double formatFunds(String element) {

        final String regex = "(\\d+)(B|M|k)?";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(element);

        if (matcher.find()) {
            double num = Double.parseDouble(matcher.group(1));
            Character unit = null;

            if (matcher.group(2) != null) {
                unit = matcher.group(2).charAt(0);
            }

            return num * ((unit != null) ? unitTranslation.get(unit) : 1d);
        } else {
            return 0d;
        }
    }
}
