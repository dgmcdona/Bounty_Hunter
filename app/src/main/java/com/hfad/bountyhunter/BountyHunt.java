package com.hfad.bountyhunter;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import swapi.models.People;


/**
 * A simple {@link Fragment} subclass.
 */
public class BountyHunt extends Fragment implements View.OnClickListener {

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    String[] planetNames;
    String[] bounties;
    BountyHunter selectedHunter = BountyHunter.bobaFett;
    String selectedBountyName;
    public BountyHunt() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.start_hunt:
                selectedHunter.collectBounty(selectedBountyName);
                Snackbar snackbar = Snackbar.make(getView(), R.string.hunt_successful, Snackbar.LENGTH_LONG);
                snackbar.setAction("Capture", new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(getActivity(), BountyHunterDetailActivity.class);
                        intent.putExtra("BountyHunter", selectedHunter);
                        getActivity().startActivity(intent);
                    }
                });
                snackbar.show();
            case R.id.ig88_button:
                selectedHunter = BountyHunter.ig88;
            case R.id.boba_fett_button:
                selectedHunter = BountyHunter.bobaFett;
            case R.id.bossk_button:
                selectedHunter = BountyHunter.bossk;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        planetNames = new String[BountyHunterApp.planets.size()];
        for(int i = 0; i < BountyHunterApp.planets.size(); i++){
            planetNames[i] = BountyHunterApp.planets.get(i).name;
            Log.d("BountyHunt", "Planet: " + BountyHunterApp.planets.get(i).name);
        }
        adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_spinner_dropdown_item, planetNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        View v = inflater.inflate(R.layout.fragment_bounty_hunt, container, false);
        Button button = v.findViewById(R.id.start_hunt);
        button.setOnClickListener(this);


        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        final Spinner spinner = getView().findViewById(R.id.planets);
        final Spinner spinner2 = getView().findViewById(R.id.targets);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String planetName = BountyHunterApp.planets.get(i).name;
                Log.d("BountyHunt", planetName);
                ArrayList<String> planetBounties = new ArrayList<>();
                for(People bounty: BountyHunterApp.bountyTargets){
                    if(bounty.homeworldPlanet == null)
                        continue;
                    Log.d("BountyHunt", bounty.homeworldPlanet.name);
                    if (bounty.homeworldPlanet.name.equals(planetNames[i])) {
                        planetBounties.add(bounty.name);
                        Log.d("BountyHunt", bounty.name + " registered.");
                    }
                }
                bounties = new String[planetBounties.size()];
                for(int j = 0; j < planetBounties.size(); j++){
                    bounties[j] = planetBounties.get(j);
                    Log.d("BountyHunt", bounties[j]);
                }
                adapter2 = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, bounties);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBountyName = bounties[i];
                for(People target : BountyHunterApp.bountyTargets){
                    if(selectedBountyName.equals(target.name)){
                        TextView tv = getView().findViewById(R.id.target_details);
                        tv.setText(target.toString());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
