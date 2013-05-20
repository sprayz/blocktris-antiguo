/**
 * 
 */
package com.companyname.blocktrisgame.logica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.companyname.blocktrisgame.assets.Globales;
import com.companyname.blocktrisgame.logica.Bloque.COLOR;
import com.companyname.blocktrisgame.logica.Bloque.DIRECCION;

/**
 * @author executor
 * 
 */
public class PiezaImp implements Pieza {

	public static enum PIEZAS {
		PIEZA_T(new boolean[][][] {
				{ { true, false, false, false }, { true, true, false, false },
						{ true, false, false, false },
						{ false, false, false, false } },

				{ { true, true, true, false }, { false, true, false, false },
						{ false, false, false, false },
						{ false, false, false, false } },

				{ { false, true, false, false }, { true, true, false, false },
						{ false, true, false, false },
						{ false, false, false, false } },

				{ { false, true, false, false }, { true, true, true, false },
						{ false, false, false, false },
						{ false, false, false, false } }

		}),

		PIEZA_CUBO(new boolean[][][] {
				{ { true, true, false, false }, { true, true, false, false },
						{ false, false, false, false },
						{ false, false, false, false } },

				{ { true, true, false, false }, { true, true, false, false },
						{ false, false, false, false },
						{ false, false, false, false } }

		}), PIEZA_PALO(new boolean[][][] {
				{  
						{ false, false, false, false },
						{ true, true, true, true },
						{ false, false, false, false },
						{ false, false, false, false } },

				{ 		{ false, true, false, false }, 
						{ false, true, false, false },
						{ false, true, false, false },
						{ false, true, false, false } }

		}), PIEZA_ELE1(new boolean[][][] {
				{ { true, false, false, false }, { true, true, true, false },
						{ false, false, false, false },
						{ false, false, false, false } },

				{ { true, true, false, false }, { true, false, false, false },
						{ true, false, false, false },
						{ false, false, false, false } },

				{ { true, true, true, false }, { false, false, true, false },
						{ false, false, false, false },
						{ false, false, false, false } },

				{ { false, true, false, false }, { false, true, false, false },
						{ true, true, false, false },
						{ false, false, false, false } },

		}), PIEZA_ELE2(new boolean[][][] {
				{ { true, true, true, false }, { true, false, false, false },
						{ false, false, false, false },
						{ false, false, false, false } },

				{ { true, true, false, false }, { false, true, false, false },
						{ false, true, false, false },
						{ false, false, false, false } },

				{ { false, false, true, false }, { true, true, true, false },
						{ false, false, false, false },
						{ false, false, false, false } },

				{ { true, false, false, false }, { true, false, false, false },
						{ true, true, false, false },
						{ false, false, false, false } },

		}), PIEZA_LLAVE1(new boolean[][][] {
				{ { true, true, false, false }, { false, true, true, false },
						{ false, false, false, false },
						{ false, false, false, false } },

				{ { false, true, false, false }, { true, true, false, false },
						{ true, false, false, false },
						{ false, false, false, false } }

		}), PIEZA_LLAVE2(new boolean[][][] {
				{ { false, true, true, false }, { true, true, false, false },
						{ false, false, false, false },
						{ false, false, false, false } },

				{ { true, false, false, false }, { true, true, false, false },
						{ false, true, false, false },
						{ false, false, false, false } }

		});

		private static final List<PIEZAS> VALUES = Collections
				.unmodifiableList(Arrays.asList(values()));
		private static final int tamaño = VALUES.size();
		private static final Random RANDOM = new Random();

		public static PIEZAS piezaAleatoria() {
			return VALUES.get(RANDOM.nextInt(tamaño));
		}

		PIEZAS(boolean estructura[][][]) {
			this.estructura = estructura;

		}

		public boolean[][][] getEstructura() {
			return estructura;
		}

		private final boolean estructura[][][];

	}

	// Hast aquí enum estática anidada

	ArrayList<Bloque> bloques;
	Tabla tabla;
	COLOR color;
	int posx;
	int posy;
	PIEZAS estructura;
	int rotacion = 0;

	public PiezaImp(Tabla tabla, COLOR color, PIEZAS tipo, int posx, int posy) {

		this.color = color;
		this.tabla = tabla;
		bloques = new ArrayList<Bloque>();
		this.estructura = tipo;
		this.posx = posx;
		this.posy = posy;

		// pintamos los bloques siguiendo el patron y los almacenamos en el
		// array

		// no es necesario comprobar nada puesto que en principio las piezas al
		// crearse salen por arriba
		// en caso de que la tabla esté llena saltará negativo en el primer
		// movimiento

		// NOTA:esto es cutrísimo hay que cambiar los constructores para que no
		// pinten nada y distribuir la funcionalidad
		// en otros métodos , para pintar y "asociar" los bloques y piezas a la
		// tabla y entre sí

		for (int y = 0, ultimoBloque = 0; y < estructura.getEstructura()[0].length; y++) {
			for (int x = 0; x < estructura.getEstructura()[rotacion][0].length; x++) {
				if (estructura.getEstructura()[rotacion][x][y] == true) {
					bloques.add(new Bloque(tabla, getXEfectivo(x),
							getYEfectivo(y), color));

				}

			}
		}
	}

	// mover el bloque con animación
	//NOTA: cómo el timer es al fin y al cabo un hilo diferente  esto va con MUCHO cuidado.
	//		El yo del pasado dice:
	//		"Ya me he pegado con  tropecientas condiciones de carrera por el maldito
	//		hilo.NO TOCAR HASTA QUE ESTES SEGURO DE LO QUE HACES.
	//		INCLUSO SI PARECE QUE FUNCIONA ,ESTÁ PENDIENDO DE UN HILO *Badum-Tsss*".
	@Override
	public boolean moverAnim(int x, int y, float tiempo) {
		

		for (Bloque b : bloques) {
			b.moverAnim((b.getX() - posx) + x, (b.getY() - posy) + y, tiempo);
		}

		posx = x;
		posy = y;

		return false;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return posx;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return posy;
	}

	@Override
	public void destruir() {

	}

	private int getXEfectivo(int x) {

		return posx + x;
	}

	private int getYEfectivo(int y) {

		return posy + y;
	}

	@Override
	public boolean puedeMover(int x, int y) {
		int bloqueX = 0;
		int bloqueY = 0;
		for (Bloque b : bloques) { // por cada bloque

			// posición relativa de los bloques
			// la coordenada a la que los quermos mover depende de su lugar en
			// la pieza
			bloqueX = (b.getX() - posx) + x;
			bloqueY = (b.getY() - posy) + y;

			// si las coordenadas son invalidas no podemos movernos
			if (!tabla.coordsValidas(bloqueX, bloqueY))
				return false;

			// si el bloque todavía dice que no puede moverse
			if (!b.puedeMover(bloqueX, bloqueY)
					&& bloques.indexOf(tabla.tablero[bloqueX][bloqueY]) == -1) {
				// comprobamos que el bloque que se lo impide no es uno de los
				// nuestros

				// en caso de que no sea ,hemos topado con bloques
				// fijos(retornamos false).

				return false;
			}
		}

		return true;
	}

	// mover sin animacion
	@Override
	public boolean mover(int x, int y) {

		int ultimoBloque = 0;

		for (Bloque b : bloques) {
			b.mover((b.getX() - posx) + x, (b.getY() - posy) + y);
		}

		posx = x;
		posy = y;

		return false;
	}

	/*
	 * public boolean puedeRotar(){ int rotacionSiguiente = rotacion++;
	 * 
	 * int xeff; int yeff; for (int y = 0,ultimoBloque=0; y <
	 * estructura.getEstructura()[rotacionSiguiente].length; y++) { for (int x =
	 * 0; x < estructura.getEstructura()[rotacionSiguiente][0].length ; x++) {
	 * if (estructura.getEstructura()[rotacionSiguiente][x][y] == true) {
	 * 
	 * xeff=getXEfectivo(x); yeff=getYEfectivo(y);
	 * 
	 * 
	 * if (!tabla.coordsValidas(xeff,yeff )) return false;
	 * 
	 * if (!bloques.get(ultimoBloque).puedeMover(xeff,yeff) &&
	 * bloques.indexOf(tabla.tablero[xeff][yeff]) == -1) return false;
	 * 
	 * 
	 * bloques.get(ultimoBloque).mover(xeff,yeff);
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * return true; }
	 */
	@Override
	public boolean rotar() {

		if (rotacion == estructura.getEstructura().length - 1) {
			// si hemos llegado al final de las posiciones que soporta esta
			// pieza
			// empezamos el ciclo de nuevo
			rotacion = 0;
		} else {
			// en caso contrario cambiamos la estructura a la siguiente
			rotacion++;

		}
		// en cualquiera de los casos tenemos que pintar la estructura nueva
		// teniendo en cuenta que puede, o no, ser posible rotar.
		// reutilizamos el metodo mover con cada bloque para evitar
		// no es elegante pero el dia 5 es MUYYYYYYY pronto :P
		// NOTA: rediseñar para proyecto final

		int xeff;
		int yeff;
		for (int y = 0, ultimoBloque = 0; y < estructura.getEstructura()[rotacion].length; y++) {
			for (int x = 0; x < estructura.getEstructura()[rotacion][0].length
					&& ultimoBloque < bloques.size(); x++) {
				if (estructura.getEstructura()[rotacion][x][y] == true) {

					xeff = getXEfectivo(x);
					yeff = getYEfectivo(y);
					if (!tabla.coordsValidas(xeff, yeff))
						return false;

					if (!bloques.get(ultimoBloque).puedeMover(xeff, yeff)
							&& bloques.indexOf(tabla.tablero[xeff][yeff]) == -1)
						return false;

					// bloques.get(ultimoBloque).mover(xeff,yeff);
					ultimoBloque++;
				}

			}
		}

		for (int y = 0, ultimoBloque = 0; y < estructura.getEstructura()[rotacion].length; y++) {
			for (int x = 0; x < estructura.getEstructura()[rotacion][0].length
					&& ultimoBloque < bloques.size(); x++) {
				xeff = getXEfectivo(x);
				yeff = getYEfectivo(y);

				if (estructura.getEstructura()[rotacion][x][y] == true) {
					bloques.get(ultimoBloque).mover(xeff, yeff);
					ultimoBloque++;
				}
			}

		}

		return true;
	}

	public boolean rotarAnim(float tiempo) {

		if (rotacion == estructura.getEstructura().length - 1) {

			rotacion = 0;
		} else {

			rotacion++;

		}

		int xeff;
		int yeff;
		for (int y = 0, ultimoBloque = 0; y < estructura.getEstructura()[rotacion].length; y++) {
			for (int x = 0; x < estructura.getEstructura()[rotacion][0].length
					&& ultimoBloque < bloques.size(); x++) {
				if (estructura.getEstructura()[rotacion][x][y] == true) {

					xeff = getXEfectivo(x);
					yeff = getYEfectivo(y);
					// ya cacheará le compilador los vlores de get-Efectivo(-)
					// en una temporal
					if (!tabla.coordsValidas(xeff, yeff))
						return false;

					if (!bloques.get(ultimoBloque).puedeMover(xeff, yeff)
							&& bloques.indexOf(tabla.tablero[xeff][yeff]) == -1)
						return false;

					bloques.get(ultimoBloque).moverAnim(xeff, yeff, tiempo);
					ultimoBloque++;
				}

			}
		}

		return true;
	}

}
