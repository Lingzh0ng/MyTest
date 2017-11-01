package com.wearapay.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 2017/8/3.
 */
public class FlowLayout extends ViewGroup {
  private static final int LEFT = -1;
  private static final int CENTER = 0;
  private static final int RIGHT = 1;

  protected List<List<View>> mAllViews = new ArrayList<>();
  protected List<Integer> mLineHeight = new ArrayList<>();
  protected List<Integer> mLineWidth = new ArrayList<>();
  private int mGravity;
  private List<View> lineViews = new ArrayList<>();

  public List<String> getContacts() {
    return contacts;
  }

  private List<String> contacts = new ArrayList<>();
  private List<ContactView> contactsViews = new ArrayList<>();

  public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
    mGravity = ta.getInt(R.styleable.TagFlowLayout_gravity, LEFT);
    ta.recycle();
    initContactView();
  }

  public FlowLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FlowLayout(Context context) {
    this(context, null);
  }

  public void setContacts(List<String> contacts) {
    this.contacts.clear();
    this.contactsViews.clear();
    this.contacts.addAll(contacts);
    removeAllViews();
    initContactView();
  }

  public void addContact(String contact) {
    if (!TextUtils.isEmpty(contact)) {
      addContactView(contact);
    }
  }

  private void addContactView(String contact) {
    if (contacts.size() >= 1) {
      contactsViews.get(contacts.size() - 1).setStatus(ContactView.Status.Normal);
      ContactView contactView = createContact(ContactView.Status.Last, contact);
      contactsViews.add(contactView);
      addView(contactView);
    } else {
      contactsViews.get(0).setStatus(ContactView.Status.Last);
      contactsViews.get(0).setTvContact(contact);
    }
    contacts.add(contact);
  }

  private void initContactView() {
    ContactView contactView;
    if (contacts == null || contacts.size() == 0) {
      contactView = createContact(ContactView.Status.First, "");
      contactsViews.add(contactView);
      addView(contactView);
    } else {
      for (int i = 0; i < contacts.size(); i++) {
        if (i == contacts.size() - 1) {
          contactView = createContact(ContactView.Status.Last, contacts.get(i));
        } else {
          contactView = createContact(ContactView.Status.Normal, contacts.get(i));
        }
        contactsViews.add(contactView);
        addView(contactView);
      }
    }
  }

  private ContactView createContact(ContactView.Status status, String contact) {
    ContactView contactView = new ContactView(getContext());
    contactView.setStatus(status);
    contactView.setTvContact(contact);
    contactView.setOnContactClickListener(new ContactView.OnContactClickListener() {
      @Override public void delClick(View view, String contact) {
        Toast.makeText(getContext(), contact, Toast.LENGTH_SHORT).show();
        removeContactView(view, contact);
      }

      @Override public void editTextChangeNoFocus(String contact) {
        if (!TextUtils.isEmpty(contact)) {
          addContactView(contact);
        }
      }
    });
    return contactView;
  }

  private void removeContactView(View view, String contact) {
    if (contacts.size() > 1) {
      if (contacts.get(contacts.size() - 1).equals(contact)) {
        contactsViews.get(contacts.size() - 2).setStatus(ContactView.Status.Last);
      }
      contacts.remove(contact);
      removeView(view);
      contactsViews.remove(view);
    } else {
      contacts.remove(contact);
      contactsViews.get(0).setStatus(ContactView.Status.First);
      contactsViews.get(0).setTvContact("");
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
    int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
    int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
    int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

    // wrap_content
    int width = 0;
    int height = 0;

    int lineWidth = 0;
    int lineHeight = 0;

    int cCount = getChildCount();

    for (int i = 0; i < cCount; i++) {
      View child = getChildAt(i);
      if (child.getVisibility() == View.GONE) {
        if (i == cCount - 1) {
          width = Math.max(lineWidth, width);
          height += lineHeight;
        }
        continue;
      }
      measureChild(child, widthMeasureSpec, heightMeasureSpec);
      MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

      int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
      int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

      if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
        width = Math.max(width, lineWidth);
        lineWidth = childWidth;
        height += lineHeight;
        lineHeight = childHeight;
      } else {
        lineWidth += childWidth;
        lineHeight = Math.max(lineHeight, childHeight);
      }
      if (i == cCount - 1) {
        width = Math.max(lineWidth, width);
        height += lineHeight;
      }
    }
    setMeasuredDimension(
        //
        modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
        modeHeight == MeasureSpec.EXACTLY ? sizeHeight
            : height + getPaddingTop() + getPaddingBottom()//
    );
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    mAllViews.clear();
    mLineHeight.clear();
    mLineWidth.clear();
    lineViews.clear();

    int width = getWidth();

    int lineWidth = 0;
    int lineHeight = 0;

    int cCount = getChildCount();

    for (int i = 0; i < cCount; i++) {
      View child = getChildAt(i);
      if (child.getVisibility() == View.GONE) continue;
      MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

      int childWidth = child.getMeasuredWidth();
      int childHeight = child.getMeasuredHeight();

      if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin
          > width - getPaddingLeft() - getPaddingRight()) {
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);
        mLineWidth.add(lineWidth);

        lineWidth = 0;
        lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
        lineViews = new ArrayList<View>();
      }
      lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
      lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
      lineViews.add(child);
    }
    mLineHeight.add(lineHeight);
    mLineWidth.add(lineWidth);
    mAllViews.add(lineViews);

    int left = getPaddingLeft();
    int top = getPaddingTop();

    int lineNum = mAllViews.size();

    for (int i = 0; i < lineNum; i++) {
      lineViews = mAllViews.get(i);
      lineHeight = mLineHeight.get(i);

      // set gravity
      int currentLineWidth = this.mLineWidth.get(i);
      switch (this.mGravity) {
        case LEFT:
          left = getPaddingLeft();
          break;
        case CENTER:
          left = (width - currentLineWidth) / 2 + getPaddingLeft();
          break;
        case RIGHT:
          left = width - currentLineWidth + getPaddingLeft();
          break;
      }

      for (int j = 0; j < lineViews.size(); j++) {
        View child = lineViews.get(j);
        if (child.getVisibility() == View.GONE) {
          continue;
        }

        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        int lc = left + lp.leftMargin;
        int tc = top + lp.topMargin;
        int rc = lc + child.getMeasuredWidth();
        int bc = tc + child.getMeasuredHeight();

        child.layout(lc, tc, rc, bc);

        left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
      }
      top += lineHeight;
    }
  }

  @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new MarginLayoutParams(getContext(), attrs);
  }

  @Override protected LayoutParams generateDefaultLayoutParams() {
    return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
  }

  @Override protected LayoutParams generateLayoutParams(LayoutParams p) {
    return new MarginLayoutParams(p);
  }
}
