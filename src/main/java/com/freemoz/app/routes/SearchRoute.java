package com.freemoz.app.routes;


import com.freemoz.app.config.Values;
import com.freemoz.app.dto.SearchResult;
import com.freemoz.app.service.Singleton;
import com.freemoz.app.util.Helpers;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class SearchRoute {
    public static ModelAndView search(Request request, Response response) {
        Map<String, Object> map = new HashMap<>();

        int page = 0;
        String query = Values.EMPTY_STRING;

        if (request.queryParams().contains("p")) {
            page = Helpers.tryParseInt(request.queryParams("p"), 0);
        }

        if (request.queryParams().contains("q")) {
            query = request.queryParams("q");
        }

        SearchResult searchResult = Singleton.getSearcher().search(query, page);

        map.put("searchResult", searchResult);
        return new ModelAndView(map, "search.ftl");
    }
}
