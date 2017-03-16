package com.freemoz.app.service;


import com.freemoz.app.config.IDatabaseConfig;
import com.freemoz.app.config.SQLiteDatabaseConfig;
import com.freemoz.app.config.Values;
import com.freemoz.app.dao.ContentDAO;
import com.freemoz.app.dao.QueueDAO;
import com.freemoz.app.dao.UserDAO;
import com.freemoz.app.dto.ContentDTO;
import com.freemoz.app.dto.SubmissionDTO;
import com.freemoz.app.util.Helpers;
import com.freemoz.app.util.Properties;
import org.eclipse.jetty.util.ConcurrentArrayQueue;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Queue;

public class Singleton {
    private static IDatabaseConfig userDatabaseConfig = null;
    private static IDatabaseConfig contentDatabaseConfig = null;
    private static IDatabaseConfig queueDatabaseConfig = null;
    private static UserDAO userDAO = null;
    private static ContentDAO contentDAO = null;
    private static QueueDAO queueDAO = null;
    private static Helpers helpers = null;
    private static Indexer indexer = null;
    private static Searcher searcher = null;
    private static JobService jobService = null;
    private static EditorService editorService = null;
    private static SubmissionService submissionService = null;
    private static Scheduler scheduler;
    private static Queue<SubmissionDTO> submissionQueue = null;

    public static synchronized Scheduler getScheduler() {
        if (scheduler == null) {
            try {
                SchedulerFactory sf = new StdSchedulerFactory();
                scheduler = sf.getScheduler();
            } catch (SchedulerException ex) {}
        }

        return scheduler;
    }

    public synchronized static SubmissionService getSubmissionService() {
        if (submissionService == null) {
            submissionService = new SubmissionService();
        }

        return submissionService;
    }

    public synchronized static Queue<SubmissionDTO> getSubmissionQueue() {
        if (submissionQueue == null) {
            submissionQueue = new ConcurrentArrayQueue<>();
        }

        return submissionQueue;
    }

    public synchronized static IDatabaseConfig getUserDatabaseConfig() {
        if (userDatabaseConfig == null) {
            String sqliteFile = (String) Properties.getProperties().getOrDefault(Values.USER_SQLITE_FILE, Values.DEFAULT_USER_SQLITE_FILE);
            userDatabaseConfig = new SQLiteDatabaseConfig(sqliteFile);
        }

        return userDatabaseConfig;
    }

    public synchronized static IDatabaseConfig getContentDatabaseConfig() {
        if (contentDatabaseConfig == null) {
            String sqliteFile = (String) Properties.getProperties().getOrDefault(Values.CONTENT_SQLITE_FILE, Values.DEFAULT_CONTENT_SQLITE_FILE);
            contentDatabaseConfig = new SQLiteDatabaseConfig(sqliteFile);
        }

        return contentDatabaseConfig;
    }

    public synchronized static IDatabaseConfig getSubmissionDatabaseConfig() {
        if (queueDatabaseConfig == null) {
            String sqliteFile = (String) Properties.getProperties().getOrDefault(Values.QUEUE_SQLITE_FILE, Values.DEFAULT_QUEUE_SQLITE_FILE);
            queueDatabaseConfig = new SQLiteDatabaseConfig(sqliteFile);
        }

        return queueDatabaseConfig;
    }

    public synchronized static JobService getJobService() {
        if (jobService == null) {
            jobService = new JobService();
        }

        return jobService;
    }

    public synchronized static EditorService getEditorService() {
        if (editorService == null) {
            editorService = new EditorService();
        }

        return editorService;
    }

    public synchronized static UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }

        return userDAO;
    }

    public synchronized static ContentDAO getContentDAO() {
        if (contentDAO == null) {
            contentDAO = new ContentDAO();
        }

        return contentDAO;
    }

    public synchronized static QueueDAO getQueueDAO() {
        if (queueDAO == null) {
            queueDAO = new QueueDAO();
        }

        return queueDAO;
    }

    public synchronized static Helpers getHelpers() {
        if (helpers == null) {
            helpers = new Helpers();
        }

        return helpers;
    }

    public synchronized static Indexer getIndexer() {
        if (indexer == null) {
            indexer = new Indexer();
        }

        return indexer;
    }

    public synchronized static Searcher getSearcher() {
        if (searcher == null) {
            searcher = new Searcher();
        }

        return searcher;
    }
}
