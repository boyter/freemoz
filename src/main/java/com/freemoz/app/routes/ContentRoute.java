package com.freemoz.app.routes;


import com.freemoz.app.dto.ContentDTO;
import com.freemoz.app.service.Singleton;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.*;

public class ContentRoute {
    public static ModelAndView getCategory(Request request, Response response, String category) {
        Map<String, Object> map = new HashMap<>();

        String searchCategory = category;
        List<String> splat = Arrays.asList(request.splat());
        if (!splat.isEmpty()) {
            searchCategory = category + "/" + String.join("/", splat);
        }

        List<String> subcategories = Singleton.getContentDAO().getSubcategories(searchCategory);
        List<ContentDTO> sites = Singleton.getContentDAO().getSites(searchCategory);


        String[] breadCrumb = new String[0];
        if (!splat.isEmpty()) {
            breadCrumb = splat.get(0).split("/");
        }

        map.put("breadCrumb", breadCrumb);
        map.put("categoryName", category);
        map.put("subCategories", subcategories);
        map.put("sites", sites);

        return new ModelAndView(map, "category.ftl");
    }
}
