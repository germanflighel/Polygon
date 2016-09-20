package com.example.a41914608.polygon_posta;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {

    CCGLSurfaceView miVistaPrincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        miVistaPrincipal = new CCGLSurfaceView(this);


        Log.d("Help", "1.0");
        setContentView(miVistaPrincipal);
        Log.d("Help", "2.0");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Juego","Voy a instanciar miVistaPrincipal");

        clsPloygon juego;
        juego = new clsPloygon(miVistaPrincipal);
        Log.d("Juego","Instancie miVistaPrincipal");

        Log.d("Juego","Voy comenzarJuego()");

        juego.comenzarJuego();

        Log.d("Juego","Comence Juego");

    }


}

