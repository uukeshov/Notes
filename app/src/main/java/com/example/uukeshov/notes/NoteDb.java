package com.example.uukeshov.notes;

/**
 * Created by uukeshov on 3/1/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class NoteDb extends SQLiteOpenHelper {

    private static final String LOG_TAG = "NoteDbLog";

    public NoteDb(Context context) {
        super(context, "NoteDB", null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table notestable (id integer primary key autoincrement,notetext text,createdate text,notetheme text,latitude text, longitude text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXIST notestable");
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("notetext", note.get_noteText());
        cv.put("createdate", note.get_noteDate());
        cv.put("notetheme", note.get_noteTheme());
        cv.put("latitude", note.get_latitude());
        cv.put("longitude", note.get_longitude());
        db.insert("notestable", null, cv);
        db.close();
    }

    public Note getNoteById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("notestable", new String[]{"id", "notetext", "createdate", "notetheme", "latitude", "longitude"}, "id" + "+?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Note note = new Note(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getDouble(4), cursor.getDouble(5));
        db.close();
        return note;
    }

    public void deleteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("notestable", "id" + "=" + id, null);
        db.close();
    }

    public void deleteAllNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + "notestable");
        db.close();
    }

    public List<Note> getAllNotes() {

        List<Note> noteList = new ArrayList<Note>();
        String selectQuery = "SELECT * FROM notestable ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.set_noteId(Integer.valueOf(cursor.getString(0)));
                note.set_noteText(cursor.getString(1));
                note.set_noteDate(cursor.getString(2));
                note.set_noteTheme(cursor.getString(3));
                note.set_latitude(cursor.getDouble(4));
                note.set_longitude(cursor.getDouble(5));
                noteList.add(note);
                Log.d(LOG_TAG, "--- onCreate database ---" + note);
            } while (cursor.moveToNext());
        }
        db.close();
        return noteList;
    }

    public int getNotesAmount() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(id) FROM " + "notestable", null);
        if (cursor.moveToFirst()) {
            cursor.getInt(0);
        }
        cursor.getInt(0);
        db.close();

        return cursor.getInt(0);
    }

    public void updateNoteById(int id, String notetext, String createdate) {

        Log.d(LOG_TAG, "--- onCreate database dsa ---" + id + notetext);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put("notetext", notetext);
        args.put("createdate", createdate);
        db.update("notestable", args, "id" + "='" + id + "'",
                null);
        db.close();
    }

    public int getNoteBydate(String date) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("notestable", new String[]{"id"}, "createdate" + "+?", new String[]{date}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        int id = cursor.getInt(0);
        db.close();
        return id;
    }
}