package com.freemoz.app.routes;


import com.freemoz.app.service.Singleton;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentRoute {
    public static ModelAndView getCategory(Request request, Response response, String category) {

        List<String> subcategories = Singleton.getContentDAO().getSubcategories();
        Map<String, Object> map = new HashMap<>();

        map.put("categoryName", category);
        map.put("subCategories", subcategories);

        return new ModelAndView(map, "category.ftl");
    }
}
