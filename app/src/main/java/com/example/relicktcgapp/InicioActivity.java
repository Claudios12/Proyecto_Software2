package com.example.relicktcgapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InicioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Button botonIniciar = findViewById(R.id.botonIniciar);

        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InicioActivity.this, DecidirActivity.class);
                startActivity(intent);
                finish(); // Opcional: cerrar esta pantalla para no volver atrás
            }
        });
    }
}
