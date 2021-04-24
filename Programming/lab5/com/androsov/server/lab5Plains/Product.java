package com.androsov.server.lab5Plains;

import com.androsov.server.productManagment.exceptions.ContentException;

import java.time.LocalDateTime;
import java.util.List;

public class Product implements Comparable<Product> {
    public Product(long id,
                    String name,
                    Coordinates coordinates,
                    java.time.LocalDateTime creationDate,
                    Integer price,
                    String partNumber,
                    Float manufactureCost,
                    UnitOfMeasure unitOfMeasure,
                    Person owner) throws ContentException {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
    }

    private long id; //g/Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //f/Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //f/Поле не может быть null
    private java.time.LocalDateTime creationDate; //g/Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer price; //f/Поле может быть null, Значение поля должно быть больше 0
    private String partNumber; //f/Строка не может быть пустой, Значение этого поля должно быть уникальным, Поле не может быть null
    private Float manufactureCost; //f/Поле может быть null
    private UnitOfMeasure unitOfMeasure; //f/Поле не может быть null
    private Person owner; //f + g/Поле может быть null

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getPrice() {
        return price;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public Float getManufactureCost() {
        return manufactureCost;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return ("product id: " + id + "\n" +
                "   name: " + name + "\n" +
                "   coordinates: " + coordinates.getX() + " , " + coordinates.getY() + "\n" +
                "   creation date: " + creationDate.toString() + "\n" +
                "   price: " + price + "\n" +
                "   part number: " + partNumber + "\n" +
                "   manufacture cost: " + manufactureCost + "\n" +
                "   unit of measure: " + unitOfMeasure + "\n" +
                "   owner info: " + "\n" +
                owner.toString()
        );
    }

    @Override
    public int compareTo(Product p) {
        return name.compareTo(p.getName()); // сортировка имени
    }
}
