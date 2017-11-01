/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.uiautomator2.model.internal;

import java.util.Hashtable;
import java.util.regex.Pattern;

import io.appium.uiautomator2.model.AndroidElement;

/**
 * A cache of elements that the app has seen.
 */
public class AndroidElementsHash {

    private static final Pattern endsWithInstancePattern = Pattern.compile(".*INSTANCE=\\d+]$");
    private static AndroidElementsHash instance;
    private final Hashtable<String, AndroidElement> elements;
    private Integer counter;

    /**
     * Constructor
     */
    public AndroidElementsHash() {
        counter = 0;
        elements = new Hashtable<String, AndroidElement>();
    }

    public static AndroidElementsHash getInstance() {
        if (AndroidElementsHash.instance == null) {
            AndroidElementsHash.instance = new AndroidElementsHash();
        }
        return AndroidElementsHash.instance;
    }

    /**
     * Return an element given an Id.
     *
     * @return {@link AndroidElement}
     */
    public AndroidElement getElement(final String key) {
        return elements.get(key);
    }
}
