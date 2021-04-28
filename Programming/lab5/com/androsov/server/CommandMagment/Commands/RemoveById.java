package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.productManagment.ProductBuilder;

import java.util.List;
import java.util.ListIterator;

public class RemoveById extends ListCommand {
    ProductBuilder productBuilder;

    public RemoveById(List<Product> list) {
        this.list = list;

        name = "remove";
        description = "Removes product with given id.";
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        if(args.length > 0) {
            try {
                long id = Long.parseLong(args[0]);

                ListIterator<Product> iter = list.listIterator();
                Product p;
                while ((p = iter.next()) != null) {
                    if(p.getId() == id) {
                        iter.remove();
                        productBuilder.usedPartNumbers.remove(p.getPartNumber());
                        break;
                    }
                }
                result = "Product with id " + id + " was removed.";
            } catch (NumberFormatException e) {
                result = "Wrong id format. Please enter long-format argument";
            }
        } else {
            result = "Please, enter id.";
        }

        return result;
    }
}
