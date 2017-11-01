package io.appium.uiautomator2.model;

import android.graphics.Rect;
import android.support.test.uiautomator.UiObjectNotFoundException;

import java.util.List;

import io.appium.uiautomator2.common.exceptions.InvalidCoordinatesException;
import io.appium.uiautomator2.common.exceptions.InvalidSelectorException;
import io.appium.uiautomator2.common.exceptions.NoAttributeFoundException;
import io.appium.uiautomator2.common.exceptions.UiAutomator2Exception;
import io.appium.uiautomator2.utils.Point;

public interface AndroidElement {

    public By getBy();

    public void clear() throws UiObjectNotFoundException;

    public void click() throws UiObjectNotFoundException;

    public boolean longClick() throws UiObjectNotFoundException;

    public String getText() throws UiObjectNotFoundException;

    public String getName() throws UiObjectNotFoundException;

    public String getStringAttribute(final String attr) throws UiObjectNotFoundException, NoAttributeFoundException;

    public boolean getBoolAttribute(final String attr)
            throws UiObjectNotFoundException, NoAttributeFoundException, UiAutomator2Exception;

    public void setText(final String text, boolean unicodeKeyboard) throws UiObjectNotFoundException;

    public String getId();

    public Rect getBounds() throws UiObjectNotFoundException;

    public Object getChild(final Object sel) throws UiObjectNotFoundException, InvalidSelectorException, ClassNotFoundException;

    public List<Object> getChildren(final Object selector, final By by) throws UiObjectNotFoundException, InvalidSelectorException, ClassNotFoundException;

    public String getContentDesc() throws UiObjectNotFoundException;

    public Object getUiObject();

    public Point getAbsolutePosition(final Point point)
            throws UiObjectNotFoundException, InvalidCoordinatesException;

    public boolean dragTo(final int destX, final int destY, final int steps)
            throws UiObjectNotFoundException, InvalidCoordinatesException;

    public boolean dragTo(final Object destObj, final int steps)
            throws UiObjectNotFoundException, InvalidCoordinatesException;
}
