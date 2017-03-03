package com.freemoz.app.routes;


import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class EditorRoute {
    public static ModelAndView login(Request request, Response response) {
        if (getAuthenticatedUser(request) != null) {
            response.redirect("/");
            halt();
            return null;
        }

        return new ModelAndView(null, "login.ftl");
    }

    public static ModelAndView doLogin(Request request, Response response) {
        if (getAuthenticatedUser(request) != null) {
            response.redirect("/");
            halt();
            return null;
        }

        Map<String, Object> sparkModel = new HashMap<>();

        if (request.queryParams().contains("loginPassword")) {
            addAuthenticatedUser(request);
            response.redirect("/");
            halt();
        }


        return new ModelAndView(sparkModel, "login.ftl");
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
