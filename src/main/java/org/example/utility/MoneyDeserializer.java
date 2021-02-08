package org.example.utility;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class MoneyDeserializer implements JsonDeserializer<Float> {

    private static final Logger L = LoggerFactory.getLogger(MoneyDeserializer.class);

    private static final String BILLION = "B";
    private static final String MILLION = "M";
    private static final String THOUSANDS = "k";

    @Override
    public Float deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return formatFunds(jsonElement.getAsString());
    }

    protected Float formatFunds(String element) {
        try {
            int indexOfCurrency = getIndexOfCurrency(element);

            if (element.length() > indexOfCurrency) {
                // no regex for perf purposes
                if (element.endsWith(BILLION)) {
                    return Float.parseFloat(element.substring(indexOfCurrency, element.length() - 1)) * 1_000_000_000;
                } else if (element.endsWith(MILLION)) {
                    return Float.parseFloat(element.substring(indexOfCurrency, element.length() - 1)) * 1_000_000;
                } else if (element.endsWith(THOUSANDS)) {
                    return Float.parseFloat(element.substring(indexOfCurrency, element.length() - 1)) * 1_000;
                } else {
                    return Float.parseFloat(element.substring(indexOfCurrency));
                }
            } else {
                return 0f;
            }
        } catch (NumberFormatException e) {
            L.error("Number format unknown ", element);
            throw e;
        }
    }

    protected int getIndexOfCurrency(String element) {
        if (element.indexOf('$') >= 0) {
            return element.indexOf('$') + 1;
        } else if (element.indexOf('€') > -1) {
            return element.indexOf('€') + 1;
        } else if (element.indexOf('£') > -1) {
            return element.indexOf('£') + 1;
        } else {
            return 0;
        }
    }
}
