package lele.e_learning.activity.entity;

import java.util.ArrayList;

public class TextChapter {
    String name;
    ArrayList<TextSection> textSections;

    public TextChapter(String name, ArrayList<TextSection> textSections) {
        this.name = name;
        this.textSections = textSections;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TextSection> getTextSections() {
        return textSections;
    }

    public void setTextSections(ArrayList<TextSection> textSections) {
        this.textSections = textSections;
    }

    @Override
    public String toString() {
        return "TextChapter{" +
                "name='" + name + '\'' +
                ", textSections=" + textSections.toString() +
                '}';
    }
}
