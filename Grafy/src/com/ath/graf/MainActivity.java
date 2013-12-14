package com.ath.graf;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnGestureListener {

	@SuppressWarnings("deprecation")
	AbsoluteLayout absolut;
	int tmpX = 0;
	int tmpY = 0;
	int mTmpX = 0;
	int mTmpY = 0;
	RelativeLayout rl;
	private GestureDetector gDetector;
	private int width;
	private int height;
	DrawView drawView;
	private Dialog dialog;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gDetector = new GestureDetector(this);
		rl = (RelativeLayout) findViewById(R.id.rl);
		Display display = getWindowManager().getDefaultDisplay(); 
		 width = display.getWidth(); 
		 height = display.getHeight(); 
		absolut = (AbsoluteLayout) findViewById(R.id.Absolut);
		rysujSiatke();
		
		//drawView = new DrawView(this);
        
		//rl.addView(drawView);
	}
	

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public void rysujSiatke()
	{
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(50, 50, mTmpX + (i*100) + tmpX, mTmpY + (j*60) + tmpY);
				Button but = new Button(getApplicationContext());
				but.setText((i+1) + "" + (j+1));
				but.setId(Integer.valueOf(i+""+j));
				but.setLayoutParams(lp);
				but.setBackgroundResource(R.drawable.pobrane_edited);
				
				int[] locationInWindow = new int[2];
				   but.getLocationInWindow(locationInWindow);
				Log.i("zzz", locationInWindow[0] +  " " + locationInWindow[1]);
				absolut.addView(but);
				tmpY += j* 10;
				mTmpX += (j*2) + (j+5);
			}
			tmpY = 0;
			mTmpX = 0;
			mTmpY += (i * 2) + (i+5);
			tmpX += i* 20;
		}
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) 
	{
		
		if (e1.getRawX() > e2.getRawX() && e1.getRawX() > (85f/100f)*width) 
        {
			 LinearLayout ll = (LinearLayout) findViewById(R.id.wysowanyLayout);
			 ll.setVisibility(LinearLayout.VISIBLE);
        }
		else if(e1.getRawX() < e2.getRawX() && e1.getRawX() > (85f/100f)*width)
		{
			LinearLayout ll = (LinearLayout) findViewById(R.id.wysowanyLayout);
			ll.setVisibility(LinearLayout.INVISIBLE);
		}
       
 
		return false;
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
		return gDetector.onTouchEvent(event);
    }

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void szukajNajlepszejScierzkiClick(View v)
	{
		
	}
	
	public void wczytajGrafClick(View v)
	{
		dialog = new Dialog(MainActivity.this);
		
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  dialog.setContentView(R.layout.wczytaj_graf_dialog);
		  dialog.setCancelable(true);
		  
		

		dialog.show();
	}
	
	public void customizeClick(View v)
	{
		dialog = new Dialog(MainActivity.this);
		
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  dialog.setContentView(R.layout.customize_dialog);
		  dialog.setCancelable(true);
		  
		

		dialog.show();
	}
}
