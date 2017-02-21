package com.github.peterzagoranski.firebase;

import com.google.firebase.database.Exclude;

public abstract class UidModel {
    @Exclude
    public String uid;
}
