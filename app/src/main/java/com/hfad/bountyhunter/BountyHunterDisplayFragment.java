package com.hfad.bountyhunter;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class BountyHunterDisplayFragment extends Fragment {


    public BountyHunterDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_bounty_hunter_display, container, false);

        String[] hunterNames = new String[BountyHunter.bountyHunters.length];
        for(int i = 0; i < BountyHunter.bountyHunters.length; i++){
            hunterNames[i] = BountyHunter.bountyHunters[i].getCharacter().name;
        }

        int[] imageIds = new int[BountyHunter.bountyHunters.length];
        for(int i = 0; i < BountyHunter.bountyHunters.length; i++){
            imageIds[i] = BountyHunter.bountyHunters[i].getImageResourceId();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(hunterNames, imageIds);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), BountyHunterDetailActivity.class);
                intent.putExtra("BountyHunter", BountyHunter.bountyHunters[position]);
                getActivity().startActivity(intent);
            }
        });
        return recyclerView;
    }

}
