package com.example;

import com.google.common.base.Function;
import io.appium.java_client.android.AndroidDriver;

/**
 * Created by mike on 24/02/2017.
 */
public interface ExpectedCondition<T> extends Function<AndroidDriver, T> {
}
