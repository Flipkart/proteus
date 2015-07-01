package com.flipkart.layoutengine.view;

import android.view.View;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * A wrapper class to update the views build by a {@link com.flipkart.layoutengine.builder.LayoutBuilder}.
 * Enables consumers of the views built by the builder to update the data at
 * runtime.
 * <p>
 * The {@link com.flipkart.layoutengine.builder.LayoutBuilder#build} method returns a
 * {@link com.flipkart.layoutengine.view.ProteusView}. To use raw view call it's
 * {@link ProteusView#getView()} method.
 * </p>
 * In order to update the data associated with the {@link android.view.View} use the
 * {@link com.flipkart.layoutengine.view.ProteusView#updateData(com.google.gson.JsonObject)}.
 *
 * @author Aditya Sharat {@literal <aditya.sharat@flipkart.com>}
 */
public interface ProteusView {

    /**
     * @return reference to the view {@link android.view.View} wrapped by the {@link ProteusView}
     */
    View getView();

    /**
     * @return the index of this view in it's parent.
     */
    int getIndex();

    /**
     * @return the parent {@link ProteusView} of this {@link ProteusView}.
     */
    ProteusView getParent();

    /**
     * Adds a child {@link ProteusView}.
     *
     * @param view The {@link ProteusView} to add as a child.
     */
    void addChild(ProteusView view);

    /**
     * @return the list of children.
     */
    ArrayList<ProteusView> getChildren();

    /**
     * Updates the data associated with view wrapped by the {@link ProteusView}
     * with new {@link org.json.JSONObject} object.
     *
     * @param data new {@link org.json.JSONObject} object which will used to update the view.
     * @return reference to the updated view {@link android.view.View} wrapped by the
     * {@link ProteusView}
     */
    View updateData(JsonObject data);

    /**
     * Replace the {@link View}, {@link com.flipkart.layoutengine.binding.Binding} and children
     * associated to this {@link ProteusView}.
     *
     * @param view The {@link ProteusView} to use for the replacement
     */
    void replaceView(ProteusView view);
}
