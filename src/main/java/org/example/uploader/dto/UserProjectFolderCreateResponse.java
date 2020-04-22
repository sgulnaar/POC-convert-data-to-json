package org.example.uploader.dto;

public class UserProjectFolderCreateResponse {
    private String message;

    public UserProjectFolderCreateResponse() {
    }

    public UserProjectFolderCreateResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserProjectFolderCreateResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
