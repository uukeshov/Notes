package com.example.uukeshov.notes;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MainMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String LOG_TAG = "MainMapsActivity";
    private GoogleMap mMap;
    NoteDb NoteDb;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        init();
    }

    public void init() {

        Double latitude;
        Double longitude;
        String note_theme;
        NoteDb db = new NoteDb(MainMapsActivity.this);
        List<Note> stringItems = db.getAllNotes();
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        for (int i = 0; i < stringItems.size(); i++) {

            Note note = stringItems.get(i);

            LatLng coordinate = new LatLng(note.get_latitude(), note.get_longitude());
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 10);
            mMap.addMarker(new MarkerOptions().position(coordinate).title(note.get_noteTheme()));
            mMap.animateCamera(yourLocation);
        }

    /*    mMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(42.832794, 74.604036))
                .add(new LatLng(41.832794, 73.604036))
                .add(new LatLng(40.832794, 71.604036)));

    }*/
    }
}
