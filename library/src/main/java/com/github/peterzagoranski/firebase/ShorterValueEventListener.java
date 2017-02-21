package com.github.peterzagoranski.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public abstract class ShorterValueEventListener implements ValueEventListener {

    @Override
    public void onDataChange(DataSnapshot snapshot) {
        onEvent(null, snapshot);
    }

    @Override
    public void onCancelled(DatabaseError error) {
        onEvent(error, null);
    }

    public abstract void onEvent(DatabaseError error, DataSnapshot snapshot);
}
