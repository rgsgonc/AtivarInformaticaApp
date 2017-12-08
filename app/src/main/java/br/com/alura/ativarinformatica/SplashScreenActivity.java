package br.com.alura.ativarinformatica;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarListaCliente();

            }
        }, 3000);
    }

    private void mostrarListaCliente() {
        Intent intent = new Intent(SplashScreenActivity.this, ListaClientesActivity.class);
        startActivity(intent);
        finish();
    }

}
