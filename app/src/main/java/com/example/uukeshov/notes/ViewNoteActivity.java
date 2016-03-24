package com.example.uukeshov.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewNoteActivity extends AppCompatActivity {

    TextView noteText;
    TextView noteTheme;
    NotesListAdapter adapter;
    private static final String LOG_TAG = "ViewNoteActivityLog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String noteId1 = getIntent().getStringExtra("note_id");
        getSupportActionBar().setTitle("Заметка №(" + String.valueOf(noteId1) + ")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteTheme = (TextView) findViewById(R.id.textView4);
        String noteTheme1 = getIntent().getStringExtra("note_theme");
        noteTheme.setText(noteTheme.getText().toString() + " " + noteTheme1);

        noteText = (TextView) findViewById(R.id.EditText2);
        String noteText1 = getIntent().getStringExtra("note_text");
        noteText.setText(noteText.getText().toString() + " " + noteText1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.viewnote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("note_text"));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.cancel) {

            NoteDb db = new NoteDb(ViewNoteActivity.this.getApplicationContext());
            db.deleteById(Integer.parseInt(getIntent().getStringExtra("note_id")));
            /*Intent it = new Intent(this, MainActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(it);*/
            Toast.makeText(this, "Заметка успешно удалена", Toast.LENGTH_SHORT).show();
            finish();

        } else if (id == R.id.save) {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formateDate = dt.format(c.getTime());

            EditText et = (EditText) findViewById(R.id.EditText2);
            String text = et.getText().toString();
            NoteDb db = new NoteDb(ViewNoteActivity.this);
            String noteId1 = getIntent().getStringExtra("note_id");
            db.updateNoteById(Integer.parseInt(noteId1), text, formateDate);
            Toast.makeText(this, "Заметка успешно отдеактирована", Toast.LENGTH_SHORT).show();
           /* Intent intent = new Intent(ViewNoteActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/
            finish();

        } else if (id == R.id.show) {

            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("note_theme", getIntent().getStringExtra("note_theme"));
            intent.putExtra("latitude", getIntent().getStringExtra("latitude"));
            intent.putExtra("longitude", getIntent().getStringExtra("longitude"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}