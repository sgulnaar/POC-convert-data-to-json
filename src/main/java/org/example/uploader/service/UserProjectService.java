package org.example.uploader.service;

import org.example.uploader.dto.UserProject;
import org.example.uploader.dto.UserProjectFolderCreateResponse;

public interface UserProjectService {

    UserProjectFolderCreateResponse createUserProject(UserProject userProject);
}
