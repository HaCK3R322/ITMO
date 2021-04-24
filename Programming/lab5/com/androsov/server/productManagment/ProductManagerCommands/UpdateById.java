package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.InternetConnection.ServerIO;
import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;
import com.androsov.server.productManagment.exceptions.ContentException;

import javax.imageio.IIOException;
import java.io.IOException;

public class UpdateById extends ProductManagerCommand {
    ServerIO io;

    public UpdateById(ProductManager manager, ServerIO io) {
        super(manager);

        name = "update_by_id";
        description = "updates product with given id.";

        this.io = io;
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        if(args.length > 0) {
            long id = Long.parseLong(args[0]);

            try {
                try {
                    manager.getProductBuilder().usedPartNumbers.remove(manager.getProduct(id).getPartNumber());
                } catch (NullPointerException e) {
                    throw new ContentException("Product with such id doesnt exist.");
                }
                manager.update(id, manager.getProductBuilder().buildProduct(Add.createProductManually(manager, io), id));
            } catch (NumberFormatException e) {
                result = "Wrong id format. Please enter long-format argument";
            } catch (ContentException e) {
                result = "Product build exception: " + e.getMessage();
            } catch (IOException e) {
                result = "IOException: " + e.getMessage();
            }

        } else {
            result = "Please, enter id.";
        }

        return result;
    }
}
