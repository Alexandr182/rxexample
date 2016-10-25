package sample.rxexample.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by profiralexandr on 17/08/16.
 */

public class SearchResult extends RealmObject {
    @SerializedName("lf")
    @PrimaryKey
    private String name;
    @SerializedName("freq")
    private int frequency;
    @SerializedName("since")
    private int year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
