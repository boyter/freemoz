package com.freemoz.app.util;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Used inside Spark routes to convert return model into JSON
 */
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
}
