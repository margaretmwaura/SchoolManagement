package com.android.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllUsers
{
    @SerializedName("results")
    private List<Student> results;
    public List<Student> getResults() {
        return results;
    }

}
