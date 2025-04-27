package com.example.relicktcgapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class DecidirActivity extends AppCompatActivity {

    Button Jugador1Button, Jugador2Button, IniciarButton;
    TextView resultado1TextView, resultado2TextView, JugadorInicialTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decidir);

        Jugador1Button = findViewById(R.id.Jugador1Button);

        Jugador2Button = findViewById(R.id.Jugador2Button);

        resultado1TextView = findViewById(R.id.resultado1TextView);

        resultado2TextView = findViewById(R.id.resultado2TextView);

        IniciarButton = findViewById(R.id.IniciarButton);

        JugadorInicialTextView = findViewById(R.id.JugadorInicialTextView);

        Jugador1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dado(1);
            }
        });
        Jugador2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dado(2);
            }
        });
        IniciarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarJuego();
            }
        });
    }
    int dado1 = 0;
    int dado2 = 0;

    private int dado(int jugador) {
        Random random = new Random();

        int dado = random.nextInt(6) + 1;

        switch (jugador) {
            case 1:
                resultado1TextView.setText("Resultado " + dado);
                dado1 = dado;
                break;
            case 2:
                resultado2TextView.setText("Resultado " + dado);
                dado2 = dado;
                break;
        }
        return dado;
    }

    private int JugadorInicial(int dado1, int dado2) {
        if (dado1 > dado2) {
            JugadorInicialTextView.setText("Empieza Jugador 1");
            return 1;
        } else if (dado2 > dado1) {
            JugadorInicialTextView.setText("Empieza Jugador 2");
            return 2;
        } else {
            JugadorInicialTextView.setText("Empate, vuelvan a tirar");
            return 0;
        }
    }


    private void iniciarJuego() {
        Intent intent = new Intent(DecidirActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}