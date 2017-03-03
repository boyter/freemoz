package com.freemoz.app.routes;


import com.freemoz.app.dto.UserDTO;
import com.freemoz.app.service.EditorService;
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

        if (!request.queryParams().contains("loginUsername") || !request.queryParams().contains("loginPassword")) {
            response.redirect("/login/");
            halt();
            return null;
        }

        String username = request.queryParams("loginUsername");
        String password = request.queryParams("loginPassword");

        EditorService editorService = new EditorService();

        UserDTO user = editorService.loginUser(username, password);

        if (user == null) {
            response.redirect("/login/");
            halt();
            return null;
        }

        addAuthenticatedUser(request);

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
