package org.example.uploader.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.uploader.dto.UserProjectFolderCreateResponse;
import org.example.uploader.service.impl.UserProjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class UserProjectExceptionHandler {
    private static Logger log = LogManager.getLogger(UserProjectExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<UserProjectFolderCreateResponse> handleExceptions(Exception ex, WebRequest request) {
        log.error("<<Exceptions>>", ex);
        UserProjectFolderCreateResponse response =
                new UserProjectFolderCreateResponse("unknown error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
