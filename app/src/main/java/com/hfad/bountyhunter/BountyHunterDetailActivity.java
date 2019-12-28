package com.hfad.bountyhunter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class BountyHunterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounty_hunter_detail);

        Intent intent = getIntent();
        BountyHunter bh = (BountyHunter)intent.getSerializableExtra("BountyHunter");

        TextView tv = findViewById(R.id.hunter_name);
        ImageView iv = findViewById(R.id.hunter_image);

        tv.setText(bh.toString());
        if(bh.getCharacter().name.equals("Boba Fett")){
            iv.setImageResource(R.drawable.bobafett);
        }
        else if(bh.getCharacter().name.equals("IG-88")){
            iv.setImageResource(R.drawable.ig88);
        }
        else if(bh.getCharacter().name.equals("Bossk")){
            iv.setImageResource(R.drawable.bossk);
        }

        String[] bountyNames = new String[bh.getBounties().size()];
        for(int i = 0; i < bh.getBounties().size(); i++){
            bountyNames[i] = bh.getBounties().get(i).toString();
        }

        int[] imageIds = new int[bh.getBounties().size()];
        Arrays.fill(imageIds, R.drawable.shadycharacter);

        RecyclerView recyclerView = findViewById(R.id.bounty_recycler);


        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(bountyNames, imageIds);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
