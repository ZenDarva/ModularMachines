package com.gmail.zendarva.mm.items.modules;

public class InputModule extends BaseModule {

    public InputModule()
    {
        super();
        this.unlocalizedName ="inputmodule";
    }

    @Override
    public int rfPerTick() {
        return 0;
    }

    @Override
    public int progress() {
        return 0;
    }
}
