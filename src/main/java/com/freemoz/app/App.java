package com.freemoz.app;

import com.freemoz.app.routes.EditorRoute;
import com.freemoz.app.service.Singleton;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Main entry point for the application.
 */
public class App {
    public static void main( String[] args ) {
        preStart();
        Spark.port(8080);
        Spark.staticFileLocation("/public");

        get("/", (request, response) -> {
            return new ModelAndView(null, "index.ftl");
        }, new FreeMarkerEngine());


        get( "/login/", (EditorRoute::login), new FreeMarkerEngine());
        post( "/login/", (EditorRoute::doLogin), new FreeMarkerEngine());
    }

    private static void preStart() {
        // Setup database
        Singleton.getUserDAO().createTableIfMissing();
    }
}
