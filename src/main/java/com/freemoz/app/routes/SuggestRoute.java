package com.freemoz.app.routes;

import com.freemoz.app.config.Values;
import com.freemoz.app.dto.SubmissionDTO;
import com.freemoz.app.service.Singleton;
import org.apache.commons.validator.routines.UrlValidator;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.*;

public class SuggestRoute {

    public static ModelAndView suggest(Request request, Response response) {
        Map<String, Object> map = new HashMap<>();

        boolean isValid = true;
        List<String> validationErrors = new ArrayList<>();

        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);

        String siteUrl = getStringValue(request, "siteUrl");
        String siteTitle = getStringValue(request, "siteTitle");
        String siteDescription = getStringValue(request, "siteDescription");
        String siteTags = getStringValue(request, "siteTags");
        String emailAddress = getStringValue(request, "emailAddress");
        boolean haveConfirmed = request.queryParams().contains("haveConfirmed");

        if (!urlValidator.isValid(siteUrl)) {
            isValid = false;
            validationErrors.add("Site URL " + siteUrl + " appears to be invalid. Enter a valid URL into this field.");
        }

        if (siteUrl.length() > 1000) {
            isValid = false;
            validationErrors.add("Site URL cannot be over 255 characters. You have entered " + siteUrl.length() + " characters.");
        }

        if (Singleton.getHelpers().isNullOrWhitespace(siteTitle)) {
            isValid = false;
            validationErrors.add("Site title cannot be empty. Enter a value into this field.");
        }

        if (siteTitle.length() > 255) {
            isValid = false;
            validationErrors.add("Site title cannot be over 255 characters. You have entered " + siteTitle.length() + " characters.");
        }

        if (Singleton.getHelpers().isNullOrWhitespace(siteDescription)) {
            isValid = false;
            validationErrors.add("Site description cannot be empty. Enter a value into this field.");
        }

        if (siteDescription.length() > 1200) {
            isValid = false;
            validationErrors.add("Site description cannot be over 1200 characters. You have entered " + siteDescription.length() + " characters.");
        }

        if (siteTags.length() > 50) {
            isValid = false;
            validationErrors.add("Site tags cannot be over 50 characters. You have entered " + siteTags.length() + " characters.");
        }

        if (!haveConfirmed) {
            isValid = false;
            validationErrors.add("You must confirm licence agreement.");
        }

        if (!isValid) {
            map.put("validationErrors", validationErrors);
            map.put("siteUrl", siteUrl);
            map.put("siteTitle", siteTitle);
            map.put("siteDescription", siteDescription);
            map.put("siteTags", siteTags);
            map.put("emailAddress", emailAddress);

            return new ModelAndView(map, "suggest.ftl" );
        }

        if (!Singleton.getHelpers().isNullOrWhitespace(emailAddress)) {
            // Probably an automated spam-bot so just pretend it worked
            return new ModelAndView(map, "suggest_success.ftl");
        }

        // Actually add things here

        SubmissionDTO submissionDTO = new SubmissionDTO(siteUrl, siteTitle, siteDescription, "", siteTags);

        return new ModelAndView(map, "suggest_success.ftl");
    }

    public static String getStringValue(Request request, String value) {
        if (request.queryParams().contains(value)) {
            return request.queryParams(value);
        }

        return Values.EMPTY_STRING;
    }
}
