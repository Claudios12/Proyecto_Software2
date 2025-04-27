package com.example.relicktcgapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Handler;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random random = new Random();
    int vidaJugador1 = 8;
    int vidaJugador2 = 8;

    int jugador = 1;

    TextView vidaJugador1TextView, vidaJugador2TextView, resultadoTextView;
    Button atacarJugador1Button, atacarJugador2Button;

    Button reiniciarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jugador = getIntent().getIntExtra("jugadorInicial", 1); // recibimos quien inicia

        actualizarTurno();

        vidaJugador1TextView = findViewById(R.id.vidaJugador1TextView);
        vidaJugador2TextView = findViewById(R.id.vidaJugador2TextView);
        resultadoTextView = findViewById(R.id.resultadoTextView);
        atacarJugador1Button = findViewById(R.id.atacarJugador1Button);
        atacarJugador2Button = findViewById(R.id.atacarJugador2Button);
        reiniciarButton = findViewById(R.id.reiniciarButton);

        reiniciarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reiniciar el juego
                vidaJugador1 = 8;
                vidaJugador2 = 8;
                vidaJugador1TextView.setText("Vida Jugador 1: " + vidaJugador1);
                vidaJugador2TextView.setText("Vida Jugador 2: " + vidaJugador2);
                resultadoTextView.setText("Aqui van los resultado");
                vidaJugador1TextView.setTextColor(Color.BLACK);
                vidaJugador2TextView.setTextColor(Color.BLACK);
            }
        });

        atacarJugador1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atacar(1);
            }
        });


        atacarJugador2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atacar(2);
            }
        });
    }

    private void actualizarTurno() {
        if (jugador == 1) {
            atacarJugador1Button.setEnabled(true);   // Jugador 1 puede atacar
            atacarJugador2Button.setEnabled(false);  // Jugador 2 no puede atacar
        } else {
            atacarJugador1Button.setEnabled(false);  // Jugador 1 no puede atacar
            atacarJugador2Button.setEnabled(true);   // Jugador 2 puede atacar
        }
    }


    private void atacar(int jugador) {

        if (vidaJugador1 <= 0 || vidaJugador2 <= 0) {
            switch (jugador) {
                case 1:
                    if (vidaJugador2 <= 0) {
                    animarDaño(vidaJugador2TextView);
                    cambiarColorTemporal(vidaJugador2TextView);
                    }
                    break;
                case 2:
                    if (vidaJugador1 <= 0) {
                        animarDaño(vidaJugador1TextView);
                        cambiarColorTemporal(vidaJugador1TextView);
                    }
                    break;
            }
            return; // No hacemos nada si alguien ya perdió
        }

        int dado = random.nextInt(6) + 1;
        int dañoBase = 3;
        int dañoCritico=5;
        int dañoFinal = 0;

        switch (dado) {
            case 1:
            case 2:
            case 3:
                dañoFinal = 0;
                break;
            case 4:
                dañoFinal = Math.round(dañoBase * 0.5f);
                break;
            case 5:
                dañoFinal = dañoBase;
                break;
            case 6:
                dañoFinal = dañoCritico;
                break;
        }

        if (jugador == 1) {
            if (dañoFinal == 0) {
                resultadoTextView.setText("Jugador 1 falló el ataque (sacó un " + dado + ")");
                animarDaño(vidaJugador2TextView);
            } else {
                vidaJugador2 -= dañoFinal;
                vidaJugador2TextView.setText("Vida Jugador 2: " + vidaJugador2);
                resultadoTextView.setText("Jugador 1 sacó un " + dado + ". Daño hecho: " + dañoFinal);
                animarDaño(vidaJugador2TextView);
                cambiarColorTemporal(vidaJugador2TextView);
            }
        }
        if (jugador == 2) {
            if (dañoFinal == 0) {
                resultadoTextView.setText("Jugador 2 falló el ataque (sacó un " + dado + ")");
                animarDaño(vidaJugador1TextView);
            } else {
                vidaJugador1 -= dañoFinal;
                vidaJugador1TextView.setText("Vida Jugador 1: " + vidaJugador1);
                resultadoTextView.setText("Jugador 2 sacó un " + dado + ". Daño hecho: " + dañoFinal);
                animarDaño(vidaJugador1TextView);
                cambiarColorTemporal(vidaJugador1TextView);
            }
        }
        if (vidaJugador1 <= 0) {
            resultadoTextView.setText("Jugador 2 Gana!");
            vidaJugador1TextView.setText("GAME OVER");
        } else if (vidaJugador2 <= 0) {
            resultadoTextView.setText("Jugador 1 Gana!");
            vidaJugador2TextView.setText("GAME OVER");
        }
        if (jugador == 1) {
            this.jugador = 2;
        } else {
            this.jugador = 1;
        }
    }

    private void animarDaño(TextView textView) {
        textView.animate()
                .translationX(10f)
                .setDuration(50)
                .withEndAction(() -> textView.animate()
                        .translationX(-10f)
                        .setDuration(50)
                        .withEndAction(() -> textView.animate()
                                .translationX(0f)
                                .setDuration(50)
                        )
                )
                .start();
    }

    private void cambiarColorTemporal(TextView textView) {
        int colorOriginal = textView.getCurrentTextColor();

        textView.setTextColor(Color.RED);

        new Handler().postDelayed(() -> {
            textView.setTextColor(colorOriginal);
        }, 250);
    }


}
