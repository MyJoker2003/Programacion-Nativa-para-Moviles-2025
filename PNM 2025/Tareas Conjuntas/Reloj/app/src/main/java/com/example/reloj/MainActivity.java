package com.example.reloj;



import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTime;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTime = findViewById(R.id.textViewTime);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);

        // Función para actualizar la hora cada segundo
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                handler.postDelayed(this, 1000); // Actualiza cada segundo
            }
        };
        handler.post(runnable);

        // Evento de botón para actualizar la hora manualmente
        buttonUpdate.setOnClickListener(v -> updateTime());
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        textViewTime.setText(currentTime);
    }
}
