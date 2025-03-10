package com.example.caculadoraareayperimetro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText etBase, etAltura, etRadio;
    private TextView tvResultado, tvFigura, tvBase, tvAltura, tvRadio;
    private Button btnCalcular;
    private RadioGroup radioGroup;
    private RadioButton rbRectangulo, rbCirculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando los componentes de la UI
        etBase = findViewById(R.id.et_base);
        etAltura = findViewById(R.id.et_altura);
        etRadio = findViewById(R.id.et_radio);
        tvResultado = findViewById(R.id.tv_resultado);
        tvFigura = findViewById(R.id.tv_figura);
        tvBase = findViewById(R.id.tv_base);
        tvAltura = findViewById(R.id.tv_altura);
        tvRadio = findViewById(R.id.tv_radio);
        btnCalcular = findViewById(R.id.btn_calcular);
        radioGroup = findViewById(R.id.radioGroup);
        rbRectangulo = findViewById(R.id.rb_rectangulo);
        rbCirculo = findViewById(R.id.rb_circulo);

        // Acción al hacer clic en el botón Calcular
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultado = "";
                if (rbRectangulo.isChecked()) {
                    // Rectángulo
                    double base = Double.parseDouble(etBase.getText().toString());
                    double altura = Double.parseDouble(etAltura.getText().toString());
                    double area = base * altura;
                    double perimetro = 2 * (base + altura);
                    resultado = "Área: " + area + " m²\nPerímetro: " + perimetro + " m";
                } else if (rbCirculo.isChecked()) {
                    // Círculo
                    double radio = Double.parseDouble(etRadio.getText().toString());
                    double area = Math.PI * Math.pow(radio, 2);
                    double perimetro = 2 * Math.PI * radio;
                    resultado = "Área: " + area + " m²\nPerímetro: " + perimetro + " m";
                }
                tvResultado.setText(resultado);
            }
        });

        // Cambiar entre rectángulo y círculo
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_rectangulo) {
                tvBase.setVisibility(View.VISIBLE);
                etBase.setVisibility(View.VISIBLE);
                tvAltura.setVisibility(View.VISIBLE);
                etAltura.setVisibility(View.VISIBLE);
                tvRadio.setVisibility(View.GONE);
                etRadio.setVisibility(View.GONE);
            } else if (checkedId == R.id.rb_circulo) {
                tvBase.setVisibility(View.GONE);
                etBase.setVisibility(View.GONE);
                tvAltura.setVisibility(View.GONE);
                etAltura.setVisibility(View.GONE);
                tvRadio.setVisibility(View.VISIBLE);
                etRadio.setVisibility(View.VISIBLE);
            }
        });
    }
}
