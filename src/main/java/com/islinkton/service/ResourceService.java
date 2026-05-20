package com.islinkton.service;

import com.islinkton.dao.ResourceDAO;
import com.islinkton.model.Resource;
import java.util.List;

public class ResourceService {

    private ResourceDAO resourceDAO = new ResourceDAO();

    /**
     * Business logic layer for Resource
     */
    public boolean uploadResource(Resource resource) throws Exception {
        // Basic business validation
        if (resource.getTitle() == null || resource.getTitle().trim().isEmpty()) {
            throw new Exception("Resource title is required");
        }
        
        if (resource.getFilePath() == null || resource.getFilePath().trim().isEmpty()) {
            throw new Exception("File path is required");
        }

        // You can add more business rules here (e.g., file size check, allowed extensions, etc.)

        return resourceDAO.uploadResource(resource);
    }

    public List<Resource> getAllResources() throws Exception {
        return resourceDAO.getAllResources();
    }

    public List<Resource> getRecentResources() throws Exception {
        return resourceDAO.getRecentResources();
    }

    public Resource getResourceById(int id) throws Exception {
        return resourceDAO.getResourceById(id);
    }
}