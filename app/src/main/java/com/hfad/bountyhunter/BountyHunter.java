package com.hfad.bountyhunter;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import swapi.models.People;

public class BountyHunter implements Serializable, ViewableCharacter {
    public static BountyHunter ig88;
    public static BountyHunter bobaFett;
    public static BountyHunter bossk;

    public static BountyHunter[] bountyHunters;


    private People character;
    private ArrayList<Bounty> bounties;
    private int health;
    private int imageResourceId;

    public BountyHunter(People character) {
        this.character = character;
        this.bounties = new ArrayList<>();
        this.health = 100;
        if(this.character.name.equals("Boba Fett"))
            this.imageResourceId = R.drawable.bobafett;
        else if(this.character.name.equals("IG-88"))
            this.imageResourceId = R.drawable.ig88;
        else if(this.character.name.equals("Bossk"))
            this.imageResourceId = R.drawable.bossk;
    }

    public static void generateBountyHunters(){
        bountyHunters = new BountyHunter[3];
        for(People bounty : BountyHunterApp.bountyTargets){
            if(bounty.name.equals("IG-88"))
                ig88 = new BountyHunter(bounty);
            else if(bounty.name.equals("Boba Fett"))
                bobaFett = new BountyHunter(bounty);
            else if(bounty.name.equals("Bossk"))
                bossk = new BountyHunter(bounty);
        }
        bountyHunters[0] = ig88;
        bountyHunters[1] = bobaFett;
        bountyHunters[2] = bossk;

        for(int i = 0; i < 3; i++){
            int bounty1 = (int)(Math.random() * 86.0);
            int bounty2 = (int)(Math.random() * 86.0);
            int bounty3 = (int)(Math.random() * 86.0);

            bountyHunters[0].getBounties().add(new Bounty(BountyHunterApp.bountyTargets.get(bounty1)));
            bountyHunters[1].getBounties().add(new Bounty(BountyHunterApp.bountyTargets.get(bounty2)));
            bountyHunters[2].getBounties().add(new Bounty(BountyHunterApp.bountyTargets.get(bounty3)));
        }
    }

    public void collectBounty(String bountyName){
        Bounty bounty = null;
        People toDelete = null;
        for(People target : BountyHunterApp.bountyTargets){
            if(target.name.equals(bountyName)){
                bounty = new Bounty(target);
                this.getCharacter().ownedStarships.addAll(target.ownedStarships);
                this.getCharacter().ownedVehicles.addAll(target.ownedVehicles);
                toDelete = target;
                this.getBounties().add(bounty);
                break;
            }
        }
        BountyHunterApp.bountyTargets.remove(toDelete);
        try {
            int damage = (int) ( this.getMass() / bounty.getMass()) * 20;
            this.health -= damage;
        } catch (NullPointerException e){
            Log.e("BountyHunter", "No such bounty target exits.");
        }
    }

    public double getMass(){
        return Double.parseDouble(this.character.mass);
    }

    public People getCharacter(){
        return this.character;
    }

    public int getImageResourceId() { return this.imageResourceId; }

    public ArrayList<Bounty> getBounties(){ return this.bounties; }

    @Override
    public String toString(){
        return String.format("Name: %s\nBirth Year: %s\nMass: %s\nSkin Color: %s\nHealth: %d\nPrimary Starship: %s\n",
                this.character.name,
                this.character.birthYear,
                this.character.mass,
                this.character.skinColor,
                this.health,
                (this.getCharacter().ownedStarships == null || this.getCharacter().ownedStarships.size() == 0 ? "" : this.getCharacter().ownedStarships.get(0).name));
    }
}
