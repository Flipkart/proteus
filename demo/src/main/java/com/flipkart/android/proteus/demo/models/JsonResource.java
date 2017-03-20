/*
 * Apache License
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION
 *
 * Copyright (c) 2017 Flipkart Internet Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.flipkart.android.proteus.demo.models;

import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.toolbox.Styles;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * JsonResource
 *
 * @author aditya.sharat
 */

public interface JsonResource {
    @GET("{path}")
    Call<JsonObject> get(@Path("path") String path);

    @GET("styles.json")
    Call<Styles> getStyles();

    @GET("layout.json")
    Call<Layout> getLayout();

    @GET("layouts.json")
    Call<Map<String, Layout>> getLayouts();
}