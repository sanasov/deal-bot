package ru.igrey.dev.constant;

public enum Command {
    OPERATIONS("/operations"), DICTIONARIES("/dictionaries");

    Command(String title) {
        this.title = title;
    }

    public final String title;
}
