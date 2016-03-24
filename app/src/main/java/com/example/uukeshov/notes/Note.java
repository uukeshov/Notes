package com.example.uukeshov.notes;

/**
 * Created by uukeshov on 3/1/2016.
 */
public class Note {
    public Note() {
    }

    private static final String LOG_TAG = "NoteLog";
    int _noteId;
    String _noteTheme;
    String _noteText;
    String _noteDate;
    Double _longitude; //долгота
    Double _latitude; //широта

    public Note(int noteId, String noteText, String noteDate, String noteTheme, Double longitude, Double latitude) {
        this._noteId = noteId;
        this._noteText = noteText;
        this._noteDate = noteDate;
        this._noteTheme = noteTheme;
        this._longitude = longitude;
        this._latitude = latitude;
    }

    public Note(String noteText, String noteDate, String noteTheme, Double longitude, Double latitude) {
        this._noteText = noteText;
        this._noteDate = noteDate;
        this._noteTheme = noteTheme;
        this._longitude = longitude;
        this._latitude = latitude;
    }

    public Note(int noteId, String noteText, String noteDate, String noteTheme) {
        this._noteId = noteId;
        this._noteText = noteText;
        this._noteDate = noteDate;
        this._noteTheme = noteTheme;
    }

    public Note(String noteText, String noteDate, String noteTheme) {
        this._noteText = noteText;
        this._noteDate = noteDate;
        this._noteTheme = noteTheme;
    }

    public int get_noteId() {
        return _noteId;
    }

    public void set_noteId(int _noteId) {
        this._noteId = _noteId;
    }

    public String get_noteText() {
        return _noteText;
    }

    public void set_noteText(String _noteText) {
        this._noteText = _noteText;
    }

    public String get_noteDate() {
        return _noteDate;
    }

    public void set_noteDate(String _noteDate) {
        this._noteDate = _noteDate;
    }

    public String get_noteTheme() {
        return _noteTheme;
    }

    public void set_noteTheme(String _noteTheme) {
        this._noteTheme = _noteTheme;
    }

    public Double get_longitude() {
        return _longitude;
    }

    public void set_longitude(Double _longitude) {
        this._longitude = _longitude;
    }

    public Double get_latitude() {
        return _latitude;
    }

    public void set_latitude(Double _latitude) {
        this._latitude = _latitude;
    }

    @Override
    public String toString() {
        return "Note{" +
                "_noteId=" + _noteId +
                ", _noteText='" + _noteText + '\'' +
                ", _noteDate='" + _noteDate + '\'' +
                ", _noteTheme='" + _noteTheme + '\'' +
                ", _longitude='" + _longitude + '\'' +
                ", _latitude='" + _latitude + '\'' +
                '}';
    }
}