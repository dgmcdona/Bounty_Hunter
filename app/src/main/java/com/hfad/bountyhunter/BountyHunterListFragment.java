package com.hfad.bountyhunter;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.internal.bind.ArrayTypeAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BountyHunterListFragment extends ListFragment {

    public BountyHunterListFragment() {
        // Required empty public constructor
    }



    public void onListItemClick(ListView l, View v, int position, long id){
        Intent intent = new Intent(this.getContext(), BountyHunterDetailActivity.class);
        BountyHunter bh = (BountyHunter)getListView().getItemAtPosition(position);
        intent.putExtra("BountyHunter", bh);
        startActivity(intent);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayAdapter<BountyHunter> adapter = new ArrayAdapter<BountyHunter>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                BountyHunter.bountyHunters
        );

        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
