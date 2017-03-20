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

package com.flipkart.android.proteus.processor;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.view.View;

import com.flipkart.android.proteus.AttributeProcessor;
import com.flipkart.android.proteus.value.AttributeResource;
import com.flipkart.android.proteus.value.Primitive;
import com.flipkart.android.proteus.value.Resource;
import com.flipkart.android.proteus.value.StyleResource;
import com.flipkart.android.proteus.value.Value;
import com.flipkart.android.proteus.parser.ParseHelper;

/**
 * @author kirankumar
 * @author aditya.sharat
 */
public abstract class StringAttributeProcessor<V extends View> extends AttributeProcessor<V> {

    public static final String EMPTY_STRING = "";
    public static final Primitive EMPTY = new Primitive("");
    private static final String STRING_RESOURCE_PREFIX = "@string/";

    public static boolean isLocalStringResource(String value) {
        return value.startsWith(STRING_RESOURCE_PREFIX);
    }

    /**
     * @param view  View
     * @param value
     */
    @Override
    public void handleValue(V view, Value value) {
        String string = value.getAsString();
        if (ParseHelper.isLocalAttribute(string) || isLocalStringResource(string)) {
            process(view, precompile(value, view.getContext()));
        } else {
            setString(view, string);
        }
    }

    @Override
    public void handleResource(V view, Resource resource) {
        String string = resource.getString(view.getContext());
        setString(view, null == string ? EMPTY_STRING : string);
    }

    @Override
    public void handleAttributeResource(V view, AttributeResource attribute) {
        TypedArray a = attribute.apply(view.getContext());
        setString(view, a.getString(0));
    }

    @Override
    public void handleStyleResource(V view, StyleResource style) {
        TypedArray a = style.apply(view.getContext());
        setString(view, a.getString(0));
    }

    /**
     * @param view View
     */
    public abstract void setString(V view, String value);

    @Override
    public Value compile(@Nullable Value value, Context context) {
        if (null == value || value.isNull()) {
            return EMPTY;
        }
        return value;
    }
}
