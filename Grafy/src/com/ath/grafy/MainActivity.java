package com.ath.grafy;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

	int marLeft = 30;
	int marTop = 20;
	int minMarLeft = 0;
	int minMarTop = 0;
	private Button  btnTag;
	RelativeLayout RL;
	private LinearLayout layout;
	float x;
	float y;
	
	
	 Paint mPaint;
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		RL = (RelativeLayout) findViewById(R.id.RelativeMain);
		layout = (LinearLayout) findViewById(R.id.linear_layout_tags);
		layout.setOrientation(LinearLayout.VERTICAL);  
		drawSiatka();
		
		
		 mPaint = new Paint();
	        mPaint.setAntiAlias(true);
	        mPaint.setDither(true);
	        mPaint.setColor(0xFFFF0000);
	        mPaint.setStyle(Paint.Style.STROKE);
	        mPaint.setStrokeJoin(Paint.Join.ROUND);
	       // mPaint.setStrokeCap(Paint.Cap.ROUND);
	        mPaint.setStrokeWidth(10);
	        
	        	
		    MyView view1 = new MyView(this, mPaint);
	        view1.setBackgroundColor(Color.BLACK);
	        view1.requestFocus();
	       // setContentView(view1);

	        Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
	        Canvas c = new Canvas(b);
	}
	
	@Override
    public void onClick(View v) {

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if(v.getId() == Integer.valueOf(i + "" + j)) {
                    //Toast.makeText(MainActivity.this, i+1 + " -- " + j+1, Toast.LENGTH_SHORT).show();
                	v.setBackgroundColor(Color.CYAN);
                }
            }
        }
    }
	
	public void drawSiatka()
	{
		for (int i = 0; i < 6; i++)
		{
		    LinearLayout row = new LinearLayout(this);
		    row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		    
		    for (int j = 0; j < 6; j++ )
		    {
		    	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(50, 50);
		    	lp.setMargins(marLeft + minMarLeft, marTop + minMarTop, 0, 0);
		    	
		        btnTag = new Button(this);
		        btnTag.setLayoutParams(lp);
		        btnTag.setText((i+1) + " " + (j+1));
		        btnTag.setId(Integer.valueOf(i + "" + j));
		        marLeft += 5 ;
		        minMarTop =+ (j+1)*5 + (j);
		        Log.i("mar", ""+ marLeft);
		        row.addView(btnTag);
		        
		        btnTag.setOnClickListener(this);
		    }
		    minMarLeft -= 1 + ((i+1));
		    minMarTop = 0;
		    marLeft = 30;
		    marTop += 5;
		    layout.addView(row);
		}
	}
	
	
	
}
