package com.freemoz.app.service;


import com.freemoz.app.config.Values;
import com.freemoz.app.dao.ContentDAO;
import com.freemoz.app.dto.ContentDTO;
import com.freemoz.app.dto.SearchResult;
import com.freemoz.app.util.Helpers;
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

    private final ContentDAO contentDAO;
    private int PAGELIMIT = 20;

    public Searcher() {
        this.contentDAO = Singleton.getContentDAO();
    }

    public SearchResult search(String queryString, int page) {
        SearchResult searchResult = null;

        try {
            IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(Properties.getProperties().getProperty(Values.INDEX_LOCATION, Values.DEFAULT_INDEX_LOCATION))));
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();

            // Search over the titles only for the moment
            QueryParser parser = new QueryParser(Values.TITLE, analyzer);
            Query query = parser.parse(queryString);

            searchResult = this.doPagingSearch(reader, searcher, query, queryString, page);
            reader.close();
        }
        catch(Exception ex) {}

        return searchResult;
    }

    public SearchResult doPagingSearch(IndexReader reader, IndexSearcher searcher, Query query, String queryString, int page) throws IOException {
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
        List<ContentDTO> contentDTOList = new ArrayList<>();

        for (int i = start; i < end; i++) {
            Document doc = searcher.doc(hits[i].doc);
            String filepath = doc.get(Values.PATH);

            // Get the content out of database
            ContentDTO byId = this.contentDAO.getById(Helpers.tryParseInt(filepath, -1));
            if (byId != null) {
                contentDTOList.add(byId);
            }
        }

        return new SearchResult(numTotalHits, page, pages, queryString, contentDTOList);
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
