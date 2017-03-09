package com.freemoz.app.service;


import com.freemoz.app.config.Values;
import com.freemoz.app.dto.ContentDTO;
import com.freemoz.app.util.Properties;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Indexer {

    /**
     * Given a queue of documents to index, index them by popping the queue limited to 1000 items.
     * This method must be synchronized as we have not added any logic to deal with multiple threads writing to the
     * index.
     */
    public synchronized void indexDocuments(List<ContentDTO> contentDTOList) throws IOException {
        Directory indexDirectory = FSDirectory.open(Paths.get(Properties.getProperties().getProperty(Values.INDEX_LOCATION, Values.DEFAULT_INDEX_LOCATION)));

        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        IndexWriter writer = new IndexWriter(indexDirectory, indexWriterConfig);

        try {
            for(ContentDTO contentDTO: contentDTOList) {

                Document doc = new Document();
                // Path is the primary key for documents
                String primaryKey =  "" + contentDTO.getId();
                Field pathField = new StringField(Values.PATH, primaryKey, Field.Store.YES);
                doc.add(pathField);

                doc.add(new TextField("title", contentDTO.getTitle().toLowerCase(), Field.Store.NO));
                doc.add(new TextField("description", contentDTO.getDescription().toLowerCase(), Field.Store.NO));

                writer.updateDocument(new Term(Values.PATH, primaryKey), doc);
            }
        }
        finally {
            writer.close();
        }
    }
}
