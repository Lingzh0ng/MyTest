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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import io.appium.uiautomator2.common.exceptions.UiAutomator2Exception;

public class ReflectionUtils {

    /**
     * Clears the in-process Accessibility cache, removing any stale references. Because the
     * AccessibilityInteractionClient singleton stores copies of AccessibilityNodeInfo instances,
     * calls to public APIs such as `recycle` do not guarantee cached references get updated. See
     * the android.view.accessibility AIC and ANI source code for more information.
     */
    public static boolean clearAccessibilityCache() throws UiAutomator2Exception {
        boolean success = false;

        try {
            final Class c = Class
                    .forName("android.view.accessibility.AccessibilityInteractionClient");
            final Method getInstance = ReflectionUtils.method(c, "getInstance");
            final Object instance = getInstance.invoke(null);
            final Method clearCache = ReflectionUtils.method(instance.getClass(),
                    "clearCache");
            clearCache.invoke(instance);

            success = true;
        } catch (IllegalAccessException e) {
            Logger.error("Failed to clear Accessibility Node cache. ", e);
        } catch (InvocationTargetException e) {
            Logger.error("Failed to clear Accessibility Node cache. ", e);
        } catch (ClassNotFoundException e) {
            Logger.error("Failed to clear Accessibility Node cache. ", e);
        }
        return success;
    }

    public static Class getClass(final String name) throws UiAutomator2Exception {
        try {
            return Class.forName(name);
        } catch (final ClassNotFoundException e) {
            final String msg = String.format("unable to find class %s", name);
            throw new UiAutomator2Exception(msg, e);
        }
    }

    public static Object getField(final Class clazz, final String fieldName, final Object object) throws UiAutomator2Exception {
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            return field.get(object);
        } catch (final Exception e) {
            final String msg = String.format("error while getting field %s from object %s", fieldName, object);
            Logger.error(msg + " " + e.getMessage());
            throw new UiAutomator2Exception(msg, e);
        }
    }

    public static Object getField(final String field, final Object object) throws UiAutomator2Exception {
        return getField(object.getClass(), field, object);
    }

    public static Object getField(final String className, final String field, final Object object) throws UiAutomator2Exception {
        return getField(getClass(className), field, object);
    }

    public static Object invoke(final Method method, final Object object, final Object... parameters) throws UiAutomator2Exception {
        try {
            return method.invoke(object, parameters);
        } catch (final Exception e) {
            final String msg = String.format("error while invoking method %s on object %s with parameters %s", method, object, Arrays.toString(parameters));
            Logger.error(msg + " " + e.getMessage());
            throw new UiAutomator2Exception(msg, e);
        }
    }

    public static Method method(final Class clazz, final String methodName, final Class... parameterTypes) throws UiAutomator2Exception {
        try {
            final Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);

            return method;
        } catch (final Exception e) {
            final String msg = String.format("error while getting method %s from class %s with parameter types %s", methodName, clazz, Arrays.toString(parameterTypes));
            Logger.error(msg + " " + e.getMessage());
            throw new UiAutomator2Exception(msg, e);
        }
    }

    public static Method method(final String className, final String method, final Class... parameterTypes) throws UiAutomator2Exception {
        return method(getClass(className), method, parameterTypes);
    }
}
