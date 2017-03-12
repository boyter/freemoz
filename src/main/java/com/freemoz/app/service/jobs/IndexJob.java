package com.freemoz.app.service.jobs;

import com.freemoz.app.dao.ContentDAO;
import com.freemoz.app.dto.ContentDTO;
import com.freemoz.app.service.Indexer;
import com.freemoz.app.service.Singleton;
import org.quartz.*;

import java.io.IOException;
import java.util.List;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class IndexJob implements Job {

    private final Indexer indexer;
    private final ContentDAO contentDAO;

    public IndexJob() {
        this(Singleton.getIndexer(), Singleton.getContentDAO());
    }

    public IndexJob(Indexer indexer, ContentDAO contentDAO) {
        this.indexer = indexer;
        this.contentDAO = contentDAO;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        int offset = 0;
        int pagesize = 10000;
        List<ContentDTO> sitesPaged = this.contentDAO.getAllSitesPaged(offset, pagesize);

        while(!sitesPaged.isEmpty()) {
            try {
                indexer.indexDocuments(sitesPaged);
            } catch (IOException e) {}
            offset += pagesize;
            sitesPaged = this.contentDAO.getAllSitesPaged(offset, pagesize);
        }
    }
}