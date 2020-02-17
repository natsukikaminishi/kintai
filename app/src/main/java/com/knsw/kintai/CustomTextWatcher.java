package com.knsw.kintai;

import android.text.Editable;
import android.text.TextWatcher;

/* CustomTextWatcher
 * 必須Overrideが多すぎるので、このクラスで簡略化
 */
public class CustomTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){};

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){}

    @Override
    public void afterTextChanged(Editable editable) {};

}
