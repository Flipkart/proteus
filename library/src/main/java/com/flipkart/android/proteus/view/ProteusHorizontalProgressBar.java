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

package com.flipkart.android.proteus.view;

import android.content.Context;
import android.view.View;

import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.manager.ProteusViewManager;

/**
 * HorizontalProgressBar
 *
 * @author aditya.sharat
 */
public class ProteusHorizontalProgressBar extends com.flipkart.android.proteus.view.custom.HorizontalProgressBar implements ProteusView {

    private ProteusViewManager viewManager;

    public ProteusHorizontalProgressBar(Context context) {
        super(context);
    }

    @Override
    public ProteusViewManager getViewManager() {
        return viewManager;
    }

    @Override
    public void setViewManager(ProteusViewManager proteusViewManager) {
        this.viewManager = proteusViewManager;
    }

    @Override
    public View getAsView() {
        return this;
    }
}
