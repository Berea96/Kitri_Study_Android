package com.example4.bereakj.dbtest;

public class Member {
    private int _id;
    private String name;

    public Member() {
    }

    public Member(int _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public int getId() {
        return _id;
    }
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this._id = _id;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
