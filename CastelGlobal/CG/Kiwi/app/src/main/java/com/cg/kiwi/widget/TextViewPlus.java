package com.cg.kiwi.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Saurabh on 6/20/2017.
 */

public class TextViewPlus extends AppCompatTextView {

    Context mContext;
    Typeface fontnormal;

    public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewPlus(Context context) {
        super(context);
        init(context, null);
    }

    void init(Context context, AttributeSet attrs) {
        if (isInEditMode())
            return;
        mContext = context;
        fontnormal = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/pt_sans_regular.ttf");
        this.setTypeface(fontnormal);

    }
}
