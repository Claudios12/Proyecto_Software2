package com.example.conversor;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConversionActivity extends AppCompatActivity {

    private EditText edtValueToConvert;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private Button btnConvert;
    private TextView txtResult;
    private Button backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        edtValueToConvert = findViewById(R.id.edtValueToConvert);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        btnConvert = findViewById(R.id.btnConvert);
        txtResult = findViewById(R.id.txtResult);
        backbutton= findViewById(R.id.backButton);

        HistoryManager.init(this);

        // Configura los spinners (si quieres usar fragmentos o directamente aquí, depende de la implementación)
        setupSpinners();

        backbutton.setOnClickListener(v -> {
            Intent intent = new Intent(ConversionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnConvert.setOnClickListener(v -> {
            try {
                double inputValue = Double.parseDouble(edtValueToConvert.getText().toString());
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();

                double result = convert(inputValue, fromUnit, toUnit);

                txtResult.setText("Resultado: " + result);

                // También puedes guardar en el historial:
                String historyEntry = inputValue + " " + fromUnit + " = " + result + " " + toUnit;
                HistoryManager.addHistory(historyEntry);

            } catch (NumberFormatException e) {
                txtResult.setText("Por favor ingresa un número válido");
            }
        });

    }

    private void setupSpinners() {
        // Lista de unidades de ejemplo
        String[] units = {"Metros", "Kilómetros", "Millas", "Pulgadas"};

        // Crea un ArrayAdapter para llenar los spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el adaptador a los spinners
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
    }

    private double convert(double value, String fromUnit, String toUnit) {
        double valueInMeters = 0;

        // Primero convertimos todo a METROS
        switch (fromUnit) {
            case "Metros":
                valueInMeters = value;
                break;
            case "Kilómetros":
                valueInMeters = value * 1000;
                break;
            case "Millas":
                valueInMeters = value * 1609.34;
                break;
            case "Pulgadas":
                valueInMeters = value * 0.0254;
                break;
            default:
                throw new IllegalArgumentException("Unidad de origen desconocida: " + fromUnit);
        }
            double result = 0.0;
        // Luego, convertimos desde METROS a la unidad destino
        switch (toUnit) {
            case "Metros":
               result =  valueInMeters;
               break;
            case "Kilómetros":
                result= valueInMeters / 1000;
                break;
            case "Millas":
                result= valueInMeters / 1609.34;
                break;
            case "Pulgadas":
                result= valueInMeters / 0.0254;
                break;
            default:
                throw new IllegalArgumentException("Unidad de destino desconocida: " + toUnit);

        }
        txtResult.setText("Resultado: " + String.format("%.2f", result));

        return result;
    }
}
