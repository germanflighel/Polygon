package com.example.a41914608.polygon_posta;

import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.base.Action;
import org.cocos2d.actions.interval.Animate;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.RotateBy;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.ScaleTo;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Animation;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by German Flighelman && Ivan PussyDestroyer on 20/9/2016.
 */
public class clsPloygon {

    boolean MantieneTouchApretado=false;
    int Puntuacion =0;
    CCGLSurfaceView _vistaJuego;
    CCSize Pantalla;
    Sprite Titulo;
    Sprite miPoligono;
    float DireccionDeRotacion;
    Timer Reloj;
    Random miRandom = new Random();
    ArrayList BolasDeFuego = new ArrayList();
    int CantidadDeBolasDeFuego=0;


    public clsPloygon(CCGLSurfaceView vistaJuego) {
        Log.d("Constructor", "constructor del juego");
        _vistaJuego = vistaJuego;



    }

    public void comenzarJuego(){
        Log.d("Acciones", "Comenzo el juego");
        Director.sharedDirector().attachInView(_vistaJuego);


        Pantalla= Director.sharedDirector().displaySize();
        Log.d("setup", "ancho pantalla:"+ Pantalla.width+"alto pantalla:"+ Pantalla.height);


        Log.d("Acciones", "Voy a Runnear la Escena");
        Director.sharedDirector().runWithScene(EscenaDeInicio());
        Log.d("Acciones", "Runneó la Escena");





    }

    private Scene EscenaDeInicio(){
        Log.d("Escena", "Comenzando Puesta de Fondo");

        Log.d("Escena", "Declaro e instancio la escena");
        Scene miEscena;
        miEscena = Scene.node();

        Log.d("Escena", "Declaro e instancio la capa de Fondo");
        CapaTitulo MiCapaTitulo = new CapaTitulo();


        Log.d("Escena", "Agrego las capas y las ordeno");
        miEscena.addChild(MiCapaTitulo, -10);


        Log.d("Escena", "Arme la Escena y la voy a devolver");
        return miEscena;




    }

    private Scene EscenaDeJuego(){
        Log.d("Escena", "Comenzando Escena de Juego");

        Log.d("Escena", "Declaro e instancio la escena");
        Scene miEscena;
        miEscena = Scene.node();

        Log.d("Escena", "Declaro e instancio la capa de Juego");
        CapaJuego MiCapaJuego = new CapaJuego();
        CapaDelJugador MiCapaJugador = new CapaDelJugador();
        CapaDelEnemigo MiCapaEnemigo = new CapaDelEnemigo();

        Log.d("Escena", "Agrego las capas y las ordeno");
        miEscena.addChild(MiCapaJuego, 10);
        miEscena.addChild(MiCapaJugador,20);
        miEscena.addChild(MiCapaEnemigo,30);


        Log.d("Escena", "Arme la Escena y la voy a devolver");
        return miEscena;
    }

    class CapaJuego extends Layer {

        public CapaJuego() {
            Log.d("Capa Juego", "Comienza Constructor de capa de juego");
            PonerFondo();
        }

        public void PonerFondo(){

            Sprite miFondo = Sprite.sprite("fondodejuego.png");
            setPosition(Pantalla.width/2, Pantalla.height/2);
            miFondo.runAction(ScaleTo.action(1f,Pantalla.width,Pantalla.height));
            super.addChild(miFondo);
            Log.d("PonerFondo", "Agrego el fondo");

        }
    }


    class CapaTitulo extends Layer{

        public CapaTitulo(){
            Log.d("Capa titulo", "comienza constructor de capa titulo");
            PonerFondo();
            setIsTouchEnabled(true);
        }

        public void PonerFondo(){
            Log.d("Capa titulo", "comienza poner imagen en capa titulo");
            Titulo= Sprite.sprite("titulo.png");
            Log.d("Capa titulo", "Instancie sprite");

            Titulo.setPosition(Pantalla.width/2, Pantalla.height/2);
            Log.d("Capa titulo", "Setee Posicion");

            //Titulo.runAction(ScaleBy.action(0.01f,3.0f,2.3f));

            super.addChild(Titulo,10);
            Log.d("Capa fondo", "agrega sprite fondo");
        }



        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            Log.d("Touch Termina", "Termina en X: "+ event.getX() + " Y: "+ event.getY() );

            Log.d("Touch Termina","Redirecciono a EscenaDeJuego()");
            Director.sharedDirector().runWithScene(EscenaDeJuego());

            return true;
        }
    }

    class CapaDelEnemigo extends Layer{

        Animation AnimacionBola = new Animation
                ("Bola",0.2f, "bola1.png", "bola2.png", "bola3.png", "bola4.png", "bola5.png", "bola6.png");


        public CapaDelEnemigo(){
            Log.d("Capa del Enemigo", "comienza constructor de capa del enemigo");
            PonerEnemigo();
        }

        private void PonerEnemigo() {
            Sprite miEnemigo;
            miEnemigo = Sprite.sprite("enemigo.png");
            miEnemigo.setPosition(Pantalla.width/2, Pantalla.height/2);
            miEnemigo.runAction(ScaleTo.action(0.01f,0.15f, 0.15f));
            Disparar();
            super.addChild(miEnemigo,30);
        }

        private void Disparar(){
            TimerTask miTarea = new TimerTask() {
                @Override
                public void run() {
                    Sprite miBola = Sprite.sprite("bola1.png");
                    Log.d("Dispara","Entra al timer");
                    miBola.runAction(Animate.action(AnimacionBola));
                    miBola.setPosition(Pantalla.width/2, Pantalla.height/2);
                    miBola.runAction(MoveBy.action(0.5f,miRandom.nextFloat(),miRandom.nextFloat()));
                    addChild(miBola,40);
                    CantidadDeBolasDeFuego++;
                    BolasDeFuego.add(miBola);
                    float Pi =((float) Math.PI);
                    Log.d("Log", "Pi: "+ Pi);
                }
            };
            Reloj= new Timer();
            Reloj.schedule(miTarea,0,1000);


        }


    }

    class CapaDelJugador extends Layer{
        public CapaDelJugador(){
            Log.d("Capa de Jugador", "comienza constructor de capa de jugador");
            setIsTouchEnabled(true);
            PonerPoligono();

        }

        private void PonerPoligono(){

            int ladosDePoligono = CalcularLados();
            switch (ladosDePoligono){
                case 3:
                    Log.d("Poner Poligono", "Voy a Instanciar el Triangulo");
                    miPoligono = Sprite.sprite("triangulo.png");
                    miPoligono.runAction(ScaleTo.action(0.01f,2f,2f));
                    miPoligono.setPosition(Pantalla.width/2, Pantalla.height/2);
                    Log.d("Poner Poligono", "Setee Posicion");
                    super.addChild(miPoligono, 20);
                    break;
            }


        }

        private void Rotar(){

            TimerTask MantieneTouch = new TimerTask() {
                @Override
                public void run() {
                    Log.d("Rota","Entra al timer");
                    if (MantieneTouchApretado) {
                        Log.d("Girando","El touch esta apretado, ahora va a rotar, X vale : "+ DireccionDeRotacion);

                        miPoligono.runAction(RotateBy.action(0.01f, DireccionDeRotacion*4));
                    }
                }
            };
            Reloj= new Timer();
            Reloj.schedule(MantieneTouch,0,100);
        }

        private int CalcularLados(){

            Log.d("Calcular Lados", "Voy a Calcular Lados");
            int lados=-1;
            if (Puntuacion == 0){
                return 3;
            }
            if (Puntuacion>0 && Puntuacion<10){

            }


            return lados;

        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event) {
            Log.d("MueveJugador", "Toco Pantalla");
            Log.d("MueveJugador", "X: "+ event.getX()+ " Y: "+ event.getY());

            MantieneTouchApretado=true;
            if (event.getX()<Pantalla.width/2 && event.getX()>0){
                DireccionDeRotacion = 1f;
            }
            else{
                DireccionDeRotacion = -1f;
            }
            Log.d("Began", "Llamo a Rotar() con "+DireccionDeRotacion);
            Rotar();
            return true;
        }


        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            Log.d("touch","suelta el touch");
            MantieneTouchApretado=false;
            Reloj.cancel();
            Log.d("Reloj", "Cancele el Timer para ahorrar recursos");
            return true;
        }
    }


}
