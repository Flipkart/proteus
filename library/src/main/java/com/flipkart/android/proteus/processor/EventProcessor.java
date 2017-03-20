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


import com.flipkart.android.proteus.AttributeProcessor;
import com.flipkart.android.proteus.ProteusLayoutInflater;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.toolbox.EventType;
import com.flipkart.android.proteus.value.AttributeResource;
import com.flipkart.android.proteus.value.Resource;
import com.flipkart.android.proteus.value.StyleResource;
import com.flipkart.android.proteus.value.Value;

/**
 * Use this as the base processor for handling events like OnClick , OnLongClick , OnTouch etc.
 * Created by prateek.dixit on 20/11/14.
 */

public abstract class EventProcessor<T> extends AttributeProcessor<T> {

    @Override
    public void handleValue(T view, Value value) {
        setOnEventListener(view, value);
    }

    @Override
    public void handleResource(T view, Resource resource) {
        setOnEventListener(view, resource);
    }

    @Override
    public void handleAttributeResource(T view, AttributeResource attribute) {
        setOnEventListener(view, attribute);
    }

    @Override
    public void handleStyleResource(T view, StyleResource style) {
        setOnEventListener(view, style);
    }

    public abstract void setOnEventListener(T view, Value value);

    /**
     * This delegates Event with required attributes to client
     */
    public void fireEvent(ProteusView view, EventType eventType, Value parser) {
        ProteusLayoutInflater.Callback callback = view.getViewManager().getContext().getCallback();
        if (null != callback) {
            callback.onEvent(view, eventType, parser);
        }
    }
}
