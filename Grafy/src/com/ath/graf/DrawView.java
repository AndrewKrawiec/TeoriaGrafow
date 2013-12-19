package com.ath.graf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.util.Log;
import android.view.View;


public class DrawView extends View {
	
    Paint paint = new Paint();
    
   int lineSize;
    int x;
    int y;
    int clear = 0;
    int x1 = 0;
    int y1 = 0;
    public DrawView(Context context, int x ,int y,int x1,int y1,int lineSize, int color) {
    	super(context);
    	
        paint.setColor(color);
        
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.lineSize=lineSize;
     
    }
    


@Override
    public void onDraw( Canvas canvas) {
	
	        paint.setStrokeWidth(lineSize);
	       
	         canvas.drawLine(x,y, x1,y1, paint);
          
    }
  

}