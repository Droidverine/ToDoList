package com.droidverine.todolist_md.Models;

public class TodoList {
    String Taskname,Taskdate,Taskstatus;

    public TodoList() {
    }

    public TodoList(String taskname, String taskdate, String taskstatus) {
        Taskname = taskname;
        Taskdate = taskdate;
        Taskstatus = taskstatus;
    }

    public String getTaskname() {
        return Taskname;
    }

    public void setTaskname(String taskname) {
        Taskname = taskname;
    }

    public String getTaskdate() {
        return Taskdate;
    }

    public void setTaskdate(String taskdate) {
        Taskdate = taskdate;
    }

    public String getTaskstatus() {
        return Taskstatus;
    }

    public void setTaskstatus(String taskstatus) {
        Taskstatus = taskstatus;
    }
}
