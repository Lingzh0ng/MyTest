package com.wearapay.selectpic;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Toast;

/**
 * .  -.--,       .--,
 * .  ( (  \.---./  ) )
 * .   '.__/o   o\__.'
 * .      {=  ^  =}
 * .       >  -  <
 * .     //       \\
 * .    "'\       /'"_.-~^`'-.
 * .       \  _  /--'         `
 * .    (((__) (__)))
 * -    高山仰止,景行行止.
 * -    虽不能至,心向往之.
 * Created by Van Liu on 2017/7/25.
 */
public class ContainsEmojiEditText extends AppCompatEditText {
    //输入表情前的光标位置
    private int cursorPos;
    //输入表情前EditText中的文本
    private String inputAfterText;
    //是否重置了EditText的内容
    private boolean resetText;
    private Context mContext;
    public ContainsEmojiEditText(Context context) {
        super(context);
        mContext = context;
        initEmojiEditText();
    }

    public ContainsEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ContainsEmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void initEmojiEditText() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    cursorPos = getSelectionEnd();
                    inputAfterText = s.toString();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    String str = s.toString();
                    if (str.length() >2&&str.length()>cursorPos) {
                        str = str.substring(cursorPos);
                    }
                    if (conTains(str)) {
                        resetText = true;
                        Toast.makeText(mContext, "亲,不能输入Emoji表情符号哟~", Toast.LENGTH_SHORT).show();
                        //是表情符号就将文本还原为输入表情符号之前的内容
                        setText(inputAfterText);
                        CharSequence text = getText();
                        if (text instanceof Spannable) {
                            Spannable spanText = (Spannable) text;
                            Selection.setSelection(spanText, text.length());
                        }
                    }
                } else {
                    resetText = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 匹配是否包含表情
     * @param source
     * @return
     */
    public static boolean conTains(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmoji(codePoint)) {//是表情返回TRUE
                return true;
            }
        }
        return false;
    }

    /**
     * 判断Emoji字符
     * @param codePoint 单个字符
     * @return
     */
    private static boolean isEmoji(char codePoint) {

        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }
}
