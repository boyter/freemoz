package com.freemoz.app;

import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import static spark.Spark.get;

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


    }
}
