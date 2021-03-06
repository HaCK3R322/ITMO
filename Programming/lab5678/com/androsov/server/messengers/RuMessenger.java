package com.androsov.server.messengers;

public class RuMessenger extends Messenger {
    static final RuMessenger INSTANCE = new RuMessenger();
    public static RuMessenger getInstance() { return INSTANCE; }

    private RuMessenger() {
        add.description = "Ручное создание и добавление продука.";
        add.product_was_added = "Продукт был добавлен!";
        add.Creation_of_new_product = "Создание нового продукта";
        add.enter_name = "введите имя:";
        add.Name_can_not_be_empty_try_again = "Имя не может быть пустым. Попробуйте ещё раз.";
        add.Enter_coordinate_x = "Введите координату x:";
        add.X_coordinate_can_not_be_bigger_than_653_Try_again = "X координата не может быть больше 653. Попробуйте ещё.";
        add.Please_enter_double_format_number = "Пожалуйста введите число формата double:";
        add.Enter_coordinate_y = "Введите координату y:";
        add.y_is_NULL = "y не может быть NULL";
        add.Enter_price = "Введите стоимость:";
        add.Price_can_not_be_0_Try_again = "Стоимость не может равняться 0! Поробуйте ещё:";
        add.Please_enter_int_format_number = "Пожалуйста введите число формата int:";
        add.Enter_part_number = "Введите номер детали:";
        add.Part_number_is_empty_or_already_used_Try_again = "Номер детали уже используется! Попробуйте ещё.";
        add.Enter_manufacture_cost = "Введите стоимость мануфактуры:";
        add.Manufacture_cost_can_not_be_NULL_Try_again = "Стоимость мануфактуры не может раняться NULL. Попробуйте ещё.";
        add.Please_enter_float_format_number = "Пожалуйста введите число формата float:";
        add.Enter_unit_of_measure = "Введите единицу измерения:";
        add.That_type_of_measure_doesnt_supports_Supported_units_GRAMS_KILOGRAMS_SQUARE_METERS_Try_again = "Такой тим единиц измерения не поддерживается. Поддерживаемые типы: KILOGRAMS, SQUARE_METERS, GRAMS. Попробуйте ещё.";
        add.Enter_owners_name = "Введите имя владельца:";
        add.Owners_name_can_not_be_empty_Try_again = "Имя владельца не может быть пустым. Попробуйте ещё.";
        add.Enter_owners_height = "Введите рост владельца:";
        add.Please_enter_long_format_number = "Пожалуйста введите число в формате long";
        add.Enter_owner_eye_color = "Введите цвет глаз владельца:";
        add.That_type_of_color_doesnt_supports_Supported_colors_BLUE_GREEN_BLACK_ORANGE_WHITE_BROWN_Try_again = "Такой цвет не поддерживается. Поддерживаемые цвета: BLUE, GREEN, BLACK, ORANGE, WHITE, BROWN. Попробуйте ещё.";
        add.Enter_owner_hair_color = "Введите цвет волос владельца:";
        add.Enter_owner_nationality = "Введите национальность владельца:";
        add.That_type_of_nationality_doesnt_supports_Supported_nationality_GERMANY_THAILAND_JAPAN_Try_again = "Такая страна не поддерживается. Поддерживаемые страны: GERMANY, THAILAND, JAPAN. Попробуйте ещё.";

        averageOfManufactureCost.description = "выводит среднюю цену мануфактуры.";

        clear.description = "удаляет все продукты из коллекции.";
        clear.result = "Лист был очищен!";

        countByPrice.description = "Подсчитывает количество продуктов с этой ценой.";
        countByPrice.Please_enter_argument = "Пожалуйста введите цену.";
        countByPrice.Wrong_number_format = "Неправильный формат числа. Попробуйте ещё.";

        executeScript.description = "выполняет скрипт. Формат команды: <execute_script> <путь до скрипта>";
        executeScript.Script_error = "Ошибка выполнения скрипта";
        executeScript.script = "скрипт";
        executeScript.Please_enter_script_name = "Пожалуйста введите путь до скрипта.";
        executeScript.executed = "выполнен";

        exit.description = "Заканчивает работу программы.";

        help.description = "Если введена без аргумента, выводит список всех команд с их описанием. В ином случае выводит запрошенные команды с их описанием.";
        help.doesnt_have_description = "<не имеет описания>";

        history.description = "Выводит последние 12 использованных команд.";
        history.history = "история";

        info.Collection_info = "Информация о коллекции";
        info.description = "выводит некоторую информациюю о данной коллекции";
        info.initialization_date = "дата создания";
        info.number_of_elements = "число элементов";
        info.type_Linked_list = "тип: LinkedList";

        removeById.description = "Удаляет продукт имеющий данный id";
        removeById.Please_enter_id = "Пожалуйста введите id";
        removeById.Product_with_id = "Продукт с id";
        removeById.was_removed = "был удален";
        removeById.Wrong_id_format_Please_enter_long_format_argument = "Неправильный формат id. Пожалуйста введите число в формате long";

        removeByManufactureCost.description = "удаляет один продукт имеющий данную цену мануфактуры";
        removeByManufactureCost.Please_enter_cost = "Пожалуйста, введите цену.";
        removeByManufactureCost.Wrong_id_format_Please_enter_long_format_argument = "Неправильный формат id. Пожалуйста введите число в формате long";

        removeFirst.description = "удаляет первый элемент коллекции.";
        removeFirst.Product_with_id = "Продукт с id";
        removeFirst.was_removed = "был удален.";
        removeFirst.List_is_already_empty = "Лист пуст.";

        save.description = "сохраняет коллекцию.";
        save.saved = "Коллекция была сохранена успешно!";

        show.description = "выводит все элементы коллекции.";

        sort.description = "сортирует коллекцию.";
        sort.Sorted = "Коллекция отсортирована.";

        updateById.description = "Заменяет продукт новым, оставляя id";
        updateById.Build_exception = "Ошибка создания";
        updateById.Please_enter_id = "Пожалуйста, введите id";
        updateById.Wrong_id_format_Enter_long = "Неверный формат id. Пожалуйста введите число в формате long";
        updateById.Product_was_updated = "Продукт был обновлен.";
    }
}
