package com.example.seeker.Model.Responses;


import com.example.seeker.Model.Category;

import java.util.List;

public class CategoryResponse {

    private int status;
    private String msg;
    private List<Category> categoryList;

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

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", categoryList=" + categoryList +
                '}';
    }
}
