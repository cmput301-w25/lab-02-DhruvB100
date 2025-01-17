package com.example.listcity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button addButton;
    Button deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);
        final String[] selectedCity = {null}; // To store the currently selected city

        String []cities = {"Edmonton","Vancouver","Moscow","Sydney","Vienna","Tokyo","Beijing","Osaka","New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // Enable single selection mode

        // Set a listener to detect item selection
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedCity[0] = dataList.get(position); // Get the selected city
            Toast.makeText(MainActivity.this, "Selected: " + selectedCity[0], Toast.LENGTH_SHORT).show();
        });

        // Add button functionality
        addButton.setOnClickListener(v -> {
            // Prompt for user input using a dialog or edit text
            EditText input = new EditText(MainActivity.this);
            input.setHint("Enter city name");
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Add City")
                    .setView(input)
                    .setPositiveButton("Add", (dialog, which) -> {
                        String newCity = input.getText().toString().trim();
                        if (!newCity.isEmpty()) {
                            dataList.add(newCity);
                            cityAdapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, newCity + " added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "City name cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Delete button functionality
        deleteButton.setOnClickListener(v -> {
            if (selectedCity[0] != null) {
                dataList.remove(selectedCity[0]); // Remove the selected city
                cityAdapter.notifyDataSetChanged(); // Update the ListView

                selectedCity[0] = null; // Reset the selected city
            }
        });
    }
}