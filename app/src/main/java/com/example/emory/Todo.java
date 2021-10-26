package com.example.emory;

public class Todo {
    private String name;
    private String deadline;
    private String note;

    public Todo(String name, String deadline, String note) {
        this.name = name;
        this.deadline = deadline;
        this.note = note;
    }

    public Todo(String name, String deadline) {
        this.name = name;
        this.deadline = deadline;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public String getDeadline() {
        return this.deadline;
    }

    public String getNote() {
        return this.note;
    }
}
