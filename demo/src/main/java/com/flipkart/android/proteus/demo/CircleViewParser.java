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

package com.flipkart.android.proteus.demo;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.demo.customviews.CircleView;
import com.flipkart.android.proteus.processor.StringAttributeProcessor;
import com.google.gson.JsonObject;

/**
 * CircleViewParser
 *
 * @author aditya.sharat
 */

public class CircleViewParser extends ViewTypeParser<CircleView> {

    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout, @NonNull JsonObject data, ViewGroup parent, int dataIndex) {
        return new CircleView(parent.getContext());
    }

    @Override
    protected void addAttributeProcessors() {

        addAttributeProcessor("color", new StringAttributeProcessor<CircleView>() {
            @Override
            public void setString(CircleView view, String value) {
                view.setColor(value);
            }
        });
    }
}

