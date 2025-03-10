package com.example.contador;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int steps = 0; // Contador de pasos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los elementos de la interfaz
        TextView textViewSteps = findViewById(R.id.textViewSteps);
        Button buttonStep = findViewById(R.id.buttonStep);

        // Evento para registrar un paso
        buttonStep.setOnClickListener(v -> {
            steps++;
            textViewSteps.setText("Pasos: " + steps);
        });
    }
}
