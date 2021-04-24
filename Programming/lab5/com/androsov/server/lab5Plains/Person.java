package com.androsov.server.lab5Plains;

import com.androsov.server.productManagment.exceptions.ContentException;

public class Person {
    public Person(String name, long height, Color eyeColor, Color hairColor, Country nationality) {
        this.name = name;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    private String name; //Поле не может быть null, Строка не может быть пустой
    private long height; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле не может быть null

    public void CheckParams() throws ContentException {
        if(name == null || name.equals(""))
            throw new ContentException("Name cannot be null or empty.");
        if (height <= 0)
            throw new ContentException("Height cannot be zero.");
        if (eyeColor == null)
            throw new ContentException("Eye color cannot be null.");
        if (hairColor == null)
            throw new ContentException("Hair color cannot be null.");
        if (nationality == null)
            throw new ContentException("Nationality cannot be null.");
    }

    @Override
    public String toString() {
        return ("       name: " + name + "\n" +
                "       height: " + height + "\n" +
                "       eye color: " + eyeColor.toString() + "\n" +
                "       hair color: " + hairColor.toString() + "\n" +
                "       country: " + nationality);
    }
}
