package com.freemoz.app.service;

import com.freemoz.app.dto.SubmissionDTO;

import java.util.Queue;

public class SubmissionService {

    private final Queue<SubmissionDTO> submissionQueue;

    public SubmissionService() {
        this(Singleton.getSubmissionQueue());
    }

    public SubmissionService(Queue<SubmissionDTO> submissionQueue) {
        this.submissionQueue = submissionQueue;
    }

    public boolean enqueueSubmission(SubmissionDTO submissionDTO) {
        this.submissionQueue.add(submissionDTO);
        return true;
    }
}
