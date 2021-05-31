package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.Command;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class AverageOfManufactureCost extends ListCommand implements Command {
    final MessengersHandler messenger;
    final List<Product> list;

    public AverageOfManufactureCost(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "average_of_manufacture_cost";
        description = "show average of manufacture cost.";
        argumentFormat = "void";
        userAccessible = true;
    }

    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        Response response = new ResponseImpl(request.getUser());
        float average = (float)list.stream()
                .map(Product::getManufactureCost)
                .mapToDouble(cost -> cost)
                .sum();

        response.addData(average);
        return response;
    }

    public String execute(String[] args) {
        String result = "";

        float average = 0;

        average = (float)list.stream()
                .map(Product::getManufactureCost)
                .mapToDouble(cost -> cost)
                .sum();

        result += average;

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.AverageOfManufactureCost().description;
    }
}