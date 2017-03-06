package com.freemoz.app;

import com.freemoz.app.config.Values;
import com.freemoz.app.routes.EditorRoute;
import com.freemoz.app.service.Singleton;
import com.freemoz.app.util.Helpers;
import com.freemoz.app.util.Properties;
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
        // Database migrations happen before we start
        preStart();

        Spark.port(getServerPort());
        Spark.staticFileLocation("/public");

        get("/", (request, response) -> {
            return new ModelAndView(null, "index.ftl");
        }, new FreeMarkerEngine());


        get( "/login/", (EditorRoute::login), new FreeMarkerEngine());
        post( "/login/", (EditorRoute::doLogin), new FreeMarkerEngine());
        get( "/logout/", (EditorRoute::logout), new FreeMarkerEngine());
    }

    private static int getServerPort() {
        return Helpers.tryParseInt(Properties.getProperties().getProperty(Values.SERVER_PORT, "" + Values.DEFAULT_SERVER_PORT), Values.DEFAULT_SERVER_PORT);
    }

    private static void preStart() {
        // Setup database
        Singleton.getUserDAO().createTableIfMissing();
    }
}
