package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.CommandHandler;
import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.InternetConnection.ServerIO;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.productManagment.ProductBuilder;
import com.androsov.server.productManagment.exceptions.ContentException;

import java.io.IOException;
import java.util.List;

public class UpdateById extends ListCommand {
    ServerIO io;
    ProductBuilder productBuilder;
    MessengersHandler messenger;

    Add add;

    public UpdateById(List<Product> list, ProductBuilder productBuilder, ServerIO io, MessengersHandler messenger) {
        this.list = list;
        this.productBuilder = productBuilder;
        this.io = io;
        this.messenger = messenger;

        add = new Add(list, productBuilder, io, messenger);

        name = "update_by_id";
        description = "Manual product update with given id.";
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        if(args.length > 0) {
            try {
                long id = Long.parseLong(args[0]);
                String partNumber = null;
                for(int i = 0; i < list.size(); i++) {
                    if(list.get(i).getId() == id) {
                        partNumber = list.get(i).getPartNumber();
                        break;
                    }
                }
                if(partNumber != null)
                    productBuilder.usedPartNumbers.remove(partNumber);

                for(int i = 0; i < list.size(); i++) {
                    if(list.get(i).getId() == id) {
                        list.set(i, productBuilder.buildProduct(add.createProductManually(productBuilder, io), id));
                        break;
                    }
                }

                result = messenger.UpdateById().Product_was_updated;
            } catch (NumberFormatException e) {
                result = messenger.UpdateById().Wrong_id_format_Enter_long;
            } catch (ContentException e) {
                result = messenger.UpdateById().Build_exception + ": " + e.getMessage();
            } catch (IOException e) {
                result = "IOException: " + e.getMessage();
            }

        } else {
            result = messenger.UpdateById().Please_enter_id;
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.UpdateById().description;
    }
}
