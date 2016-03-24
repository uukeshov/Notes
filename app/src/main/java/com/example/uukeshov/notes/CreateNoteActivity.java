package com.example.uukeshov.notes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNoteActivity extends AppCompatActivity {
    private static final String LOG_TAG = "CreateNoteLog";

    EditText etName;
    NoteDb NoteDb;
    boolean canGetLocation = false;
    int note_i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_createnote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Double longitude; //долгота
        Double latitude;
        //noinspection SimplifiableIfStatement

        if (id == R.id.cancel) {
            Intent intent = new Intent(CreateNoteActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        if (id == R.id.save) {

            EditText et = (EditText) findViewById(R.id.EditText);
            EditText et1 = (EditText) findViewById(R.id.EditText2);

            String text = et.getText().toString();
            String text2 = et1.getText().toString();

            if (text.length() > 0 && text2.length() > 0) {

                NoteDb db = new NoteDb(CreateNoteActivity.this);
                GPSTracker gps = new GPSTracker(CreateNoteActivity.this);

                Calendar c = Calendar.getInstance();
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formateDate = dt.format(c.getTime());

                if (gps.canGetLocation) {
                    Location l = gps.getLocation();
                    longitude = gps.getLongitude();
                    latitude = gps.getLatitude();
                    Note note = new Note(text, formateDate, text2, longitude, latitude);
                    db.addNote(note);

                    Toast.makeText(this, "Заметка успешно создана", Toast.LENGTH_SHORT).show();

                    /**/
                    note_i = db.getNoteBydate(formateDate);
                    Intent myIntent = new Intent(this, ViewNoteActivity.class);

                    myIntent.putExtra("note_id", String.valueOf(note_i));
                    myIntent.putExtra("note_text", text);
                    myIntent.putExtra("note_theme", text2);
                    myIntent.putExtra("latitude", String.valueOf(longitude));
                    myIntent.putExtra("longitude", String.valueOf(latitude));

                    PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), myIntent, 0);

                    // Build notification
                    // Actions are just fake
                    Notification noti = new Notification.Builder(this)
                            .setContentTitle("Заметка создана")
                            .setContentText(text).setSmallIcon(R.drawable.ic_create)
                            .setContentIntent(pIntent)
                            .build();
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    noti.flags |= Notification.FLAG_AUTO_CANCEL;

                    notificationManager.notify(0, noti);
                    /**/

                    finish();

                } else {
                    gps.showSettingsAlert();
                }

            } else {
                if (text.length() <= 0 && text2.length() <= 0) {
                    Toast.makeText(this, "Тема заметки и тело пустые", Toast.LENGTH_SHORT).show();
                } else if (text.length() <= 0) {
                    Toast.makeText(this, "Тело заметки пустая", Toast.LENGTH_SHORT).show();
                } else if (text2.length() <= 0) {
                    Toast.makeText(this, "Тема заметки пустое", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Это косяк", Toast.LENGTH_SHORT).show();
                }
            }

        }

        Log.d(LOG_TAG, "--- Start 5 ---");
        return super.onOptionsItemSelected(item);
    }
}
