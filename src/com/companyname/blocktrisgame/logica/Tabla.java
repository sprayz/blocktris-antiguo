package com.companyname.blocktrisgame.logica;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import com.companyname.blocktrisgame.assets.*;
import com.companyname.blocktrisgame.logica.PiezaImp.PIEZAS;
public class Tabla extends Rectangle {
	 
    @Override
	protected void onUpdateVertices() {
		//si cambiamos de dimensión tenmos que escalar proporcionalmente los bloques que contiene la tabla...
    	
    	this.celdaDimX=this.getWidth() / dimX;
    	this.celdaDimY=this.getHeight() / dimY;
		super.onUpdateVertices();
	}
    
    
    private int filaLimite=20;
	ArrayList<Pieza> piezas;
	Pieza piezaActual = null;
	private boolean pintarBordes;
    protected int dimX = 0;
    protected int dimY = 0;
    protected float celdaDimX=0.0f;
   
    public Pieza getPiezaActual(){
    	return piezaActual;
    }
public float getCeldaDimX() {
		return celdaDimX;
	}

	public float getCeldaDimY() {
		return celdaDimY;
	}

	protected float celdaDimY=0.0f;
    public int getDimX() {
		return dimX;
	}

	public int getDimY() {
		return dimY;
	}

	public Bloque tablero[][];
    

	
	public void  reset(){
		for(int y =0 ;y <dimY; y++){
			for(int x =0 ;x <dimX; x++){
				if(tablero[x][y]!=null)
					tablero[x][y].destruir();
				
				
				
			}
			
			
		}
		
		
	}
    public Tabla(float pX, float pY, float ancho, float alto, int dimX, int dimY,int filaLimite, final VertexBufferObjectManager pVertexBufferObjectManager) {
            super(pX, pY, ancho, alto, pVertexBufferObjectManager);
            this.filaLimite= filaLimite;
            this.dimX = dimX;
            this.dimY = dimY;
            tablero = new Bloque[dimX][dimY];
            this.setColor(128, 255, 128,1.0f) ;
            piezas = new ArrayList<Pieza>();
            this.celdaDimX=this.getWidth() / dimX;
        	this.celdaDimY=this.getHeight() / dimY;
    }

  
    
    
    public void ponerPieza(){

		piezaActual = new PiezaImp(this, Bloque.COLOR.colorAleatorio(),
				PIEZAS.piezaAleatoria(), dimX / 2, filaLimite);
		piezas.add(piezaActual);

    	
    }
     
    
    public int quitarFilasCompletadas(int lineaTope) {
		int filasQuitadas = 0;
		boolean filaEntera ;

		// iteramos sobre todas las filas
		for (int fil = 0; fil < lineaTope; fil++) {
			filaEntera = true;

			for (int col = 0; col < dimX; col++) {
				// si encontramos al menos un hueco dejamos de iterar y pasamos
				// a la siguiente fila
				if (tablero[col][fil] == null) {
					filaEntera = false;
					break;
				}

			}
			// si no se encontró ningún hueco la fla podemos quitarla
			
			if (filaEntera) {
				filasQuitadas++;
				for (int col = 0; col < dimX; col++) {
					// nos cargamos todos los bloques
				tablero[col][fil].destruir();
				}
				// y hacemos bajar todos ls bloques que esten por encima
				for (int fil2 = fil; fil2 < lineaTope-1; fil2++) {

					for (int col = 0; col < dimX; col++) {
						if(tablero[col][fil2] != null)
							tablero[col][fil2].mover(tablero[col][fil2].getX(), tablero[col][fil2].getY()-1);
					}

				}
			fil --;
			}
		}

		return filasQuitadas;
	}
    
    
    public boolean coordsValidas(int x, int y){
    	if( x < dimX && y < dimY  &&   x >=0 && y >=0 ){
    		return true;
    	}
			
    	
    	return false;
    	
    	
    	
    }
    public void pintarBordes(boolean pintarBordes) {
          this.pintarBordes= pintarBordes; 
    }
   
  
   
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  
   
}