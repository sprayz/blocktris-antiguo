package com.companyname.blocktrisgame.assets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import android.app.Activity;
import android.content.Context;

import com.companyname.blocktrisgame.ActividadJuego;
import com.companyname.blocktrisgame.ActividadMenu;
import com.companyname.blocktrisgame.EscenaJuego;

public final class Globales {

	// constantes
	public static final int anchoCamara = 720;
	public static final int altoCamara = 1280;
	public static final String ficheroOpciones = "opciones.ini";
	public static final String ficheroPuntuaciones = "puntuaciones.txt";

	public static final class Video {

		public static Camera camara;
		public static Engine motor;
		public static EscenaJuego escenaJuego;
		public static ActividadJuego gsPantallaJuego;
		public static Activity actividadMenu; 
	}
	
	public static final class Opciones {
		
		public static final int lineaTope = 20;
	}

	/*
	 * Recursos varios que hay que poder acceder desde todas las clases
	 */
	public static final class Recursos {
		
		public static BitmapTextureAtlas taBloques;
		public static TextureRegion trBloqueVerde;
		public static TextureRegion trBloqueAzul;
		public static TextureRegion trBloqueRojo;
		public static TextureRegion trBloquePiedra;
		public static Font          fPuntuacion;
		public static Texture tFuente;
		
		public static  TreeMap<Long, String> puntuaciones; 

		
		
		
		
		//primitivo
		//almacenamos la puntuaciones en lineas  guardando
		//para cada puntuacion su dueño
		//en el Map usamos el Long como clave para  que se auto ordenen
		//consecuencai de usar un map, las puntuaciones identicas se suplantan
		
		public static void cargarPuntuaciones(){
			BufferedReader br;
			FileInputStream fis;
			String linea;
			String[] par;
			puntuaciones= new TreeMap<Long,String>();
			File fi = new File(ficheroPuntuaciones);
			
			
			try {
				
				 fis = Video.actividadMenu.openFileInput(   ficheroPuntuaciones );
				
				 br = new BufferedReader(new InputStreamReader(fis));
				
				 //feo pero chulo (asiganción y comparación en la misma sentencia)
				 while ((linea= br.readLine()) != null) {
					
					 par= linea.split(",");
					 
					 puntuaciones.put(Long.parseLong(par[0]), par[1]);
					 
					 
					}
				   
				  
				


				br.close();
				fis.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
		
		public static void guardarPuntuaciones(){
			BufferedWriter bw;
			FileOutputStream fos;
			try {
				 fos = Video.actividadMenu.openFileOutput(ficheroPuntuaciones, Context.MODE_APPEND );
				
				 bw = new BufferedWriter(new OutputStreamWriter(fos));
				
				for (Entry<Long, String> par: puntuaciones.entrySet())
				{
				   bw.write(par.getKey() + ","+par.getValue());
				   bw.newLine();
				}


				bw.close();
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
		
		public static void cargarRecursos() {
			// la ruta de los sprites
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/sprites/");
			
			//La textura maestra que contiene los bloques
			//reservado un espacio para el bloque de peidra que todavía no esta implementado
			//128*4 = 512 (3 colores y el de piedra(gris))
			taBloques = new BitmapTextureAtlas(
					Video.gsPantallaJuego.getTextureManager(), 512, 128,
					TextureOptions.DEFAULT);
			trBloqueRojo = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(taBloques, Video.gsPantallaJuego,
							"bloque-rojo.png", 0, 0);
			trBloqueAzul = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(taBloques, Video.gsPantallaJuego,
							"bloque-azul.png", 128, 0);
			trBloqueVerde = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(taBloques, Video.gsPantallaJuego,
							"bloque-verde.png", 256, 0);
			taBloques.load();
			
			FontFactory.setAssetBasePath("font/");
			fPuntuacion= FontFactory.createFromAsset(Globales.Video.gsPantallaJuego.getFontManager(), Globales.Video.gsPantallaJuego.getTextureManager(), 1024, 1024, Globales.Video.gsPantallaJuego.getAssets(),
				    "hyperdigital.ttf", 64, true, android.graphics.Color.WHITE);
			  fPuntuacion.load();
			  
			 
			
		}

	}

}
