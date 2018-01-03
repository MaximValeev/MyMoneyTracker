package com.example.mymoneytraker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddActivity extends AppCompatActivity {

    EditText addTitle;
    EditText addPrice;
    ImageButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_income);

        addTitle = findViewById(R.id.addTitle);
        addPrice = findViewById(R.id.addPrice);
        Spannable priceText = new SpannableString(0 + getResources().getString(R.string.currencySymbol));
        priceText.setSpan(new RelativeSizeSpan(0.75f), priceText.length()-1, priceText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        addPrice.setHint(priceText);
        addBtn = findViewById(R.id.addButton);

        addTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonStateChange();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonStateChange();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void buttonStateChange (){
        if(!TextUtils.isEmpty(addTitle.getText().toString()) && !TextUtils.isEmpty(addPrice.getText().toString())){
            addBtn.setEnabled(true);
            addBtn.setColorFilter(Color.argb(255,0,0,0));
        } else {
            addBtn.setColorFilter(Color.argb(255,149,152,154));
            addBtn.setEnabled(false);
        }
    }

}
