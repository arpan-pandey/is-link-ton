package com.islinkton.service;

import com.islinkton.dao.ThreadDAO;
import com.islinkton.model.Thread;
import java.util.List;

public class ThreadService {

    private ThreadDAO threadDAO = new ThreadDAO();

    public boolean createThread(Thread thread) throws Exception {
        return threadDAO.createThread(thread);
    }

    public List<Thread> getAllApprovedThreads() throws Exception {
        return threadDAO.getAllApprovedThreads();
    }

    public Thread getThreadById(int id) throws Exception {
        return threadDAO.getThreadById(id);
    }

    public boolean approveThread(int threadId) throws Exception {
        return threadDAO.approveThread(threadId);
    }

    public boolean deleteThread(int threadId) throws Exception {
        return threadDAO.deleteThread(threadId);
    }
}