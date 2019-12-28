package com.hfad.bountyhunter;

import java.io.Serializable;
import java.util.ArrayList;

import swapi.models.People;
import swapi.models.Starship;
import swapi.models.Vehicle;

public class Bounty implements Serializable, ViewableCharacter {
    private People character;
    private String homeworld;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Starship> starships;

    public Bounty(People character) {
        this.character = character;
    }

    public int getMass(){
        if(this.character.mass.equals("unknown"))
            return (int) (Math.random() * 100);
        return Integer.parseInt(this.character.mass);
    }

    public People getCharacter(){
        return this.character;
    }

    @Override
    public String toString(){
        return String.format("Name: %s\nBirth Year: %s\nHeight: %s\nMass: %s\nSkin Color: %s\n# of ships: %d\n# of vehicles: %d",
                this.character.name,
                this.character.birthYear,
                this.character.height,
                this.character.mass,
                this.character.skinColor,
                this.character.starshipsUrls.size(),
                this.character.vehiclesUrls.size());
    }

}
