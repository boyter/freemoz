package com.freemoz.app.dao;

import com.freemoz.app.dto.SubmissionDTO;
import com.freemoz.app.dto.UserDTO;
import junit.framework.TestCase;

public class QueueDAOTest extends TestCase {
    public void testGetNextQueue() {
        QueueDAO queueDAO = new QueueDAO();


        UserDTO userDTO = new UserDTO("admin", "admin", null);
        SubmissionDTO nextSubmission = queueDAO.getNextSubmission(userDTO);

        System.out.println(nextSubmission);
    }
}
