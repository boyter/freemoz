package com.freemoz.app.dao;

import com.freemoz.app.dto.SubmissionDTO;
import junit.framework.TestCase;

public class QueueDAOTest extends TestCase {
    public void testGetNextQueue() {
        QueueDAO queueDAO = new QueueDAO();

        SubmissionDTO nextSubmission = queueDAO.getNextSubmission();

        System.out.println(nextSubmission);
    }
}
