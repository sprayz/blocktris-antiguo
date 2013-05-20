package com.companyname.blocktrisgame;

import java.util.Map.Entry;

import com.companyname.blocktrisgame.assets.Globales;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;

public class ActividadPuntuaciones extends Activity {

	TableLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_puntuaciones);
		// Show the Up button in the action bar.
		layout = (TableLayout) findViewById(R.id.tablapuntuaciones);
		Typeface fuente = Typeface.createFromAsset(  getAssets(),
				"font/hyperdigital.ttf");
		TableRow tr;
		TextView tv;
		layout.setStretchAllColumns(true);
		
		tr=new TableRow(getApplicationContext());
		
		
		tv=new TextView(getApplicationContext());
		
		tv.setText(getResources().getText(R.string.textoPuntuacionesCabecera));
		tv.setTypeface(fuente);
		tv.setTextSize(30f);
		tr.addView(tv);
		
		layout.addView(tr);
		tr=new TableRow(getApplicationContext());
		
		
		tv=new TextView(getApplicationContext());
		
		tv.setText("===========");
		tv.setTextSize(30f);
		tr.addView(tv);
		
		layout.addView(tr);
		
		
		for(Entry<Long,String> par : Globales.Recursos.puntuaciones.descendingMap().entrySet()){
			
			
			
			//añadimos una fila
			tr=new TableRow(getApplicationContext());
			
			
			//añadims un control de texto a la fila con el nombre 
			tv=new TextView(getApplicationContext());
			
			tv.setText(par.getValue());
			tv.setTypeface(fuente);
			tv.setTextSize(30f);
			tr.addView(tv);
			//erpetimos para la puntuación
			tv=new TextView(getApplicationContext());
			tr.setMinimumHeight(30);
			tr.setPadding(0,5,0,0);
			tv.setTypeface(fuente);
			tv.setTextSize(16f);
			tv.setText(par.getKey().toString());
			tr.addView(tv);
			
			
			layout.addView(tr);
			
		}
		
		
	}

	

	

	

}
