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

package com.flipkart.android.proteus.toolbox;

import android.annotation.SuppressLint;
import android.os.Build;

import com.google.gson.JsonElement;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Aditya Sharat on 18-05-2015.
 */
public abstract class Formatter {

    public static final Formatter NOOP = new Formatter() {
        @Override
        public String format(JsonElement elementValue) {
            if (elementValue.isJsonPrimitive()) {
                return elementValue.getAsString();
            }
            return elementValue.toString();
        }

        @Override
        public String getName() {
            return "noop";
        }
    };

    public static final Formatter NUMBER = new Formatter() {

        private DecimalFormat formatter;

        @Override
        public String format(JsonElement elementValue) {
            double valueAsNumber;
            try {
                valueAsNumber = Double.parseDouble(elementValue.getAsString());
            } catch (NumberFormatException e) {
                return elementValue.toString();
            }
            formatter = new DecimalFormat("#,###");
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
                formatter.setRoundingMode(RoundingMode.FLOOR);
            }
            formatter.setMinimumFractionDigits(0);
            formatter.setMaximumFractionDigits(2);
            return formatter.format(valueAsNumber);
        }

        @Override
        public String getName() {
            return "number";
        }
    };

    public static final Formatter DATE = new Formatter() {

        @SuppressLint("SimpleDateFormat")
        private SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat")
        private SimpleDateFormat to = new SimpleDateFormat("d MMM, E");

        @Override
        public String format(JsonElement elementValue) {
            try {
                // 2015-06-18 12:01:37
                Date date = from.parse(elementValue.getAsString());
                return to.format(date);
            } catch (Exception e) {
                return elementValue.toString();
            }
        }

        @Override
        public String getName() {
            return "date";
        }
    };

    public static final Formatter INDEX = new Formatter() {
        @Override
        public String format(JsonElement elementValue) {
            int valueAsNumber;
            try {
                valueAsNumber = Integer.parseInt(elementValue.getAsString());
            } catch (NumberFormatException e) {
                return elementValue.toString();
            }
            return String.valueOf(valueAsNumber + 1);
        }

        @Override
        public String getName() {
            return "index";
        }
    };

    public static final Formatter JOIN = new Formatter() {
        @Override
        public String format(JsonElement elementValue) {
            if (elementValue.isJsonArray()) {
                return Utils.getStringFromArray(elementValue.getAsJsonArray(), ",");
            } else {
                return elementValue.toString();
            }
        }

        @Override
        public String getName() {
            return "join";
        }
    };

    public abstract String format(JsonElement elementValue);

    public abstract String getName();
}
