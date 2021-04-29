package com.androsov.server.Messengers;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class MessengersHandler {
    public HashMap<String, Messenger> messengers;

    Messenger messenger;

    public MessengersHandler() {
        messengers = new HashMap<>();

        messengers.put("Ru", new Ru());
        messengers.put("Eng", new Eng());

        messenger = messengers.get("Eng");
    }

    public String changeLanguage(String lang) {
        if(messengers.containsKey(lang)) {
            messenger = messengers.get(lang);
            return "Language was changed on " + lang;
        } else {
            return "Language not found.";
        }
    }

    public String getAllLangsNames() {
        String langs = "";

        for(Map.Entry<String, Messenger> entry : messengers.entrySet()) {
            langs += entry.getKey() + " ";
        }

        return langs;
    }

    public Messenger.Add Add() {
        return messenger.add;
    }
    public Messenger.AverageOfManufactureCost AverageOfManufactureCost() {
        return messenger.averageOfManufactureCost;
    }
    public Messenger.Clear Clear() {
        return messenger.clear;
    }
    public Messenger.CountByPrice CountByPrice() {
        return messenger.countByPrice;
    }
    public Messenger.ExecuteScript ExecuteScript() {
        return messenger.executeScript;
    }
    public Messenger.Exit Exit() { return messenger.exit; }
    public Messenger.Help Help() {
        return messenger.help;
    }
    public Messenger.History History() {
        return messenger.history;
    }
    public Messenger.Info Info() {
        return messenger.info;
    }
    public Messenger.RemoveById RemoveById() {
        return messenger.removeById;
    }
    public Messenger.RemoveByManufactureCost RemoveByManufactureCost() {
        return messenger.removeByManufactureCost;
    }
    public Messenger.RemoveFirst RemoveFirst() {
        return messenger.removeFirst;
    }
    public Messenger.Save Save() {
        return messenger.save;
    }
    public Messenger.Show Show() {
        return messenger.show;
    }
    public Messenger.Sort Sort() {
        return messenger.sort;
    }
    public Messenger.UpdateById UpdateById() {
        return messenger.updateById;
    }
}
