package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.productManagment.ProductBuilder;
import com.androsov.server.productManagment.exceptions.ContentException;

import java.util.List;

public class UpdateById extends ListCommand {
    final ProductBuilder productBuilder;
    final MessengersHandler messenger;
    final List<Product> list;

    final Add add;

    public UpdateById(List<Product> list, ProductBuilder productBuilder, MessengersHandler messenger) {
        this.list = list;
        this.productBuilder = productBuilder;
        this.messenger = messenger;

        add = new Add(list, productBuilder, messenger);

        name = "update_by_id";
        description = "Manual product update with given id.";
        argumentFormat = "Long";
        userAccessible = true;
    }

    @Override
    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        final Response response = new ResponseImpl(request.getUser());
        String result;

        if(args.size() > 0) {
            try {
                Long id = (Long)args.get(0);
                args.remove(0);
                String partNumber = null;
                for (Product product : list) {
                    if (product.getId() == id) {
                        partNumber = product.getPartNumber();
                        break;
                    }
                }
                if(partNumber != null)
                    productBuilder.usedPartNumbers.remove(partNumber);

                for(int i = 0; i < list.size(); i++) {
                    if(list.get(i).getId() == id) {
                        list.set(i, productBuilder.buildProduct(add.createProductManually(args.toArray(String[]::new)), id));
                        break;
                    }
                }

                result = messenger.UpdateById().Product_was_updated;
            } catch (NumberFormatException e) {
                result = messenger.UpdateById().Wrong_id_format_Enter_long;
            } catch (ContentException e) {
                result = messenger.UpdateById().Build_exception + ": " + e.getMessage();
            }

        } else {
            result = messenger.UpdateById().Please_enter_id;
        }

        response.setMessage(result);
        return response;
    }

    @Override
    public String getDescription() {
        return messenger.UpdateById().description;
    }
}
