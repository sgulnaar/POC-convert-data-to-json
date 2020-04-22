package org.example.uploader.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.uploader.dto.UserProject;
import org.example.uploader.dto.UserProjectFolderCreateResponse;
import org.example.uploader.exception.FileStorageException;
import org.example.uploader.service.UserProjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserProjectServiceImpl implements UserProjectService {
    private static Logger log = LogManager.getLogger(UserProjectServiceImpl.class);

    private final Path projectDirectoryPath;

    public UserProjectServiceImpl(@Value("${project.dir}") String projectDirectory) {
        if(StringUtils.isEmpty(projectDirectory) || projectDirectory.trim().isEmpty()) {
            projectDirectoryPath =
                    FileSystems.getDefault().getPath("").toAbsolutePath();
        } else {
            projectDirectoryPath =
                    Paths.get(projectDirectory)
                            .toAbsolutePath().normalize();
        }
        try {
            Files.createDirectories(this.projectDirectoryPath);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the project's folder will be created.", ex);
        }
    }

    @Override
    public UserProjectFolderCreateResponse createUserProject(UserProject userProject) {
        UserProjectFolderCreateResponse response =
                new UserProjectFolderCreateResponse();
        try {
            createUserFolderIfNotExists(userProject.getUserName());
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return response;
        }
        boolean isUserProjectAlreadyExists = false;
        try {
            isUserProjectAlreadyExists = checkIfUserProjectAlreadyExists(userProject);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return response;
        }
        if (isUserProjectAlreadyExists) {
            response.setMessage("Project folder already present. Please use different name for the project");
            return response;
        }
        String userDirName = StringUtils.cleanPath(userProject.getUserName());
        String userProjectDirName = StringUtils.cleanPath(userProject.getProjectName());
        Path userDirPath = this.projectDirectoryPath.resolve(userDirName);
        Path projectDirPath =
                userDirPath.resolve(userProjectDirName).normalize();
        try {
            Files.createDirectories(projectDirPath);
            Path configurationsDirPath = projectDirPath.resolve("configurations");
            Files.createDirectories(configurationsDirPath);
            Path testingDirPath = projectDirPath.resolve("testing");
            Files.createDirectories(testingDirPath);
            Path scenariosDirPath = projectDirPath.resolve("scenarios");
            Files.createDirectories(scenariosDirPath);
            Path testArchiveDirPath = projectDirPath.resolve("test archive");
            Files.createDirectories(testArchiveDirPath);
        } catch (Exception ex) {
            log.error("Could not create the user project directory", ex);
            response.setMessage("Could not create the user project directory");
            return response;
        }
        response.setMessage("Created a folder. Your subfolders are configurations, scenarios, test archive, testing");
        return response;
    }

    private void createUserFolderIfNotExists(String userName) {
        String userDirName = StringUtils.cleanPath(userName);
        try {
            // Check if the file's name contains invalid characters
            if (userDirName.contains("..")) {
                throw new FileStorageException("Sorry! Username contains invalid path sequence " + userDirName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path userDirPath = this.projectDirectoryPath.resolve(userDirName);
            Files.createDirectories(userDirPath);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the user directory.", ex);
        }
    }

    private boolean checkIfUserProjectAlreadyExists(UserProject userProject) {
        String userDirName = StringUtils.cleanPath(userProject.getUserName());
        String userProjectDirName = StringUtils.cleanPath(userProject.getProjectName());
        if (userProjectDirName.contains("..")) {
            throw new FileStorageException("Sorry! ProjectName contains invalid path sequence " + userProjectDirName);
        }
        try {
            Path userDirPath = this.projectDirectoryPath.resolve(userDirName);
            Path projectDirPath =
                    userDirPath.resolve(userProjectDirName).normalize();
            Resource resource = new UrlResource(projectDirPath.toUri());
            if (resource.exists()) {
                return true;
            }
        } catch (MalformedURLException ex) {
            return false;
        }
        return false;
    }
}
