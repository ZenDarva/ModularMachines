package com.gmail.zendarva.mm;

import net.minecraftforge.energy.EnergyStorage;

/**
 * Created by James on 4/18/2017.
 */
public class ModularEnergyStorage extends EnergyStorage {
    public ModularEnergyStorage(int capacity) {
        super(capacity);
    }

    public void setEnergy(int newEnergy) {
        this.energy=newEnergy;
    }
}
