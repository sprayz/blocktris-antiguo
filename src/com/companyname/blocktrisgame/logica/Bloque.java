/**
 * 
 */
package com.companyname.blocktrisgame.logica;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Letter;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ModifierList;

import com.companyname.blocktrisgame.assets.Globales;

/**
 * @author executor
 * 
 */
public class Bloque implements IEntityModifierListener{
	LinkedList<IEntityModifier> colaAnim;
	//direcciones a mover
	public static enum DIRECCION {
		ARRIBA, ABAJO, DERECHA, IZQUIERDA
		
		
	};
	//colores
	public static enum COLOR {
		AZUL, VERDE, ROJO;
		

		 private static final List<COLOR> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
		  private static final int tamaño = VALUES.size();
		  private static final Random RANDOM = new Random();

		  public static COLOR colorAleatorio()  {
		    return VALUES.get(RANDOM.nextInt(tamaño));
		  }
		
	};

	private Tabla tabla = null;
	private Sprite sprite = null;
	private int x;
	private int y;
	COLOR color = null;
	
	
	
	
	public int getX() {
		return x;
	}




	public int getY() {
		return y;
	}




	public Bloque(Tabla tabla, int x, int y,COLOR color) {
		ITextureRegion trColor = null;
		colaAnim = new LinkedList<IEntityModifier>();
		switch (color) {
		case VERDE:
			trColor = Globales.Recursos.trBloqueVerde;
			break;

		case AZUL:
			trColor = Globales.Recursos.trBloqueAzul;
			break;

		case ROJO:
			trColor = Globales.Recursos.trBloqueRojo;
			break;

		}
		
		this.x=x;
		this.y=y;
		this.color = color;
		this.tabla = tabla;
		this.sprite = new Sprite(0, 
								0, trColor,
				Globales.Video.gsPantallaJuego.getVertexBufferObjectManager());
		sprite.setHeight(tabla.getCeldaDimY());
		sprite.setWidth(tabla.getCeldaDimX());
		//sprite.setPosition(getXEfectivo(x),getYEfectivo(y) );
		tabla.attachChild(sprite);
		mover(x,y);
		tabla.tablero[x][y]= this;
		
		

	}
	
	
	
	
	public void destruir(){
		
		sprite.detachSelf();
		sprite.dispose();
		tabla.tablero[x][y]=null;
		
	}
	public void moverAnim(int x , int y,float tiempo){
			
		
		tabla.tablero[this.x][this.y]=null;
		
		
			sprite.registerEntityModifier(new MoveModifier(tiempo,sprite.getX(),sprite.getY(),getXEfectivo(x),getYEfectivo(y),this));

			this.x =x;
			this.y=y;
			tabla.tablero[this.x][this.y]=this;
				
				
			
			//quitamos le sprite de la posición anterior
			
		
		return;
	}
		
	
	
	//interfaz pública para desplazar un bloque comprobando  las reglas
	public  void mover (int x, int y){
		
		
				tabla.tablero[this.x][this.y]=null;
				this.x =x;
				this.y=y;
				tabla.tablero[this.x][this.y]=this;
				sprite.setPosition(getXEfectivo(x), 
						getYEfectivo(y));
			
				
				
				
				tabla.tablero[this.x][this.y]=this;
		
			return ;
	}
	
	
	/*
	 * 
	 * Paraposicionarse en las celdas de la tabla
	 */
	private float getXEfectivo(int x){
		return tabla.getCeldaDimX() * x + (sprite.getWidth()/2) ;
	}
	private float getYEfectivo(int y){

		return tabla.getCeldaDimY() * y + (sprite.getHeight()/2);
	}
	
	public boolean puedeMover(int x, int y){
		
		//si la casilla adyacente esta vacia  y no nos salimos de la tabla
		if( (x < tabla.dimX && y < tabla.dimY )&&  tabla.tablero[x][y] == null ){
			return true;
					}
		else{//en caso contrario no podemos movernos
			return false;
		}
				
	}




	@Override
	public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
		
		
		
	}




	@Override
	public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
		
		
		
		
		
	}
	
	
}
