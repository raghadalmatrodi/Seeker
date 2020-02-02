package com.example.seeker.Model;

public class UserResponse {

    private int status;
    private String msg;

    private User user;

    public int getStatus() {
        return status;
    }//End of getStatus()

    public void setStatus(int status) {
        this.status = status;
    }//End of setStatus()

    public String getMsg() {
        return msg;
    }//End of getMsg()

    public void setMsg(String msg) {
        this.msg = msg;
    }//End of setMsg()

    public User getUser() {
        return user;
    }//End of getUser()

    public void setUser(User user) {
        this.user = user;
    }//End of setUser()

    @Override
    public String toString() {
        return "UserResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", user=" + user +
                '}';
    }//End of toString()

}//End of class UserResponse
