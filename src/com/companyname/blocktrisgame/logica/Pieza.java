package com.companyname.blocktrisgame.logica;

public interface Pieza {
	
	
	public boolean moverAnim(int x,int y,float tiempo);
	public boolean mover(int x, int y);
	
	public int getX();
	
	public int getY();
	
	public void destruir();
	
	public boolean puedeMover(int x, int y);
	
	public boolean rotar();
	public boolean rotarAnim(float tiempo);

}
