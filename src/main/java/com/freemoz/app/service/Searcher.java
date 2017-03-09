package com.freemoz.app.service;


import com.freemoz.app.config.Values;
import com.freemoz.app.util.Properties;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Searcher {

    private int PAGELIMIT = 20;

    public void search(String queryString, int page) {
        try {
            IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(Properties.getProperties().getProperty(Values.INDEX_LOCATION, Values.DEFAULT_INDEX_LOCATION))));
            IndexSearcher searcher = new IndexSearcher(reader);

            Analyzer analyzer = new StandardAnalyzer();

            // Search over the titles only for the moment
            QueryParser parser = new QueryParser(Values.TITLE, analyzer);

            Query query = parser.parse(queryString);

            this.doPagingSearch(reader, searcher, query, page);
            reader.close();
        }
        catch(Exception ex) {}
        //return searchResult;
    }

    public void doPagingSearch(IndexReader reader, IndexSearcher searcher, Query query, int page) throws IOException {
        TopDocs results = searcher.search(query, 20 * this.PAGELIMIT); // 20 pages worth of documents
        ScoreDoc[] hits = results.scoreDocs;

        int numTotalHits = results.totalHits;
        int start = this.PAGELIMIT * page;
        int end = Math.min(numTotalHits, (this.PAGELIMIT * (page + 1)));
        int noPages = numTotalHits / this.PAGELIMIT;

        if (noPages > 20) {
            noPages = 19;
        }

        List<Integer> pages = this.calculatePages(numTotalHits, noPages);


        for (int i = start; i < end; i++) {
            Document doc = searcher.doc(hits[i].doc);
            String filepath = doc.get(Values.PATH);

            // Get the content out of database
        }

        //return new SearchResult(numTotalHits, page, query.toString(), codeResults, pages, codeFacetLanguages, repoFacetLanguages, repoFacetOwner);
    }

    public List<Integer> calculatePages(int numTotalHits, int noPages) {
        List<Integer> pages = new ArrayList<>();
        if (numTotalHits != 0) {

            // Account for off by 1 errors
            if (numTotalHits % 10 == 0) {
                noPages -= 1;
            }

            for (int i = 0; i <= noPages; i++) {
                pages.add(i);
            }
        }
        return pages;
    }
}
