package com.example.mymoneytraker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "type";
    public static final String RESULT_ITEM = "item";
    public static final int RC_ITEM_ADD = 99;


    EditText addTitle;
    EditText addPrice;
    ImageButton addBtn;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_income);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        type = getIntent().getStringExtra(EXTRA_TYPE);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra(RESULT_ITEM, new Item(addTitle.getText().toString(), Integer.parseInt(addPrice.getText().toString()), type));
                setResult(RESULT_OK, result);
                finish();
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
