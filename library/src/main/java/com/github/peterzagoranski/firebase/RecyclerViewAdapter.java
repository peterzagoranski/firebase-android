package com.github.peterzagoranski.firebase;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public abstract class RecyclerViewAdapter<TViewHolder extends RecyclerViewHolder, TModel> extends RecyclerView.Adapter<TViewHolder> implements ChildEventListener  {

    public static FirebaseDatabase database() {
        return FirebaseDatabase.getInstance();
    }

    public RecyclerViewAdapter(@NonNull final Query query, @NonNull final Class<TModel> clazz) {
        this.clazz = clazz;
        query.addChildEventListener(this);
    }

    @SuppressWarnings("unused")
    public RecyclerViewAdapter(@NonNull final DatabaseReference reference, @NonNull final Class<TModel> clazz) {
        this((Query) reference, clazz);
    }

    @Override
    public int getItemCount() {
        return snapshots.size();
    }

    @Override
    public long getItemId(int position) {
        return snapshots.get(position).getKey().hashCode();
    }

    public TModel item(int position) {
        final TModel model = snapshots.get(position).getValue(clazz);

        if (model instanceof UidModel) ((UidModel)model).uid = getRef(position).getKey();

        return model;
    }

    public DatabaseReference getRef(int position) {
        return snapshots.get(position).getRef();
    }

    @Override
    public void onChildAdded(DataSnapshot snapshot, String previous) {
        int index = 0;
        if (previous != null) {
            index = keys.indexOf(previous) + 1;
        }
        snapshots.add(index, snapshot);
        keys.add(index, snapshot.getKey());
        onItemInserted(index);
    }

    @Override
    public void onChildChanged(DataSnapshot snapshot, String previous) {
        final int index = keys.indexOf(snapshot.getKey());
        snapshots.set(index, snapshot);
        keys.set(index, snapshot.getKey());
        onItemChanged(index);
    }

    @Override
    public void onChildRemoved(DataSnapshot snapshot) {
        final int index = keys.indexOf(snapshot.getKey());
        snapshots.remove(index);
        keys.remove(index);
        onItemRemoved(index);
    }

    @Override
    public void onChildMoved(DataSnapshot snapshot, String previous) {
        final int oldIndex = keys.indexOf(snapshot.getKey());
        snapshots.remove(oldIndex);
        keys.remove(oldIndex);
        final int newIndex = previous == null ? 0 : (keys.indexOf(previous) + 1);
        snapshots.add(newIndex, snapshot);
        keys.add(newIndex, snapshot.getKey());
        onItemMoved(newIndex, oldIndex);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    }

    @CallSuper
    protected void onItemInserted(int position) {
        notifyItemInserted(position);
    }

    @CallSuper
    protected void onItemChanged(int position) {
        notifyItemChanged(position);
    }

    @CallSuper
    protected void onItemRemoved(int position) {
        notifyItemRemoved(position);
    }

    @CallSuper
    protected void onItemMoved(int position, int old) {
        notifyItemMoved(position, old);
    }

    private final Class<TModel> clazz;
    private final ArrayList<DataSnapshot> snapshots = new ArrayList<>();
    private final ArrayList<String> keys = new ArrayList<>();
}
