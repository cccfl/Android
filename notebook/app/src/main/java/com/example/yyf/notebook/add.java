package com.example.yyf.notebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class add extends AppCompatActivity {
    private EditText nameEdit;
    private EditText numberEdit;
    private Button add;

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = nameEdit.getText().toString();
                String number = numberEdit.getText().toString();

            }

        });
    }
}
