package com.example.pasgenap10rpl121;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Bundle bundle;
    TextView tv_desc;
    String title, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_desc = findViewById(R.id.tv_desc);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            desc = bundle.getString("note");
        }

        getSupportActionBar().setTitle(title);
        tv_desc.setText(desc);

    }
}