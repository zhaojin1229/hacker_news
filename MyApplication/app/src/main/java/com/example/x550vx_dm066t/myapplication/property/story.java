package com.example.x550vx_dm066t.myapplication.property;

/**
 * Created by x550vx-dm066t on 15/10/2017.
 */

public class story {

    //private variables
    String _id;
    String _title;
    String _type;
    String _by;
    String _kids;
    String _score;

    public String get_score() {
        return _score;
    }

    public void set_score(String _score) {
        this._score = _score;
    }



    // Empty constructor
    public story(){

    }
    // constructor
    public story(String id, String title, String by, String type, String kids){
        this._id = id;
        this._title = title;
        this._by = by;
        this._type = type;
        this._kids = kids;
    }
    public String getID(){
        return this._id;
    }
    public void setID(String id){
        this._id = id;
    }
    public String getTitle(){
        return this._title;
    }
    public void seTitle(String title){
        this._title = title;
    }
    public String getBy(){
        return this._by;
    }
    public void setBy(String by){
        this._by = by;
    }
    public String getType(){
        return this._type;
    }
    public void setType(String type){
        this._type = type;
    }
    public String getKids(){
        return this._kids;
    }
    public void setKids(String kids){
        this._kids = kids;
    }
}
