package com.freemoz.app;

import com.freemoz.app.service.EditorService;
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

        Spark.port(8080);
        Spark.staticFileLocation("/public");

        get("/", (request, response) -> {
            return new ModelAndView(null, "index.ftl");
        }, new FreeMarkerEngine());


        get( "/login/", ((request, response) -> EditorService.login(request, response)), new FreeMarkerEngine());
        post( "/login/", ((request, response) -> EditorService.doLogin(request, response)), new FreeMarkerEngine());
    }
}
