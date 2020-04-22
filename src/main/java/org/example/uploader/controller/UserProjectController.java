package org.example.uploader.controller;

import org.example.uploader.dto.UserProject;
import org.example.uploader.dto.UserProjectFolderCreateResponse;
import org.example.uploader.service.UserProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userproject")
public class UserProjectController {

    @Autowired
    private UserProjectService userProjectService;

    @PostMapping
    public ResponseEntity<UserProjectFolderCreateResponse> createUserProjectFolder(@RequestBody UserProject userProject) {
        UserProjectFolderCreateResponse response =
                userProjectService.createUserProject(userProject);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
