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

package io.appium.uiautomator2.utils;

import android.support.test.uiautomator.UiObject;
import android.view.accessibility.AccessibilityNodeInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.appium.uiautomator2.model.AndroidElement;

import static io.appium.uiautomator2.utils.ReflectionUtils.method;

public abstract class ElementHelpers {

    private static Method findAccessibilityNodeInfo;

    private static AccessibilityNodeInfo elementToNode(Object element) {
        AccessibilityNodeInfo result = null;
        try {
            result = (AccessibilityNodeInfo) findAccessibilityNodeInfo.invoke((UiObject) element, 5000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Remove all duplicate elements from the provided list
     *
     * @param elements - elements to remove duplicates from
     *
     * @return a new list with duplicates removed
     */
    public static List<Object> dedupe(List<Object> elements) {
        try {
            findAccessibilityNodeInfo = method(UiObject.class, "findAccessibilityNodeInfo", long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Object> result = new ArrayList<Object>();
        List<AccessibilityNodeInfo> nodes = new ArrayList<AccessibilityNodeInfo>();

        for (Object element : elements) {
            AccessibilityNodeInfo node = elementToNode(element);
            if (!nodes.contains(node)) {
                nodes.add(node);
                result.add(element);
            }
        }

        return result;
    }

    /**
     * Return the JSONObject which Appium returns for an element
     *
     * For example, appium returns elements like [{"ELEMENT":1}, {"ELEMENT":2}]
     */
    public static JSONObject toJSON(AndroidElement el) throws JSONException {
        return new JSONObject().put("ELEMENT", el.getId());
    }
}
