package com.freemoz.app.dto;


import java.util.List;

public class SearchResult {
    private final List<Integer> pages;
    private int numTotalHits;
    private int page;
    private String query;
    private List<ContentDTO> results;

    public SearchResult(int numTotalHits, int page, List<Integer> pages, String query, List<ContentDTO> results) {
        this.numTotalHits = numTotalHits;
        this.page = page;
        this.pages = pages;
        this.query = query;
        this.results = results;
    }

    public int getNumTotalHits() {
        return numTotalHits;
    }

    public int getPage() {
        return page;
    }

    public String getQuery() {
        return query;
    }

    public List<ContentDTO> getResults() {
        return results;
    }

    public void setResults(List<ContentDTO> results) {
        this.results = results;
    }

    public List<Integer> getPages() {
        return pages;
    }
}
