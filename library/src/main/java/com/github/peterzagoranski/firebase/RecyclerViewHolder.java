package com.github.peterzagoranski.firebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public abstract class RecyclerViewHolder<TBinding extends android.databinding.ViewDataBinding> extends RecyclerView.ViewHolder {

    public RecyclerViewHolder(@NonNull final TBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public TBinding binding() {
        return this.binding;
    }

    public abstract void bind(final int position);

    protected Context context() {
        return this.binding().getRoot().getContext();
    }

    protected void start(@NonNull Intent intent) {
        this.context().startActivity(intent);
    }

    private final TBinding binding;
}
