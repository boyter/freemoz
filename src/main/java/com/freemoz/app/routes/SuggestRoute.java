package com.freemoz.app.routes;

import com.freemoz.app.service.Singleton;
import org.apache.commons.validator.routines.UrlValidator;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuggestRoute {

    public static ModelAndView suggest(Request request, Response response) {
        Map<String, Object> map = new HashMap<>();

        if (request.queryParams().contains("emailAddress") && !Singleton.getHelpers().isNullOrWhitespace(request.queryParams("emailAddress"))) {
            // Spambot, so just pretend it worked
            return new ModelAndView(map, "suggest_success.ftl");
        }

        boolean isValid = true;
        List<String> validationErrors = new ArrayList<>();

        // Validate
        String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);

        if (!request.queryParams().contains("siteUrl") || !urlValidator.isValid(request.queryParams("siteUrl"))) {
            isValid = false;
            validationErrors.add("The URL " + request.queryParams("siteUrl") + " appears to be invalid.");
        }


        if (!isValid) {
            map.put("validationErrors", validationErrors);
            return new ModelAndView(map, "suggest.ftl" );
        }

        return new ModelAndView(map, "suggest_success.ftl");
    }
}
