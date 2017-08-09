package com.haozi.baselibrary.utils;

import android.view.View;
import android.widget.TextView;

/**
 * Created by admin on 2017/8/9.
 */

public class ViewUtils {

    public static String getTextFromTextView(View view){
        if(view == null){
            return null;
        }
        if(view instanceof TextView){
            CharSequence charSequence = ((TextView)view).getText();
            if(charSequence != null){
                return charSequence.toString();
            }
        }
        return null;
    }

}
