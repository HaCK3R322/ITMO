package com.androsov.server.CommandMagment;

import com.androsov.server.lab5Plains.Product;

import java.util.List;

public abstract class ListCommand implements Command {
    public List<Product> list;

    public String name;
    public String description;

    public String getName() {
        return name;
    }
    public String getDescription() {return description; }
}
