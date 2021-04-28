package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class AverageOfManufactureCost extends ListCommand {
    public AverageOfManufactureCost(List<Product> list) {
        this.list = list;

        name = "average_of_manufacture_cost";
        description = "show average of manufacture cost.";
    }

    public String execute(String[] args) {
        String result = "";

        float average = 0;
        for(int i = 0; i < list.size(); i++) {
            average += list.get(i).getManufactureCost();
        }
        average /= list.size();

        result += average;

        return result;
    }
}