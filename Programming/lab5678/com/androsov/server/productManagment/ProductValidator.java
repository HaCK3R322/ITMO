package com.androsov.server.productManagment;

import com.androsov.server.lab5Plains.Coordinates;
import com.androsov.server.lab5Plains.Person;
import com.androsov.server.lab5Plains.UnitOfMeasure;
import com.androsov.server.productManagment.exceptions.ContentException;

import java.util.List;

public class ProductValidator {
    public static void validateProduct(long id,
                                       String name,
                                       Coordinates coordinates,
                                       java.time.LocalDateTime creationDate,
                                       Integer price,
                                       String partNumber,
                                       List<String> usedPartNumbers,
                                       Float manufactureCost,
                                       UnitOfMeasure unitOfMeasure,
                                       Person owner) throws ContentException {
        if(name.equals("")) throw new ContentException("Can not create product (id) " + id + " from file: name is empty");
        if(coordinates.getX() > (double)653) throw new ContentException("Can not create product " + name + " from file: x coordinate is bigger than 653");
        if(price == 0) throw new ContentException("Can not create product " + name + " from file: price == 0");
        boolean partNumberAlreadyUsed = false;
        for (String usedPartNumber : usedPartNumbers) {
            if (partNumber.equals(usedPartNumber)) {
                partNumberAlreadyUsed = true;
                break;
            }
        }
        if(partNumber.equals("") || partNumberAlreadyUsed) throw new ContentException("Can not create product " + name + " from file: part number is empty or already used");
        if(manufactureCost == null) throw new ContentException("Can not create product " + name + " from file: manufacture cost == null");
        if(unitOfMeasure == null) throw new ContentException("Can not create product " + name + " from file: unit of measure == null");
        if(owner == null) throw new ContentException("Can not create product " + name + " from file: owner field empty");
        try {
            owner.CheckParams();
        } catch (ContentException e) {
            throw new ContentException("Can not create product " + name + " from file:" + e.getMessage());
        }
    }
}
