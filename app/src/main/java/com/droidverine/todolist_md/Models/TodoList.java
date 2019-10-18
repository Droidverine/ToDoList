package com.droidverine.todolist_md.Models;

public class TodoList {
    private String Taskname,Taskdate,Taskstatus,TaskCategory;


    public TodoList() {
    }

    public TodoList(String taskname, String taskdate, String taskstatus) {
        Taskname = taskname;
        Taskdate = taskdate;
        Taskstatus = taskstatus;
    }

    public TodoList(String taskname, String taskdate, String taskstatus, String taskCategory) {
        Taskname = taskname;
        Taskdate = taskdate;
        Taskstatus = taskstatus;
        TaskCategory = taskCategory;
    }

    public String getTaskCategory() {
        return TaskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        TaskCategory = taskCategory;
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
