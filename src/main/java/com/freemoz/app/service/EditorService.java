package com.freemoz.app.service;


import spark.ModelAndView;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class EditorService {
    public static ModelAndView login(Request request, Response response) {
        if (getAuthenticatedUser(request) != null) {
            response.redirect("/");
            halt();
            return null;
        }

        return new ModelAndView(null, "login.ftl");
    }

    private static void addAuthenticatedUser(Request request) {
        request.session().attribute("", true);
    }

    private static void removeAuthenticatedUser(Request request) {
        request.session().removeAttribute("");
    }

    private static Object getAuthenticatedUser(Request request) {
        return request.session().attribute("");
    }
}
