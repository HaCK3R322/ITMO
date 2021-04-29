package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.productManagment.ProductBuilder;

import java.util.List;
import java.util.ListIterator;

public class RemoveById extends ListCommand {
    ProductBuilder productBuilder;
    MessengersHandler messenger;

    public RemoveById(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

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
                result = messenger.RemoveById().Product_with_id + id + messenger.RemoveById().was_removed + ".";
            } catch (NumberFormatException e) {
                result = messenger.RemoveById().Wrong_id_format_Please_enter_long_format_argument;
            }
        } else {
            result = messenger.RemoveById().Please_enter_id;
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.RemoveById().description;
    }
}
