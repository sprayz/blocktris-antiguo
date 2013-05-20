package com.companyname.blocktrisgame;

import java.lang.reflect.ReflectPermission;
import java.util.Currency;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSCounter;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.companyname.blocktrisgame.assets.*;
import com.companyname.blocktrisgame.assets.Globales.Video;
import com.companyname.blocktrisgame.logica.Bloque;
import com.companyname.blocktrisgame.logica.Tabla;

 
public class ActividadJuego extends BaseGameActivity {
       
		
	private int filas = 24;
	private int columnas = 10;
        
        
        
        
        
        
        
        
        /*
         * Métodos  de utilidad	
         */
        
        
        
       
        
        
        
               
        
        
        
        
    @Override
	public void onBackPressed() {
		
		super.onBackPressed();
		
		
		
	}

				/*
     * Callbacks generales
     */
                @Override
                public EngineOptions onCreateEngineOptions() {
                        Globales.Video.camara= new Camera(0, 0, Globales.anchoCamara, Globales.altoCamara);
                        Globales.Video.gsPantallaJuego = this;
                        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(Globales.anchoCamara, Globales.altoCamara), Globales.Video.camara);
                        Log.d("DEBUG","ENGINE CREADA");
                        return engineOptions;
                }
               
                @Override
                public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
                		Globales.Recursos.cargarRecursos();
                		Log.d("DEBUG","RECURSOS CREADOS");
 
                        pOnCreateResourcesCallback.onCreateResourcesFinished();
                }
               
              
                
                public void onJuegoAcabado(final long puntuacion){
                	Globales.Video.escenaJuego.setIgnoreUpdate(true);
                	
                	 
                	
                	
                	
                	
                	 
                	 final EditText etNombre = new EditText(this); 
                	 LinearLayout ll= new LinearLayout(this);
                	    ll.setOrientation(1); 
                	    ll.addView(etNombre);
                	 Builder alert = 	new AlertDialog.Builder(this)
                     .setTitle("Blocktris")
                     .setMessage(getResources().getText(R.string.textoDialogoPreguntaNombre))
                     .setPositiveButton(getResources().getText(R.string.textoDialogoAceptar), new OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                     
                            	 
                            	 if(!etNombre.getText().equals(""))
                            		 Globales.Recursos.puntuaciones.put(Long.valueOf(puntuacion),etNombre.getText().toString() );
                            	 	Globales.Recursos.guardarPuntuaciones();
                            	 startActivity(new Intent(ActividadJuego.this, ActividadPuntuaciones.class));
                                     ActividadJuego.this.finish();  
                                     
                             }})
                     .setNegativeButton(getResources().getText(R.string.textoDialogoCancelar), new OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                             	Globales.Video.escenaJuego.setIgnoreUpdate(false);
                             		return;
                             }});    
                	 
                	 alert.setView(ll);
                	 alert.show();
                	 
                	
                	 
                	 
                	
                }
                
                
                
                
                
                
                
                @Override
                public boolean onKeyDown(int keyCode, KeyEvent event) {
                  
                	
                	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    	Globales.Video.escenaJuego.setIgnoreUpdate(true);
                    	
                
                         ActividadJuego.this.finish();  
                    
                    
                        return true;
                    }
                    return super.onKeyDown(keyCode, event);
                }

				@Override
                public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
					Log.d("DEBUG","ESCENA CREADA");
                    
			      	Globales.Video.escenaJuego = new EscenaJuego(filas,columnas,20,0.6f,0.7f);
			     
			      	Globales.Video.escenaJuego.empezarJuego();
		            
		             
                        pOnCreateSceneCallback.onCreateSceneFinished(Globales.Video.escenaJuego);
                }
               
                @Override
                public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {
                	Log.d("DEBUG","ESCENA POBLADA");
          
                	
                	
                	pOnPopulateSceneCallback.onPopulateSceneFinished();
                }
               
}