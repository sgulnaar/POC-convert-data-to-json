package org.example.uploader.dto;

public class UserProject {
    private String userName;
    private String projectName;

    public UserProject() {
    }

    public UserProject(String userName, String projectName) {
        this.userName = userName;
        this.projectName = projectName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "UserProject{" +
                "userName='" + userName + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
