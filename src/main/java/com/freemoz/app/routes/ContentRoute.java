package com.freemoz.app.routes;


import com.freemoz.app.config.Values;
import com.freemoz.app.service.Singleton;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentRoute {
    public static ModelAndView getCategory(Request request, Response response, String category) {
        Map<String, Object> map = new HashMap<>();

        String searchCategory = category;
        List<String> splat = Arrays.asList(request.splat());
        if (!splat.isEmpty()) {
            searchCategory = category + "/" + String.join("/", splat);
        }

        List<String> subcategories = Singleton.getContentDAO().getSubcategories(searchCategory);
        
        map.put("categoryName", category);
        map.put("subCategories", subcategories);

        return new ModelAndView(map, "category.ftl");
    }
}
