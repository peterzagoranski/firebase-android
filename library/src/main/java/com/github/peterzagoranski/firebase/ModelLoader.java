package com.github.peterzagoranski.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;

public class ModelLoader<TModel extends Model> extends Loader<TModel> {

    public ModelLoader(@NonNull final Context context, @NonNull final TModel model) {
        super(context);

        this.model = model;
    }

    @Override
    protected void onStartLoading() {
        deliverResult(model);
    }

    @Override
    protected void onForceLoad() {
        deliverResult(model);
    }

    @Override
    protected void onReset() {
        model.destroy();
    }

    protected final TModel model;
}
