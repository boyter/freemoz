package com.freemoz.app.routes;

import com.freemoz.app.config.Values;
import com.freemoz.app.dto.SubmissionDTO;
import com.freemoz.app.service.Singleton;
import com.freemoz.app.util.*;
import com.freemoz.app.util.Properties;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.security.GeneralSecurityException;
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
        String recaptchaResponse = getStringValue(request, "g-recaptcha-response");
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

        // Check CAPTCHA
        HttpRequest httpRequest = new HttpRequest("https://www.google.com/recaptcha/api/siteverify");
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("secret", Properties.getProperties().getProperty(Values.RECAPTHCA_SECRET_KEY, Values.DEFAULT_RECAPTHCA_SECRET_KEY)));
        urlParameters.add(new BasicNameValuePair("response", recaptchaResponse));
        String googleRecaptchaResponse = null;
        try {
            googleRecaptchaResponse = httpRequest.executePost(urlParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Singleton.getHelpers().isNullOrWhitespace(googleRecaptchaResponse)) {
            isValid = false;
            validationErrors.add("There was a problem validating against the reCAPTCHA service. Please try again.");
        }

        if (googleRecaptchaResponse.contains("false")) {
            isValid = false;
            validationErrors.add("It appears that you were flagged as a bot by reCAPTCHA. Please try again.");
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

        // TODO Add to in memory queue to be flushed to database queue through backend process
        boolean suceess = Singleton.getQueueDAO().addSubmission(submissionDTO);

        if (!suceess) {
            validationErrors.add("Sorry but it appears we were unable to save your submission. Please try again in a few minutes.");
            map.put("validationErrors", validationErrors);
            map.put("siteUrl", siteUrl);
            map.put("siteTitle", siteTitle);
            map.put("siteDescription", siteDescription);
            map.put("siteTags", siteTags);
            map.put("emailAddress", emailAddress);

            return new ModelAndView(map, "suggest.ftl" );
        }

        return new ModelAndView(map, "suggest_success.ftl");
    }

    public static String getStringValue(Request request, String value) {
        if (request.queryParams().contains(value)) {
            return request.queryParams(value);
        }

        return Values.EMPTY_STRING;
    }
}
