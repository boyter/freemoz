package com.freemoz.app.routes;


import com.freemoz.app.dto.UserDTO;
import com.freemoz.app.service.EditorService;
import com.freemoz.app.service.Singleton;
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

        if (!request.queryParams().contains("loginUsername") || !request.queryParams().contains("loginPassword")) {
            response.redirect("/login/");
            halt();
            return null;
        }

        UserDTO user = Singleton.getEditorService().loginUser(request.queryParams("loginUsername"), request.queryParams("loginPassword"));

        if (user == null) {
            response.redirect("/login/");
            halt();
            return null;
        }

        addAuthenticatedUser(request, user);

        response.redirect("/");
        halt();
        return null;
    }

    public static ModelAndView logout(Request request, Response response) {
        removeAuthenticatedUser(request);

        response.redirect("/");
        halt();
        return null;
    }

    private static void addAuthenticatedUser(Request request, UserDTO user) {
        request.session().attribute("user", user.getUsername());
    }

    private static void removeAuthenticatedUser(Request request) {
        request.session().removeAttribute("user");
    }

    private static String getAuthenticatedUser(Request request) {
        return request.session().attribute("user");
    }
}
