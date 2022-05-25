package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import android.os.Bundle;

public class CountryActivity extends AppCompatActivity {
    ArrayList<State> states = new ArrayList<State>();
    ListView countriesList;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        button = findViewById(R.id.button6);
        setInitialData();
        try {
            Bundle arguments = getIntent().getExtras();
            String name = arguments.get("hello").toString();
            addC(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // получаем элемент ListView
        countriesList = findViewById(R.id.countriesList);
        // создаем адаптер
        StateAdapter stateAdapter = new StateAdapter(this, R.layout.list_item, states);
        // устанавливаем адаптер
        countriesList.setAdapter(stateAdapter);
        // слушатель выбора в списке
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // получаем выбранный пункт
                State selectedState = (State)parent.getItemAtPosition(position);
                onCountry(v, selectedState.getName());
                Toast.makeText(getApplicationContext(), "Был выбран населенный пункт " + selectedState.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        countriesList.setOnItemClickListener(itemListener);
    }
    private void setInitialData(){

        states.add(new State ("Уфа", "Уфа", R.drawable.a01d));
        states.add(new State ("Аргентина", "Буэнос-Айрес", R.drawable.a01d));
        states.add(new State ("Колумбия", "Богота", R.drawable.a01d));
        states.add(new State ("Уругвай", "Монтевидео", R.drawable.a01d));
        states.add(new State ("Чили", "Сантьяго", R.drawable.a01d));

    }
    private void addC(String s){

        states.add(new State (s, s, R.drawable.a01d));


    }
    public void onCountry(View view, String country) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("hello", country);
        startActivity(intent);


    }
    public void onAdd(View view){
        Intent intent = new Intent(this, AddCountry.class);
        startActivity(intent);
    }
}