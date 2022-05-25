package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCountry extends AppCompatActivity {
    private Button btn;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_country);
        btn = findViewById(R.id.button5);
        editText = findViewById(R.id.editText);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, CountryActivity.class);
        intent.putExtra("hello", editText.getText());
        startActivity(intent);
    }
}