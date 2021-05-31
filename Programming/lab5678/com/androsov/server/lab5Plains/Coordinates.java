package com.androsov.server.lab5Plains;
import com.androsov.server.productManagment.exceptions.ContentException;

public class Coordinates {
    public Coordinates(double x, double y) throws ContentException {
        this.x = x;
        this.y = y;
    }

    private final double x;
    private final Double y;

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
