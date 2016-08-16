package sample.rxexample.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by profiralexandr on 17/08/16.
 */

public class SearchResult extends RealmObject {
    @SerializedName("lf")
    private String name;
    @SerializedName("freq")
    private int frequency;
    @SerializedName("since")
    private int sinceYear;

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

    public int getSinceYear() {
        return sinceYear;
    }

    public void setSinceYear(int sinceYear) {
        this.sinceYear = sinceYear;
    }
}