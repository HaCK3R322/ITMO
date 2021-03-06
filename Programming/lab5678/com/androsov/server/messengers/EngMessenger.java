package com.androsov.server.messengers;

public class EngMessenger extends Messenger {
    static final EngMessenger INSTANCE = new EngMessenger();
    public static EngMessenger getInstance() { return INSTANCE; }

    private EngMessenger() {
        add.description = "product creation from arguments: <name><coordinate x><coordinate y><price><part number><manufacture cost><unit of measure><owner name><owner height><owner eye color><owner hair color><nationality>.";
        add.product_was_added = "Product was added successfully!";
        add.Creation_of_new_product = "Creation of new Product";
        add.enter_name = "enter name:";
        add.Name_can_not_be_empty_try_again = "Name can not be empty. Try again:";
        add.Enter_coordinate_x = "Enter coordinate x:";
        add.X_coordinate_can_not_be_bigger_than_653_Try_again = "X coordinate can not be bigger than 653. Try again:";
        add.Please_enter_double_format_number = "Please enter double-format number:";
        add.Enter_coordinate_y = "Enter coordinate_y:";
        add.y_is_NULL = "y is NULL";
        add.Enter_price = "Enter price:";
        add.Price_can_not_be_0_Try_again = "Price can not be 0! Try again:";
        add.Please_enter_int_format_number = "Please enter int-format number";
        add.Enter_part_number = "Enter part number:";
        add.Part_number_is_empty_or_already_used_Try_again = "Part number is already used. Try again.";
        add.Enter_manufacture_cost = "Enter manufacture cost:";
        add.Manufacture_cost_can_not_be_NULL_Try_again = "Manufacture cost can not be NULL. Try again.";
        add.Please_enter_float_format_number = "Please enter float-format number:";
        add.Enter_unit_of_measure = "Enter unit of measure";
        add.That_type_of_measure_doesnt_supports_Supported_units_GRAMS_KILOGRAMS_SQUARE_METERS_Try_again = "That type of measure doesn't supports. Supported units: GRAMS, KILOGRAMS, SQUARE_METERS. Try again:";
        add.Enter_owners_name = "Enter owners name";
        add.Owners_name_can_not_be_empty_Try_again = "Owners name cant be empty. Try again:";
        add.Enter_owners_height = "Enter owners height:";
        add.Please_enter_long_format_number = "Please, enter long-format number:";
        add.Enter_owner_eye_color = "Enter owner eye color:";
        add.That_type_of_color_doesnt_supports_Supported_colors_BLUE_GREEN_BLACK_ORANGE_WHITE_BROWN_Try_again = "That type of color doesn't supports. Supported colors: BLUE, GREEN, BLACK, ORANGE, WHITE, BROWN. Try again:";
        add.Enter_owner_hair_color = "Enter owner hair color:";
        add.Enter_owner_nationality = "Enter owner nationality";
        add.That_type_of_nationality_doesnt_supports_Supported_nationality_GERMANY_THAILAND_JAPAN_Try_again = "That type of nationality doesnt supports Supported nationalities: GERMANY, THAILAND, JAPAN, Try again:";

        averageOfManufactureCost.description = "shows average of manufacture cost.";

        clear.description = "deletes all products from collection.";
        clear.result = "List was cleared!";

        countByPrice.description = "counts product with that price.";
        countByPrice.Please_enter_argument = "Please enter price.";
        countByPrice.Wrong_number_format = "Wrong number format. Try again.";

        executeScript.description = "Executes script. Command format: <execute_script> <script path>";
        executeScript.Script_error = "Script error";
        executeScript.script = "script";
        executeScript.Please_enter_script_name = "Please enter script name.";
        executeScript.executed = "executed";

        exit.description = "Ends session.";

        help.description = "If used without an argument, returns a list of commands with a description, otherwise displays help for that commands.";
        help.doesnt_have_description = "<doesnt have description>";

        history.description = "Shows 12 last used commands.";
        history.history = "history";

        info.Collection_info = "Collection info";
        info.description = "gives some info about the hole collection.";
        info.initialization_date = "initialization date";
        info.number_of_elements = "number of elements";
        info.type_Linked_list = "type: LinkedList";

        removeById.description = "removes product with such id.";
        removeById.Please_enter_id = "Please enter id";
        removeById.Product_with_id = "Product with id";
        removeById.was_removed = "was removed";
        removeById.Wrong_id_format_Please_enter_long_format_argument = "Wrong id format. Please enter long-format argument.";

        removeByManufactureCost.description = "removes one product with such manufacture cost.";
        removeByManufactureCost.Please_enter_cost = "Please, enter cost.";
        removeByManufactureCost.Wrong_id_format_Please_enter_long_format_argument = "Wrong id format. Please enter long-format argument.";

        removeFirst.description = "removes first element of collection.";
        removeFirst.Product_with_id = "Product with id";
        removeFirst.was_removed = "was removed.";
        removeFirst.List_is_already_empty = "List is already empty.";

        save.description = "saves collection.";
        save.saved = "Collection was saved successfully!";

        show.description = "shows all elements of collection.";

        sort.description = "sorts collection.";
        sort.Sorted = "Collection sorted.";

        updateById.description = "Manual product update with given id.";
        updateById.Build_exception = "Product build exception";
        updateById.Please_enter_id = "Please, enter id.";
        updateById.Wrong_id_format_Enter_long = "Wrong id format. Please enter long-format argument";
        updateById.Product_was_updated = "Product was updated.";
    }
}
