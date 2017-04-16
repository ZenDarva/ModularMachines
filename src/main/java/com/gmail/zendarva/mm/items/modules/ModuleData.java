package com.gmail.zendarva.mm.items.modules;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gmail.zendarva.mm.jackson.ClassDeserializer;

public class ModuleData {
    public ModuleData(String name, int id, Class<? extends BaseModule> clazz) {
        this.name = name;
        this.id = id;
        this.clazz=clazz;
    }
    public ModuleData(){

    }
    public String name;
    public int id;
    @JsonDeserialize(using = ClassDeserializer.class)
    public Class<? extends BaseModule> clazz;
}