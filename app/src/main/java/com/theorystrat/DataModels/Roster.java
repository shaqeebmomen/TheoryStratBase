package com.theorystrat.DataModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;

/*
This class is meant to keep track of two lists while wrapping one as a mutable live data,
this allows for abstraction behind keeping track of the teams for an event and not having to dive deep inside the live data to keep track of previous entries
The team numbers are kept in one and the Team object instances in the other, both lists are kept in the same order
 */
public class Roster {
    private static final String TAG = "Roster";
    ArrayList<String> nums;
    MutableLiveData<ArrayList<Team>> teamList;


    public Roster() {
        nums = new ArrayList<>();
        teamList = new MutableLiveData<>(new ArrayList<Team>());
    }


    public void addTeam(@NonNull Team team) {
        // Create a copy to change and then place back into the mutable live data
        ArrayList<Team> output = teamList.getValue();
        // If we've already made a team with this name, update that team
        if (nums.contains(team.getTeamNum())) {
            // set the team object at a certain index to be the new team object passed in
            output.set(nums.indexOf(team.getTeamNum()), team);

        }
        // If we haven't made this team yet, add it then sort the list in order
        else {
            nums.add(team.getTeamNum());
            output.add(team);
            Collections.sort(output);

        }
        // Now that output properly represents the list we want to use, publish it as the live data
        teamList.setValue(output);

    }

    public void setTeamList(ArrayList<Team> teamList) {
        this.teamList.setValue(teamList);
        this.nums.clear();
        for (Team t :
                this.teamList.getValue()) {
            nums.add(t.getTeamNum());
        }
    }

    // Exposes the live data to be passed up to the UI
    public LiveData<ArrayList<Team>> getTeamList() {
        return teamList;
    }

}
