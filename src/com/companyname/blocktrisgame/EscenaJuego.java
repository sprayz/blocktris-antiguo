/**
 * 
 */
package com.companyname.blocktrisgame;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.IBackground;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.IModifier;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.companyname.blocktrisgame.assets.Globales;
import com.companyname.blocktrisgame.logica.Bloque;
import com.companyname.blocktrisgame.logica.Pieza;
import com.companyname.blocktrisgame.logica.PiezaImp;
import com.companyname.blocktrisgame.logica.PiezaImp.PIEZAS;
import com.companyname.blocktrisgame.logica.Tabla;

/**
 * @author executor
 * 
 */
public class EscenaJuego extends Scene implements ITimerCallback,
		IScrollDetectorListener, IOnSceneTouchListener, IClickDetectorListener {
	private int filas = 24;
	private int columnas = 10;
	private final int PUNTOS_POR_TIEMPO = 2;
	private final int MULTIPLICADOR_DESPLAZAMINETO_RAPIDO = 4;
	private final int PUNTOS_POR_FILA = 100;
	private  float tiempoInicial=0.6f;
	private float tiempoMax = 0.8f;
	private float multiplicadorTiempo = 0.91f;
	private float multiplicadorTiempoLinea=1.6f;
	private int filaLimite=20;
	private boolean piezaEncajada;
	/**
	 * @param filas
	 * @param columnas
	 * @param tiempoInicial
	 * @param tiempoMax
	 */

	

	
	

	Tabla tabla;
	TimerHandler timer;
	ScrollDetector scrollDetector;
	ClickDetector clickDetector;
	Text tPuntuacion;
	
	private float limiteDesplazamientoHorizontal = 0.0f;
	private  float limiteDesplazamientoVertical = 0.0f;

	private long puntos=0;
	private float movidoX = 0.0f;
	private float movidoY = 0.0f;

	/**
	 * @return 
	 * @throws InterruptedException
	 * 
	 */
	
	
	public void reset(){
		
		
		this.unregisterUpdateHandler(timer);
		tabla.reset();
		
		
		
		
	}
	
	public EscenaJuego(int filas, int columnas, int filaLimite,float tiempoInicial,
			float tiempoMax) {
		super();
		this.filas = filas;
		this.columnas = columnas;
		this.tiempoInicial = tiempoInicial;
		this.tiempoMax = tiempoMax;
		
		limiteDesplazamientoHorizontal = Globales.anchoCamara / (columnas *2); // ancho
																				// entre
																				// columnas
																				// -
																				// 2
		limiteDesplazamientoVertical = Globales.altoCamara / 10; // un cuarto de
																// la pantalla
		// detector de desplazamiento
		// procesa eventos táctiles y genera eventos de desplazamiento
		// ahorra un montón de cálculos para discriminar los toques de los
		// arrastres
		// o los puntos de inicio y fin / distancia arrastrada
		// NOTA: Buscar entre las utilidades más cosas cómo esta(quien tuviera
		// un miserable javadoc...)
		
		tPuntuacion= new Text(Globales.anchoCamara/2,Globales.Recursos.fPuntuacion.getLineHeight()/2,Globales.Recursos.fPuntuacion, //pos X e Y
				Globales.Video.gsPantallaJuego.getResources().getText(R.string.textoJuegoPuntuacion),//texto inicial
				Globales.Video.gsPantallaJuego.getResources().getText(R.string.textoJuegoPuntuacion).length()+15,// longitud máxima del texto
																											  // si omito el parámetro lo toma del anterior
																											  
				Globales.Video.gsPantallaJuego.getVertexBufferObjectManager());
		tPuntuacion.setColor(0, 0, 0);
		tPuntuacion.setScale(1.6f);
		// nuestra escena es su propio listener
		
		tPuntuacion.setHorizontalAlign(HorizontalAlign.LEFT);
		tPuntuacion.setWidth(Globales.anchoCamara/2.0f);
		setBackground(new Background(1.f, 1.0f, 1.0f));

		final FPSCounter fpsCounter = new FPSCounter();
		registerUpdateHandler(fpsCounter);

		final float centerX = Globales.altoCamara / 2;
		final float centerY = Globales.anchoCamara / 2;

		this.setColor(0.5f, 0.1f, 0.5f);

		this.setBackground(new Background(0.5f, 0.5f, 0.5f, 0.5f));
		
		tabla = new Tabla(Globales.anchoCamara * 2, Globales.altoCamara * 2,// posicion
				Globales.anchoCamara / 1.15f, Globales.anchoCamara / 1.15f
						* ((float) filas / columnas),// tamaño y ratio para
														// mantener proporciones

				columnas, filas,filaLimite,// divisiones
				Globales.Video.gsPantallaJuego.getVertexBufferObjectManager());// parametro

		// reposicionamos la tabla
		// NOTA :esto es cutre ,cutre, tiene que haber una manear mejor que
		// probar coordenadas a pelo

		tabla.setX(Globales.anchoCamara / 2.0f); // el
		tabla.setY(tabla.getHeight() / 1.8f); // rectangulo

		// ponemos la tabla en la escena
		attachChild(tPuntuacion);
		attachChild(tabla);
		
		// empezamos la partida
		reset();

	

	}

	

	public void empezarJuego() {
		scrollDetector = new SurfaceScrollDetector(this);
		clickDetector = new ClickDetector(this);
		clickDetector.setEnabled(true);
		scrollDetector.setEnabled(true);
		this.setOnSceneTouchListener(this);
	

		for (int fil = filas - 1; fil > Globales.Opciones.lineaTope-1; fil--) {
			for (int col = 0; col < columnas; col++) {

				new Bloque(tabla, col, fil, Bloque.COLOR.ROJO);
			}

		}
		
		registerUpdateHandler(timer = new TimerHandler(tiempoInicial, this));
		piezaEncajada=true;
	}

	@Override
	public synchronized void onTimePassed(TimerHandler pTimerHandler) {
		int lineasQuitadas = 0;
		

		
		
		
		tPuntuacion.setText(Globales.Video.gsPantallaJuego.getResources().getText(R.string.textoJuegoPuntuacion) + ":"+ puntos);
		
		
		if (piezaEncajada) {
			//por cada pieza colocada modificamos le tiempo para que vaya más rápido
			lineasQuitadas=tabla.quitarFilasCompletadas(filaLimite);
			pTimerHandler.setTimerSeconds(pTimerHandler.getTimerSeconds()*multiplicadorTiempo);
			

			tabla.ponerPieza();
			piezaEncajada=false;
			

		}
		
		if (tabla.getPiezaActual().puedeMover(tabla.getPiezaActual().getX(), tabla.getPiezaActual().getY() - 1)) {
			tabla.getPiezaActual().mover(tabla.getPiezaActual().getX(), tabla.getPiezaActual().getY() - 1);
		} else {
			if(tabla.getPiezaActual().getY() >= Globales.Opciones.lineaTope){
				
				//si el juego se ha acabado  vamos a la pantalla de puntuaciones pasando la puntuacion como parametro
				unregisterUpdateHandler(pTimerHandler);
				
				Globales.Video.gsPantallaJuego.runOnUiThread(new Runnable() {
				    
				    public void run() {
				    	Globales.Video.gsPantallaJuego.onJuegoAcabado(puntos);
				    	
				    	
				    	
				    }
				});
				return;
			}
			piezaEncajada=true;
			
			
			

		}
		
		
		
		
		//sumamos puntos por cada tick dependiendo de lo rapido que vayamos
		puntos += PUNTOS_POR_TIEMPO /pTimerHandler.getTimerSeconds();
		//por cada linea le sumamos puntos dependiendo de la velocidad
		
		
		//si hemos quitado lineas aplicamos los consiguientes bonos de puntos y reducción de velocidad
		if(lineasQuitadas >0){
			float tiempo = pTimerHandler.getTimerSeconds()*  multiplicadorTiempoLinea * lineasQuitadas; 
			if(tiempo>tiempoMax)
				tiempo=tiempoMax;
			
			pTimerHandler.setTimerSeconds(tiempo);
			puntos=(long) (puntos + lineasQuitadas * PUNTOS_POR_FILA/pTimerHandler.getTimerSeconds());
			
		}
		
	
			pTimerHandler.reset();

	}

	
	
	
	@Override
	public void onScrollStarted(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {

	}

	@Override
	public void onScroll(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {

		// Log.d("TOUCH","MovidoX: " + movidoX);

		// si hay alguna pieza en el tablero bajando
		if (!piezaEncajada) {

			// acumulamos lo que llevamos movido con lo que se acaba de mover
			movidoX += pDistanceX;
			movidoY += pDistanceY;

			// si hemos cruzado el movimiento mínimo para causar un
			// desplazamiento horizontal
			if (Math.abs(movidoX) >= limiteDesplazamientoHorizontal) {

				// nos movemos, atendiendo a que sea posible moverse, claro

				// DAT TERNARY OPERATOR
				// el operador ternario decide si se mueve a la derecha o
				// izquerda dependiendo de el signo
				// del acumulador de movimiento (movidoX)
				// es feo pero está chulo
				// NOTA: cambiarlo por un condicional antes de entregar

				if (tabla.getPiezaActual().puedeMover(tabla.getPiezaActual().getX()
						+ (movidoX > 0 ? 1 : -1), tabla.getPiezaActual().getY())) {

					tabla.getPiezaActual().mover(tabla.getPiezaActual().getX()
							+ (movidoX > 0 ? 1 : -1), tabla.getPiezaActual().getY());
				}

				movidoX = 0.0f;

			}

			if (Math.abs(movidoY) >= limiteDesplazamientoVertical) {

				// NOTA: cambiarlo por un condicional antes de entregar

				// mientras podamos movernos ,bajamos
				int yFinal = tabla.getPiezaActual().getY() - 1;
				while (tabla.getPiezaActual().puedeMover(tabla.getPiezaActual().getX(), yFinal)) {

					yFinal--;
				}
				yFinal++;
				
				puntos += (PUNTOS_POR_TIEMPO /  timer.getTimerSeconds() * ((tabla.getPiezaActual().getY()-yFinal)*MULTIPLICADOR_DESPLAZAMINETO_RAPIDO)) ;
				tabla.getPiezaActual().mover(tabla.getPiezaActual().getX(), yFinal);
				
				movidoY = 0.0f;
				piezaEncajada=true;

			}

		}
		return;

	}

	@Override
	public void onScrollFinished(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {

		// cada vez que se levante el dedo reseteamos el acumulador
		// evita que varios pequeños toques nos hgan bajar las piezas ...GRRRR
		movidoX = 0.0f;
		movidoY = 0.0f;

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		

		if (pSceneTouchEvent.isActionUp()) {
		//	Log.d("TOUCH", "TOCADO" + pSceneTouchEvent.getX());

		}

		// le pasamos al detector de desplazamiento todos los eventos tactiles
		// para que los procese
		clickDetector.onSceneTouchEvent(pScene, pSceneTouchEvent);
		scrollDetector.onSceneTouchEvent(pScene, pSceneTouchEvent);
		return true;
	}

	@Override
	public void onClick(ClickDetector pClickDetector, int pPointerID,
			float pSceneX, float pSceneY) {
		if (!piezaEncajada) {

			tabla.getPiezaActual().rotar();

		}

	}
}
