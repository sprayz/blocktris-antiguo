package com.companyname.blocktrisgame;

import com.companyname.blocktrisgame.assets.Globales;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class ActividadMenu extends Activity implements OnClickListener{

	TextView menuJugar;
	TextView menuPuntuaciones;
	TextView menuSalir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_menu);
		Typeface fuente = Typeface.createFromAsset(getAssets(),
				"font/hyperdigital.ttf");
		menuJugar = (TextView) findViewById(R.id.tvJugar);
		menuPuntuaciones = (TextView) findViewById(R.id.tvPuntuaciones);
		menuSalir = (TextView) findViewById(R.id.tvSalir);

		menuJugar.setTypeface(fuente);
		menuSalir.setTypeface(fuente);
		menuPuntuaciones.setTypeface(fuente);
		
		menuJugar.setOnClickListener(this);
		menuPuntuaciones.setOnClickListener(this);
		menuSalir.setOnClickListener(this);
		Globales.Video.actividadMenu = this;
		Globales.Recursos.cargarPuntuaciones();
		
	}

	
	
		
	 @Override
		public void onBackPressed() {
			
		 System.exit(0);
			super.onBackPressed();
			
			
			
		}

	

	@Override
	public void onClick(View v) {
	TextView menu = (TextView) v;
		
		Intent intent;
		switch (menu.getId()) {

		case R.id.tvJugar:
			intent = new Intent(this, ActividadJuego.class);
            startActivity(intent);
			break;
		case R.id.tvPuntuaciones:
			intent = new Intent(this, ActividadPuntuaciones.class);
            startActivity(intent); 
            
			break;
		case R.id.tvSalir:
			System.exit(0);
			break;

		}
		
	}


}
