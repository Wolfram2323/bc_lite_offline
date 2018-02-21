package com.bftcom.customizator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.SimpleType;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by k.nikitin on 14.02.2018.
 */
public class ActionDeserializer extends StdDeserializer<Action> {
    public ActionDeserializer() {
        super(Action.class);
    }

    @Override
    public Action deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Action result = new Action();
        jsonParser.nextToken();
        jsonParser.setCurrentValue(result);
        if(jsonParser.hasTokenId(JsonToken.FIELD_NAME.id())) {
            String propName = jsonParser.getCurrentName();
            String value = "";
            do {
                if(("type").equals(propName) || ("value").equals(propName)){
                    jsonParser.nextToken();
                    if(("type").equals(propName)){
                        result.setType(jsonParser.getText());
                    } else {
                        value = jsonParser.getText();
                    }
                } else if(("params").equals(propName)){
                    jsonParser.nextToken();
                    ObjectMapper mapper = new ObjectMapper();
                    result.setParams(mapper.readValue(jsonParser.getText(), HashMap.class));
                } else {
                    this.handleUnknownProperty(jsonParser, deserializationContext, result, propName);
                }
            } while((propName = jsonParser.nextFieldName()) != null);
            if(!value.isEmpty()){
                result.getParams().put("value", value);
            }
        }
        return result;
    }


}



