package com.wearapay.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lyz on 2017/8/3.
 */
public class ContactView extends LinearLayout {

  public enum Status {
    First,
    Last,
    Normal
  }

  private LinearLayout llContact;
  private TextView tvContact;
  private ImageView ivDel;
  private EditText edInput;
  private String contact;

  private Status currentStatus = Status.Normal;

  public void setOnContactClickListener(OnContactClickListener onContactClickListener) {
    this.onContactClickListener = onContactClickListener;
  }

  private OnContactClickListener onContactClickListener;

  public ContactView(Context context) {
    this(context, null);
  }

  public ContactView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ContactView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
    LinearLayout.LayoutParams layoutParams =
        new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dip2px(30));
    layoutParams.bottomMargin = 5;
    layoutParams.topMargin = 5;
    layoutParams.rightMargin = 5;
    layoutParams.leftMargin = 5;
    setLayoutParams(layoutParams);
  }

  public int dip2px(float dpValue) {
    final float scale = getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  private void init(final Context context) {
    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.contacts_view, this, true);
    llContact = findViewById(R.id.llContact);
    tvContact = findViewById(R.id.tvContact);
    ivDel = findViewById(R.id.ivDel);
    edInput = findViewById(R.id.edInput);

    ivDel.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View view) {
        if (onContactClickListener != null) {
          onContactClickListener.delClick(ContactView.this, contact);
        }
      }
    });
    edInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
          return true;
        }
        return false;
      }
    });
    setOnFocusChangeListener(false);
    setStatus(currentStatus);
  }

  private void setOnFocusChangeListener(boolean isListener) {
    if (isListener) {
      edInput.setOnFocusChangeListener(new OnFocusChangeListener() {
        @Override public void onFocusChange(View v, boolean hasFocus) {
          if (hasFocus) {
            // 此处为得到焦点时的处理内容
          } else {
            // 此处为失去焦点时的处理内容
            if (onContactClickListener != null) {
              onContactClickListener.editTextChangeNoFocus(getEditTextString());
              edInput.setText("");
            }
          }
        }
      });
    } else {
      if (edInput.getOnFocusChangeListener() != null) {
        edInput.setOnFocusChangeListener(null);
      }
      ;
    }
  }

  public void setStatus(Status currentStatus) {
    this.currentStatus = currentStatus;
    switch (currentStatus) {
      case First:
        setTvContact("");
        setContactTextVisibility(false);
        setEditTextVisibility(true);
        setOnFocusChangeListener(true);
        break;
      case Normal:
        setContactTextVisibility(true);
        setEditTextVisibility(false);
        setOnFocusChangeListener(false);
        break;
      case Last:
        setContactTextVisibility(true);
        setEditTextVisibility(true);
        setOnFocusChangeListener(true);
        break;
    }
  }

  public void setTvContact(String contact) {
    this.contact = contact;
    tvContact.setText(contact);
  }

  public void setEditTextVisibility(boolean isVisibility) {
    if (isVisibility) {
      edInput.setVisibility(VISIBLE);
    } else {
      edInput.setVisibility(GONE);
    }
  }

  public void setContactTextVisibility(boolean isVisibility) {
    if (isVisibility) {
      llContact.setVisibility(VISIBLE);
    } else {
      llContact.setVisibility(GONE);
    }
  }

  public String getEditTextString() {
    return edInput.getText().toString().trim();
  }

  public interface OnContactClickListener {
    void delClick(View view, String contact);

    void editTextChangeNoFocus(String contact);
  }
}
