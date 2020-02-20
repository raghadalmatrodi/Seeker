package com.example.seeker.Model.Responses;

import com.example.seeker.Model.Project;

import java.util.List;

public class ProjectResponse {

    private int status;
    private String msg;
    private List<Project> projectList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    @Override
    public String toString() {
        return "ProjectResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", projectList=" + projectList +
                '}';
    }
}
