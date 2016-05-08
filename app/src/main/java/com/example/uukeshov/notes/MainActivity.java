package com.example.uukeshov.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    NotesListAdapter adapter;
    ListView list;
    GPSTracker gps;
    private AdView mAdView;
    private static final String LOG_TAG = "MainActivityLog";
    //Log.d(LOG_TAG, "--- MainActivityLog ---" + db.getAllNotes());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivity(it);
            }
        });

        mAdView = (AdView) findViewById(R.id.ad_view);
        Log.d(LOG_TAG, "Start advertisment");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        Log.d(LOG_TAG, "Finish advetisment");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Intent it = new Intent(this, AboutAppActivity.class);
            startActivity(it);
        } else if (id == R.id.deleteAllNotes) {
            NoteDb db = new NoteDb(MainActivity.this.getApplicationContext());

            Intent intent = getIntent();
            finish();
            startActivity(intent);

            if (db.getNotesAmount() > 0) {
                db.deleteAllNotes();
            } else {
                Toast.makeText(this, "Заметок нет", Toast.LENGTH_SHORT).show();
            }
            this.onStart();
        } else if (id == R.id.show) {

            Intent it = new Intent(this, MainMapsActivity.class);
            it.putExtra("Notes", getIntent().getData());
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new NotesListAdapter(MainActivity.this, getData(), MainActivity.this.getApplicationContext());
        getSupportActionBar().setTitle("Заметки(" + String.valueOf(adapter.getCount()) + ")");
        if (Integer.valueOf(adapter.getCount()) > 0) {
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        // Activity being restarted from stopped state
    }

    public ArrayList<Note> getData() {

        NoteDb db = new NoteDb(MainActivity.this.getApplicationContext());
        final ArrayList<Note> stringItems = new ArrayList<Note>();

        ArrayList<Note> pr = (ArrayList<Note>) db.getAllNotes();

        for (Note p : pr) {
            stringItems.add(p);
        }
        return stringItems;
    }
}
