package com.example.seeker.Model;

public class SkillRecyclerView {

    private long id;
    private String name;
    private boolean isSelected;

    public SkillRecyclerView(long id, String name, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "SkillRecyclerView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
