package com.androsov.server.messengers;

public abstract class Messenger {
    protected Messenger() {
        add = new Add();
        averageOfManufactureCost = new AverageOfManufactureCost();
        clear = new Clear();
        countByPrice = new CountByPrice();
        executeScript = new ExecuteScript();
        exit = new Exit();
        help = new Help();
        history = new History();
        info = new Info();
        removeById = new RemoveById();
        removeByManufactureCost = new RemoveByManufactureCost();
        removeFirst = new RemoveFirst();
        save = new Save();
        show = new Show();
        sort = new Sort();
        updateById = new UpdateById();
    }

    public static class Add {
        public String description,
                product_was_added,
                Creation_of_new_product,
                enter_name,
                Name_can_not_be_empty_try_again,
                Enter_coordinate_x,
                X_coordinate_can_not_be_bigger_than_653_Try_again,
                Please_enter_double_format_number,
                Enter_coordinate_y,
                y_is_NULL,
                Enter_price,
                Price_can_not_be_0_Try_again,
                Please_enter_int_format_number,
                Enter_part_number,
                Part_number_is_empty_or_already_used_Try_again,
                Enter_manufacture_cost,
                Manufacture_cost_can_not_be_NULL_Try_again,
                Please_enter_float_format_number,
                Enter_unit_of_measure,
                That_type_of_measure_doesnt_supports_Supported_units_GRAMS_KILOGRAMS_SQUARE_METERS_Try_again,
                Enter_owners_name,
                Owners_name_can_not_be_empty_Try_again,
                Enter_owners_height,
                Owners_height_cant_be_zero_or_negative_Try_again,
                Please_enter_long_format_number,
                Enter_owner_eye_color,
                That_type_of_color_doesnt_supports_Supported_colors_BLUE_GREEN_BLACK_ORANGE_WHITE_BROWN_Try_again,
                Enter_owner_hair_color,
                Wrong_statement_try_again,
                Enter_owner_nationality,
                That_type_of_nationality_doesnt_supports_Supported_nationality_GERMANY_THAILAND_JAPAN_Try_again;
    }
    final Add add;

    public static class AverageOfManufactureCost {
        public String description;

    }
    final AverageOfManufactureCost averageOfManufactureCost;

    public static class Clear {
        public String description,
        result;
    }
    final Clear clear;

    public static class CountByPrice {
        public String description,
        Wrong_number_format,
        Please_enter_argument;
    }
    final CountByPrice countByPrice;

    public static class ExecuteScript {
        public String description,
        script,
        executed,
        Please_enter_script_name,
        Script_error;
    }
    final ExecuteScript executeScript;

    public static class Exit {
        public String description;
    }
    final Exit exit;

    public static class Help {
        public String description,
        doesnt_have_description;
    }
    final Help help;

    public static class History {
        public String description,
        history;
    }
    final History history;

    public static class Info {
        public String description,
        Collection_info,
        type_Linked_list,
        initialization_date,
        number_of_elements;
    }
    final Info info;

    public static class RemoveById {
        public String description,
        Product_with_id,
        was_removed,
        Wrong_id_format_Please_enter_long_format_argument,
        Please_enter_id;
    }
    final RemoveById removeById;

    public static class RemoveByManufactureCost {
        public String description,
        Wrong_id_format_Please_enter_long_format_argument,
        Please_enter_cost;
    }
    final RemoveByManufactureCost removeByManufactureCost;

    public static class RemoveFirst {
        public String description,
        Product_with_id,
        was_removed,
        List_is_already_empty;
    }
    final RemoveFirst removeFirst;

    public static class Save {
        public String description,
        saved;
    }
    final Save save;

    public static class Show {
        public String description;
    }
    final Show show;

    public static class Sort {
        public String description,
        Sorted;
    }
    final Sort sort;

    public static class UpdateById {
        public String description,
        Product_was_updated,
        Wrong_id_format_Enter_long,
        Build_exception,
        Please_enter_id;
    }
    final UpdateById updateById;
}
