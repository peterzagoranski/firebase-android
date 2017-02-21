package com.github.peterzagoranski.firebase;

import android.databinding.ObservableBoolean;
import android.os.Handler;
import android.support.annotation.CallSuper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public abstract class Model {
    public final ObservableBoolean busy = new ObservableBoolean();

    public final ObservableBoolean connected = new ObservableBoolean(true);

    public Model() {
        new Handler().postDelayed(() -> database().getReference(".info/connected").addValueEventListener(listener), 3000);
    }

    @CallSuper
    public void destroy() {
        database().getReference(".info/connected").removeEventListener(listener);
    }

    protected final FirebaseDatabase database() {
        return FirebaseDatabase.getInstance();
    }

    protected final FirebaseStorage storage() {
        return FirebaseStorage.getInstance();
    }

    protected final FirebaseAuth auth() {
        return FirebaseAuth.getInstance();
    }

    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            connected.set((boolean) dataSnapshot.getValue());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            connected.set(false);
        }
    };
}
