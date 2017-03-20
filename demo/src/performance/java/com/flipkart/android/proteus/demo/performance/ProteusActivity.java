/*
 * Copyright 2016 Flipkart Internet Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flipkart.android.proteus.demo.performance;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;

import com.flipkart.android.proteus.Proteus;
import com.flipkart.android.proteus.ProteusBuilder;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusLayoutInflater;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.demo.R;
import com.flipkart.android.proteus.toolbox.DrawableCallback;
import com.flipkart.android.proteus.toolbox.EventType;
import com.flipkart.android.proteus.toolbox.Styles;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.Value;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class ProteusActivity extends BaseActivity {

    private ProteusView proteusView;
    private Gson gson;
    private ProteusLayoutInflater builder;
    private FrameLayout container;
    private Layout pageLayout;
    private JsonObject data;
    private ViewGroup.LayoutParams layoutParams;
    private Styles styles;
    private ProteusLayoutInflater.Callback callback = new ProteusLayoutInflater.Callback() {


        @Nullable
        @Override
        public ProteusView onUnknownViewType(ProteusContext context, String type, Layout layout, JsonObject data, int index) {
            return null;
        }

        @Override
        public Layout onLayoutRequired(String type, Layout include) {
            return null;
        }

        @Override
        public View onEvent(ProteusView view, EventType eventType, Value value) {
            return null;
        }

        @Override
        public PagerAdapter onPagerAdapterRequired(ProteusView parent, List<ProteusView> children, Layout layout) {
            return null;
        }

        @Override
        public Adapter onAdapterRequired(ProteusView parent, List<ProteusView> children, Layout layout) {
            return null;
        }
    };
    private ProteusLayoutInflater.ImageLoader bitmapLoader = new ProteusLayoutInflater.ImageLoader() {
        @Override
        public void getBitmap(final ProteusView view, final String imageUrl, final DrawableCallback callback) {
            URL url;
            try {
                url = new URL(imageUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return;
            }
            new AsyncTask<URL, Integer, Bitmap>() {

                @Override
                protected Bitmap doInBackground(URL... params) {
                    try {
                        return BitmapFactory.decodeStream(params[0].openConnection().getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                protected void onPostExecute(Bitmap result) {
                    callback.setBitmap(result);
                }
            }.execute(url);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gson = new Gson();
        styles = gson.fromJson(getJsonFromFile(R.raw.styles).getAsJsonObject(), Styles.class);
        Map<String, Layout> layoutProvider = getProviderFromFile(R.raw.layout_provider);
        pageLayout = getPageLayout(R.raw.page_layout);

        data = getJsonFromFile(R.raw.data_init).getAsJsonObject();

        Proteus proteus = new ProteusBuilder().build();

        builder = proteus.getProteusContext(this, null, null, null, null).getInflater();

        container = new FrameLayout(ProteusActivity.this);
        layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        super.onCreate(savedInstanceState);
    }

    @Override
    View createAndBindView() {
        proteusView = builder.inflate(pageLayout, data, container, -1);
        return (View) proteusView;
    }

    @Override
    void attachView(View view) {
        container.addView((View) proteusView, layoutParams);
        setContentView(container);
    }

    @Override
    void onBuildComplete(long time) {
        com.flipkart.android.proteus.demo.performance.PerformanceTracker.instance(this).updateProteusRenderTime(time);
    }

    private JsonElement getJsonFromFile(int resId) {
        InputStream inputStream = getResources().openRawResource(resId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return gson.fromJson(reader, JsonElement.class);
    }

    private Layout getPageLayout(int resId) {
        InputStream inputStream = getResources().openRawResource(resId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return gson.fromJson(reader, Layout.class);
    }

    private Map<String, Layout> getProviderFromFile(int resId) {
        InputStream inputStream = getResources().openRawResource(resId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return gson.fromJson(reader, (new TypeToken<Map<String, JsonObject>>() {
        }).getType());
    }
}
