package edu.wwu.csci412.fetch_app;

public class Item {
    private int id = 0;
    private int listId = 0;
    private String name = "";

    public Item(int inId, int inListId, String inName) {
        id = inId;
        listId = inListId;
        name = inName;
    }

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }
}
