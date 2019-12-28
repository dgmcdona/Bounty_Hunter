package com.hfad.bountyhunter;

import android.app.Application;
import android.telecom.Call;
import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import swapi.models.People;
import swapi.models.Planet;
import swapi.models.SWModelList;
import swapi.models.Starship;
import swapi.models.Vehicle;
import swapi.sw.StarWars;
import swapi.sw.StarWarsApi;

public class BountyHunterApp extends Application {

    Pattern pattern = Pattern.compile("/[0-9]/$");
    public static boolean firstLoad;
    public static StarWars sw;
    public static ArrayList<People> bountyTargets = new ArrayList<>();
    public static ArrayList<Planet> planets = new ArrayList<>();
    @Override
    public void onCreate() {
        firstLoad = true;
        super.onCreate();
        StarWarsApi.init();
        sw = StarWarsApi.getApi();
        for(int k = 1; k <= 9; k++) {
            final int i = k;
            sw.getAllPeople(i, new Callback<SWModelList<People>>() {
                @Override
                public void success(SWModelList<People> peopleSWModelList, Response response) {
                    bountyTargets.addAll(peopleSWModelList.results);
                    Log.d("BountyHunterApp", "Bounties loaded successfully.");
                    Log.d("BountyHunterApp", "" + bountyTargets.size());
                    if(bountyTargets.size() == 87)
                        buildCharacters();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("BountyHunterApp", "Error loading people", error);
                }
            });
        }
        for(int i = 1; i <= 7; i++) {
            sw.getAllPlanets(i, new Callback<SWModelList<Planet>>() {
                @Override
                public void success(SWModelList<Planet> planetSWModelList, Response response) {
                    planets.addAll(planetSWModelList.results);
                    Log.d("BountyHunterApp", "Planets loaded successfully.");
                    Log.d("BountyHunterApp", "" + planets.size());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("BountyHunterApp", "Error loading planets.", error);
                }
            });
        }


    }

    public void buildCharacters(){

        for(int i = 0; i < bountyTargets.size(); i++){
            bountyTargets.get(i).ownedVehicles = new ArrayList<>();
            bountyTargets.get(i).ownedStarships = new ArrayList<>();
            final People target = bountyTargets.get(i);
            for(int j = 0; j < target.vehiclesUrls.size(); j++){
                String vehicleUrl = target.vehiclesUrls.get(j);
                String[] parsedUrl = vehicleUrl.split("/");
                int vehicleId = Integer.parseInt(parsedUrl[parsedUrl.length - 1]);
                sw.getVehicle(vehicleId, new Callback<Vehicle>() {
                    @Override
                    public void success(Vehicle vehicle, Response response) {
                        target.ownedVehicles.add(vehicle);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("BountyHunterApp", "Error adding vehicle.");
                    }
                });
            }
            for(int j = 0; j < target.starshipsUrls.size(); j++){
                String starshipUrl = target.starshipsUrls.get(j);
                String[] parsedUrl = starshipUrl.split("/");
                int starshipId = Integer.parseInt(parsedUrl[parsedUrl.length-1]);
                sw.getStarship(starshipId, new Callback<Starship>() {
                    @Override
                    public void success(Starship starship, Response response) {
                        target.ownedStarships.add(starship);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("BountyHunterApp", "Error adding starship.");
                    }
                });
            }
            String[] parsedUrl = target.homeWorldUrl.split("/");
            int planetId = Integer.parseInt(parsedUrl[parsedUrl.length-1]);
            sw.getPlanet(planetId, new Callback<Planet>() {
                @Override
                public void success(Planet planet, Response response) {
                    target.homeworldPlanet = planet;
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("BountyHunterApp", "Error loading homeworld.");
                }
            });
            Log.d("BountyHunterApp", "Name: " +
                    bountyTargets.get(i).name +
                    "\nHomeworld: " + bountyTargets.get(i).homeworldPlanet);
        }
    }
}
