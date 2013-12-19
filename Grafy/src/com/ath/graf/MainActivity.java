package com.ath.graf;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lamerman.FileDialog;

public class MainActivity extends Activity implements OnGestureListener, OnClickListener, OnTouchListener {

	@SuppressWarnings("deprecation")
	AbsoluteLayout absolut;
	int tmpX = 0;
	int tmpY = 0;
	int mTmpX = 0;
	int mTmpY = 0;
	int z = 0 ;
	int x = 0;
	RelativeLayout rl;
	private GestureDetector gDetector;
	private int width;
	private int height;
	DrawView drawView;
	private int wielkoscButtona = 50;
	private Dialog dialog;
	private int REQUEST_SAVE = 1;
	private SharedPreferences prefs;
	Editor editor;
	private int gruboscLinii;
	int backgroudResources;
	private LinearLayout customizeRL;
	private int stylResources;
	int lSizeMA=45;
	private Button button1;
	private Button button2;
	public static final String START_PATH = "START_PATH";
	public static final String CAN_SELECT_DIR = "CAN_SELECT_DIR";
	private boolean inv = true;
	private AbsoluteLayout ll;
	private int color;
	private TextView iloscWierzcholkow;
	private TextView iloscKrawedzi;
	public static String SDCARD_DIRECTORY;
	public static String REPORTS_DIRECTORY;
	int mIloscKrawedzi = 0;
	private CheckBox rysowanie;
	private boolean RysowanieMode = false;
	int firstClickID = 0;
	int secClickID = 0;
	private EditText et1;
	private int[] tablicaPolaczenRysowanie;
	private Dijkstra dijksta;
	private int REQUEST_LOA = 2;
	private String filePath;
	private int[] tablicaPolaczen;
	private int[] tablicaPolaczenRecznych;
	private int cou;
	boolean PrzesowanieMode = false;
	private int count = 0;
	Vertex [] vert;
	private int size = 0;
	private boolean trig = false;
	Vertex vertex;
	private boolean trigger = false;
	private boolean onActivResultOK = false;
	private CheckBox przesowanie;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		File sdCard = Environment.getExternalStorageDirectory();
		SDCARD_DIRECTORY = sdCard.getAbsolutePath();
		REPORTS_DIRECTORY = SDCARD_DIRECTORY + "/MyReports";
		// Create if necessary the desired folder
		
		 dijksta = new Dijkstra();
	
		prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		editor = prefs.edit();
		init();
		ll = (AbsoluteLayout) findViewById(R.id.ll);
		color = prefs.getInt("color", Color.RED);
		
		tablicaPolaczenRecznych = new int[1000];
		
		rysowanie = (CheckBox) findViewById(R.id.Rysowanie);
		przesowanie = (CheckBox) findViewById(R.id.Przesowanie);
		iloscWierzcholkow = (TextView) findViewById(R.id.iloscWierzcholkowTV);
		iloscKrawedzi = (TextView) findViewById(R.id.iloscKrawedziTV);
		
		iloscKrawedzi.setTextColor(color);
		iloscWierzcholkow.setTextColor(color);
		
		backgroudResources = prefs.getInt("backgroudResources", R.drawable.tlo1);
		stylResources = R.drawable.styl1;
		wielkoscButtona = prefs.getInt("wielkoscButtona", 50);
		gruboscLinii = prefs.getInt("gruboscLinii", 3);
		gDetector = new GestureDetector(this);
		rl = (RelativeLayout) findViewById(R.id.rl);
		rl.setBackgroundResource(backgroudResources);
		
		Display display = getWindowManager().getDefaultDisplay(); 
		 width = display.getWidth(); 
		 height = display.getHeight(); 
		absolut = (AbsoluteLayout) findViewById(R.id.Absolut);
		rysujSiatke(inv); 
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		t.start();
		//drawView = new DrawView(this); 
        
		//rl.addView(drawView);
		
		przesowanie.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) 
				{
					PrzesowanieMode = true;
				}
				else
				{
					PrzesowanieMode = false;
				}
			}
		});
		
		CheckBox pokazSiatke = (CheckBox) findViewById(R.id.pokazSiatke);
		pokazSiatke.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked) 
				{
					inv = true;
					ClearRelLayout(absolut);
					rysujSiatke(inv);
					
					
					
				}
				else 
				{
					inv = false;
					ClearRelLayout(absolut);
					rysujSiatke(inv);
				}
			}
		});
		
		rysowanie.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			private EditText et;

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked)
				{
					dialog = new Dialog(MainActivity.this);
					
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
					  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					  dialog.setContentView(R.layout.rysowanie_dialog);
					  dialog.setCancelable(true);
					  
					  	  
					   
						    LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.RysowanieLayout);
						  ll.setBackgroundResource(backgroudResources);
						  et = (EditText) dialog.findViewById(R.id.rysowanieET);
						  Button but = (Button) dialog.findViewById(R.id.RysowanieOK);
						  but.setOnClickListener(new OnClickListener() {
							
							private boolean trigger1 = false;
							

							@Override
							public void onClick(View v) {
								
								ClearAllClick(v);
								
								 size = Integer.valueOf((et.getText().toString()));
							
								vert = new Vertex[size];
								for (int i = 0; i < size; i++) 
							    {
									vert[i] = new Vertex(i + "");
									vert[i].adjacencies = new ArrayList<Edge>();
								}
								
								
								 tablicaPolaczen = new int[size];
			                     
			                     for (int i = 0; i < size; i++) 
			                     {
			                    	 Random r = new Random();
			                    	 int i1 = r.nextInt(36-1) + 1;
			                    	 
			                    	 tablicaPolaczen[i] = i1;
			                    	 Log.i("tablica polaczen", tablicaPolaczen[i] + "");
			                    	 
			                    	 for (int j = 0; j < i; j++) 
			                    	 { 
			                    		 if(tablicaPolaczen[i] == tablicaPolaczen[j]) 
			                			 {
			                			 	i--;
			                			 	trigger1 = true;
			                			 	break;
			                			 }
			                    		 if(trigger1 )
			                    		 {
			                    			 trigger1 = false;
			                    			 break;
			                    		 }
			                    	 }
			                     }
			                    AbsoluteLayout ll = (AbsoluteLayout) findViewById(R.id.ll);
			             		ll.removeAllViews();
								dialog.cancel();
								
							}
						});
					  
					  dialog.show();
					RysowanieMode = true;
				}
				else
					RysowanieMode = false;
			}
		});
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if(PrzesowanieMode)
		{
			cou = 0;
	        for(int i = 0; i < 6; i++) {
	            for(int j = 0; j < 6; j++) {
	            	cou++;
	                if(v.getId() == cou) 
	                {
	                	
	                	final int action = event.getAction();
	                    switch (action & MotionEvent.ACTION_MASK) {
	
	                        case MotionEvent.ACTION_DOWN: {
	                           // RysowanieMode = false;
	                            break;
	                        }
	
	                        case MotionEvent.ACTION_MOVE:{
	                        	float x = event.getRawX() - 20;
	                        	float y = event.getRawY() - 40;
	                        	Log.i("xy", x + "   " + y);
	                        	AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(wielkoscButtona, wielkoscButtona, (int)x, (int)y);
	                        	v.setLayoutParams(lp);
	                        	reDraw();
	                        	
	                            break;
	                        }
	                        case MotionEvent.ACTION_UP:
	                        {
	                        	//if(rysowanie.isChecked()) RysowanieMode = true;
	                        }
	                    }
	                    return true;
	                	
	                	
	                }
	            }
        }
        }
		
		
		return false;
	}
	
	public void reDraw()
	{
		ll.removeAllViews();
		View v = null;
		Log.i("reDraw", "1>>>> " + size + "");
		for(int i = 0; i < size; i ++)
		{
			for (int j = 0; j < size; j++) 
			{
				Log.i("reDraw", "2>>>> " + i + " " + j);
				if(vert[i].adjacencies == vert[j])
				{
					Log.i("reDraw", "3>>>> " + i + " " + j + "true");
					Mua(tablicaPolaczen[i], tablicaPolaczen[j], color);
				}
			}
		}
		if(size == 0)
			wczytajClick(v);
	}
	
	@Override
    public void onClick(View v) {

		cou = 0;
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
            	cou++;
                if(v.getId() == cou) {
                	
                	
					if(PrzesowanieMode)
                	{
                	
                	}
                   
                	if(RysowanieMode) 
                	{
                		Animation animzoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
    					Animation animzoom1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin1);
    					Animation animzoom2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin2);
    					
    					
    					AnimationSet s = new AnimationSet(false);
    					s.addAnimation(animzoom);
    					s.addAnimation(animzoom1);
    					s.addAnimation(animzoom2);
    					
    					v.startAnimation(s);
    					
                		if(firstClickID == 0)
                		{
                			firstClickID = cou;
                			secClickID = 0;
                			
                			 Button b = (Button) v;
                			 String buttonText = b.getText().toString();
                			 z = Integer.parseInt(buttonText);
                			
                			
                			
                		}
                		else
                		{
                			secClickID = cou;
                			Mua(firstClickID, secClickID, color);
                			firstClickID = 0;
                			secClickID = 0;
                			Button b = (Button) v;
	               			 String buttonText = b.getText().toString();
	               			 x = Integer.parseInt(buttonText);
                			
                			vert[z].adjacencies.add(new Edge(vert[x], 1));
                			Log.i("polaczenieVert", z + " polaczono z " + x);
                		}
                	}
                }
            }
        }
    }
	
	public void init()
	{
		Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "eurof76.ttf");
		
		Button but1 = (Button) findViewById(R.id.wczytajGrafBTN);
		Button but2 = (Button) findViewById(R.id.CustomizeBTN);
		Button but3 = (Button) findViewById(R.id.SzukajNajlepszejSciezkiBTN);
		Button but4 = (Button) findViewById(R.id.ZapiszGrafBTN);
		Button but5 = (Button) findViewById(R.id.ClearAllBTN);
		Button but6 = (Button) findViewById(R.id.wczytaj);
		CheckBox ch1 = (CheckBox) findViewById(R.id.pokazSiatke);
		CheckBox ch2 = (CheckBox) findViewById(R.id.Rysowanie);
		CheckBox ch3 = (CheckBox) findViewById(R.id.Przesowanie);
		
		
		but1.setTypeface(tf);
		but2.setTypeface(tf);
		but3.setTypeface(tf);
		but4.setTypeface(tf);
		but5.setTypeface(tf);
		but6.setTypeface(tf);
		ch1.setTypeface(tf);
		ch2.setTypeface(tf);
		ch3.setTypeface(tf);
	}
	

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public void rysujSiatke(boolean invis)
	{
		ClearRelLayout(absolut);
		tmpX = 0;
		tmpY = 0;
		mTmpX = 0;
		mTmpY = 0;
		cou = 0;
		
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				cou++;
				AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(wielkoscButtona, wielkoscButtona, mTmpX + (i*100) + tmpX, mTmpY + (j*60) + tmpY);
				Button but = new Button(getApplicationContext());
				if(wielkoscButtona > 50 && tablicaPolaczen != null)
				{
					for (int k = 0; k < tablicaPolaczen.length; k++) {
						if(tablicaPolaczen[k] == cou)
							but.setText(k + "");
					}
				}
				but.setId(cou);
				but.setLayoutParams(lp);
				
				
				
				if(invis) but.setVisibility(Button.INVISIBLE);
				
				if(tablicaPolaczen != null)
					for (int k = 0; k < tablicaPolaczen.length; k++) 
					{
						Log.i("file", tablicaPolaczen[k] + "  " + cou);
						if(tablicaPolaczen[k] ==  cou)
						{
							Log.i("file", tablicaPolaczen[k] + "  " + (i+1) + "" + (j+1));
							but.setVisibility(Button.VISIBLE);
							but.setBackgroundResource(backgroudResources);
						}
					}
				
				if(tablicaPolaczenRecznych != null)
					for (int k = 0; k < tablicaPolaczenRecznych.length; k++) 
					{
						if(tablicaPolaczenRecznych[k] ==  cou)
						{
							but.setVisibility(Button.VISIBLE);
							but.setBackgroundResource(backgroudResources);
						}
					}
				
				but.setBackgroundResource(stylResources);
				
				int[] locationInWindow = new int[2];
				but.getLocationInWindow(locationInWindow);
				but.setOnClickListener(this);
				but.setOnTouchListener(this);
				absolut.addView(but); 
				tmpY += j* 10;
				mTmpX += (j*2) + (j+5);
			}
			tmpY = 0;
			mTmpX = 0;
			mTmpY += (i * 2) + (i+5);
			tmpX += i* 20;
		}
		
		GetEdges();
	}
	
	public void ClearRelLayout(AbsoluteLayout RL){
	    for(int x=0;x<RL.getChildCount();x++){
	        if(RL.getChildAt(x) instanceof FrameLayout){
	            FrameLayout FL = (FrameLayout) RL.getChildAt(x);
	            FL.removeAllViewsInLayout();
	        }
	    }
	    RL.removeAllViewsInLayout();
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

		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {

		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {

		return false;
	}
	
	@Override
	protected void onResume() {

		super.onResume();
		
		Log.i("cykl", "onResume");
	}
	
	
	
	public void ZapiszGrafClick(View v) 
	{
		dialog = new Dialog(MainActivity.this);
		
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  dialog.setContentView(R.layout.zapisz_graf_dialog);
		  dialog.setCancelable(true);
		  LinearLayout loadRL = (LinearLayout) dialog.findViewById(R.id.ZapiszLayout);
		  loadRL.setBackgroundResource(backgroudResources);
		
		  Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "eurof76.ttf");
		  TextView tv1 = (TextView) dialog.findViewById(R.id.ZapiszTV);
		   et1 = (EditText) dialog.findViewById(R.id.editText1);
		  Button zapisz = (Button) dialog.findViewById(R.id.ZapiszButton);
		  
		  et1.setTypeface(tf);
		  zapisz.setTypeface(tf);
		  tv1.setTypeface(tf);
		  
		  dialog.show();
		
		  
		  zapisz.setOnClickListener(new OnClickListener() {
			
			private File mFolder;
			private String mFileName;
			private String restOfBody = "*******";
			private String sBody = WriteGraphToFile() + "\n" + restOfBody;
			
//TODO
			@Override
			public void onClick(View v) {
				
				String s = et1.getText().toString();
				
				 generateNoteOnSD(s, sBody);
			     
			     dialog.cancel();
			     
			}
		});
		  
	}
	
	public void ClearAllClick(View v)
	{
		vert = new Vertex[size];
		for (int i = 0; i < size; i++) 
	    {
			vert[i] = new Vertex(i + "");
			vert[i].adjacencies = new ArrayList<Edge>();
		}
		size = 0;
		ll = (AbsoluteLayout) findViewById(R.id.ll);
		ll.removeAllViews();
		mIloscKrawedzi = 0;
		count = 0;
		tablicaPolaczenRecznych = null;
		tablicaPolaczenRecznych = new int[1000];
		iloscKrawedzi.setText("Iloœæ Krawêdzi:\n>>\t" + mIloscKrawedzi);
		iloscWierzcholkow.setText("Iloœæ wierzcho³ków\n>>\t" + count);
		rysujSiatke(inv);
	}
	
	public void wczytajClick(View v)
	{
		rysowanie.setChecked(false);
		size = 0;
		GetEdges();
	}
	
	
	public void generateNoteOnSD(String sFileName, String sBody){
        try
        {
            File root = new File(Environment.getExternalStorageDirectory(), "Grafy");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName + ".txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.append("**************\n");

  String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

  			writer.append("Graf zosta³ wygenerowany na " + android.os.Build.MODEL + "\nW dniu: " + mydate + "\n");
 			writer.append("**************\n");
  			writer.append("Autorzy:\n");
     		writer.append("£ukasz Bartoszewski\n");
      		writer.append("Pan Andrzej Krawiec\n");
      		writer.append("Grzegorz Rafacz\n");
      		writer.append("Ziemowit Niezgoda\n");
     		writer.append("**************\n");
     		writer.append("PROJEKT na zaliczenie przedmiotu: Algorytmiczne ujêcie teorii grafów.\n");
      		writer.append("Prowadz¹cy zajêcia: prof. ATH dr hab. in¿. Stanis³aw Zawiœlak.\n");
            writer.append("AKADEMIA TECHICZNO-HUMANISTYCZNA W BIELSKU-BIA£EJ.\n");
            writer.flush();
            writer.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
	
	
	public String WriteGraphToFile() 
	{			
		String s = "";
		
		for(int i = 0; i < dijksta.NumberOfLines; i++)
		{
			String Linia = "";
			for(int j = 0; j < dijksta.NumberOfLines; j++)
			{
				boolean GotYa = false;
				for(Edge Edg : dijksta.VertexArray[i].adjacencies)
				{
					if(Integer.parseInt(Edg.target.name) == j)
					{
						Linia = Linia + (int)Edg.weight;
						GotYa = true;
					}
				}
				
				if(!GotYa)
				{
					Linia = Linia + 0;
				}	
				
				GotYa = false;
				Linia = Linia + " ";
			}
			Linia = Linia.substring(0, Linia.length() - 1);
			s += Linia + "\n";
			iloscKrawedzi.setText(s);
		}	
		
		return s;
	}
	
	public void szukajNajlepszejScierzkiClick(View v)
	{
		
//		    Mua(2,18,color);
//		    Mua(18,13,color); 
//		    Mua(13,5,color);
//		    Mua(5,22,color);
//		    Mua(22,2,color);
		
		if(!RysowanieMode)
		{
			
			List<Vertex> path = dijksta.LookForShortestPath(dijksta.VertexArray[5], dijksta.VertexArray[0]);
			Log.i("zxc", path.size() + "");
			Vertex last = null;
			for (Vertex vertex : path) 
			{
				if(last != null)
				{
					Log.i("zxc", last.name + "   " + vertex.name);
					Mua(tablicaPolaczen[Integer.parseInt(last.name)], tablicaPolaczen[Integer.parseInt(vertex.name)], Color.GRAY);
				}
				last = vertex;
			}
			
		}
		else
		{
			List<Vertex> path = dijksta.LookForShortestPath(vert[5], vert[0]);
			Log.i("zxc", path.size() + " ");
			Vertex last = null;
			for (Vertex vertex : path) 
			{
				Log.i("zxc",  "   " + vertex.name);
				if(last != null)
				{
					Log.i("zxc", last.name + "   " + vertex.name);
					Mua(tablicaPolaczen[Integer.parseInt(last.name)], tablicaPolaczen[Integer.parseInt(vertex.name)], Color.GRAY);
				}
				last = vertex;
			}
		}
	}
	
	public synchronized void onActivityResult(final int requestCode,
            int resultCode, final Intent data) {

            if (resultCode == Activity.RESULT_OK) {
            	
            	onActivResultOK  = true;
            	Log.i("cykl", "onActivityResoult");
            	View v = null;
            	ClearAllClick(v);
            	
                    if (requestCode == REQUEST_SAVE) {
                           Log.i("file", "saveing");
                    } else if (requestCode == REQUEST_LOA ) {
                           Log.i("file", "loading");
                    }
                    
                     filePath = data.getStringExtra(FileDialog.RESULT_PATH);
                     Log.i("file", filePath);
                     
                     try {
						dijksta.LetsDoThis(filePath);
				 	 } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					 }
                     
                     tablicaPolaczen = new int[dijksta.NumberOfLines];
                     
                     for (int i = 0; i < dijksta.NumberOfLines; i++) 
                     {
                    	 Random r = new Random();
                    	 int i1 = r.nextInt(36-1) + 1;
                    	 
                    	 tablicaPolaczen[i] = i1;
                    	 
                    	 for (int j = 0; j < i; j++) 
                    	 { 
                    		 if(tablicaPolaczen[i] == tablicaPolaczen[j]) 
                			 {
                			 	i--;
                			 	trigger = true;
                			 	break;
                			 }
                    		 if(trigger)
                    		 {
                    			 trigger = false;
                    			 break;
                    		 }
                    	 }
                    	 
                    	 
                		 
                    	 Log.i("file", tablicaPolaczen[i] + "");
					 } 
                     rysujSiatke(inv);
                    

            } else if (resultCode == Activity.RESULT_CANCELED) {
                    Logger.getLogger(MainActivity.class.getName()).log(
                                    Level.WARNING, "file not selected");
            }

    }

	
	public void wczytajGrafClick(View v)
	{
		dialog = new Dialog(MainActivity.this);
		
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  dialog.setContentView(R.layout.wczytaj_graf_dialog);
		  dialog.setCancelable(true);
		  LinearLayout loadRL = (LinearLayout) dialog.findViewById(R.id.loadLayout);
		  loadRL.setBackgroundResource(backgroudResources);
		
		  Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "eurof76.ttf");
		  Button tv1 = (Button) dialog.findViewById(R.id.textView1);
		  Button tv2 = (Button) dialog.findViewById(R.id.textView2);
		   
		  tv1.setTypeface(tf);
		  tv2.setTypeface(tf);
		  
		  tv1.setOnClickListener(new View.OnClickListener() {
			
			

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getBaseContext(), FileDialog.class);
                intent.putExtra(FileDialog.START_PATH, "/sdcard");
                
                //can user select directories or not
                intent.putExtra(FileDialog.CAN_SELECT_DIR, true);
                
                //alternatively you can set file filter
                //intent.putExtra(FileDialog.FORMAT_FILTER, new String[] { "png" });
                
                startActivityForResult(intent, REQUEST_SAVE);
				
			
			}
		});
		  
		  tv2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				dialog = new Dialog(MainActivity.this);
				
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
				  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				  dialog.setContentView(R.layout.przykladowe_grafy_dialog);
				  dialog.setCancelable(true);
				  
				  LinearLayout pl = (LinearLayout) dialog.findViewById(R.id.przykladoweLayout);
				  pl.setBackgroundResource(backgroudResources);
				  
				  dialog.show();
				
			}
		});
		  
		dialog.show();
	}
	
	public void customizeClick(View v)
	{
		dialog = new Dialog(MainActivity.this);
		
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  dialog.setContentView(R.layout.customize_dialog);
		  dialog.setCancelable(true);
		  customizeRL = (LinearLayout) dialog.findViewById(R.id.customizeLayout);
		  customizeRL.setBackgroundResource(backgroudResources);
		  
		  
		SeekBar seekBar1 = (SeekBar) dialog.findViewById(R.id.seekBar1);
		SeekBar seekBar2 = (SeekBar) dialog.findViewById(R.id.seekBar2);
		RadioButton radio1 = (RadioButton) dialog.findViewById(R.id.radioButton1);
		RadioButton radio2 = (RadioButton) dialog.findViewById(R.id.radioButton2);
		RadioButton radio3 = (RadioButton) dialog.findViewById(R.id.radioButton3);
		RadioButton radio4 = (RadioButton) dialog.findViewById(R.id.radioButton4);
		RadioButton radio5 = (RadioButton) dialog.findViewById(R.id.radioButton5);
		RadioButton radio6 = (RadioButton) dialog.findViewById(R.id.radioButton6);
		RadioButton radio7 = (RadioButton) dialog.findViewById(R.id.radioButton7);
		RadioButton radio8 = (RadioButton) dialog.findViewById(R.id.radioButton8);
		RadioButton radio9 = (RadioButton) dialog.findViewById(R.id.radioButton9);
		
		if(backgroudResources == R.drawable.tlo1) radio1.setChecked(true);
		else if(backgroudResources == R.drawable.tlo2) radio2.setChecked(true);
		else radio3.setChecked(true);
		
		if(stylResources == R.drawable.styl1) radio4.setChecked(true);
		else if(stylResources == R.drawable.styl2) radio5.setChecked(true);
		else radio6.setChecked(true);
		
		if(color == Color.RED) radio7.setChecked(true);
		else if(color == Color.GREEN) radio9.setChecked(true);
		else radio8.setChecked(true);
		
		radio1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked) 
				{
					backgroudResources = R.drawable.tlo1;
					editor.putInt("backgroudResources", backgroudResources);
					editor.commit();
					
					customizeRL.setBackgroundResource(backgroudResources);
					rl.setBackgroundResource(backgroudResources);
				}
			}
		});
		
		radio2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked) 
				{
					backgroudResources = R.drawable.tlo2;
					editor.putInt("backgroudResources", backgroudResources);
					editor.commit();
					
					customizeRL.setBackgroundResource(backgroudResources);
					rl.setBackgroundResource(backgroudResources);
				}
			}
		});

		radio3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked) 
				{
					backgroudResources = R.drawable.tlo3;
					editor.putInt("backgroudResources", backgroudResources);
					editor.commit();
					
					customizeRL.setBackgroundResource(backgroudResources);
					rl.setBackgroundResource(backgroudResources);
				}
			}
		});
		
			radio4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked) 
				{
					stylResources = R.drawable.styl1;
					
					
					ClearRelLayout(absolut);
					tmpX = 0;
					tmpY = 0;
					mTmpX = 0;
					mTmpY = 0;
					rysujSiatke(inv);
				}
			}
		});

			radio5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked) 
				{
					stylResources = R.drawable.styl2;
					
					
					ClearRelLayout(absolut);
					tmpX = 0;
					tmpY = 0;
					mTmpX = 0;
					mTmpY = 0;
					rysujSiatke(inv);
				}
			}
		});

			radio6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked) 
				{
					stylResources = R.drawable.styl3;
					
					
					ClearRelLayout(absolut);
					tmpX = 0;
					tmpY = 0;
					mTmpX = 0;
					mTmpY = 0;
					rysujSiatke(inv);
				}
			}
		});
			
			radio7.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					
					if(isChecked) 
					{
						color = Color.RED;
						editor.putInt("color", color);
						editor.commit();
						
						iloscKrawedzi.setTextColor(color);
						iloscWierzcholkow.setTextColor(color);
						
						View v = null;
						mIloscKrawedzi = 0;
						ll.removeAllViews();
						//szukajNajlepszejScierzkiClick(v);
						
					}
				}
			});
			
			radio8.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					
					if(isChecked) 
					{
						color = Color.BLUE;
						editor.putInt("color", color);
						editor.commit();
						
						iloscKrawedzi.setTextColor(color);
						iloscWierzcholkow.setTextColor(color);
						
						View v = null;
						mIloscKrawedzi = 0;
						ll.removeAllViews();
						//szukajNajlepszejScierzkiClick(v);
					}
				}
			});
			
			
			radio9.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					
					if(isChecked) 
					{
						color = Color.GREEN;
						editor.putInt("color", color);
						editor.commit();
						
						iloscKrawedzi.setTextColor(color);
						iloscWierzcholkow.setTextColor(color);
						
						View v = null;
						mIloscKrawedzi = 0;
						ll.removeAllViews();
						//szukajNajlepszejScierzkiClick(v);
					}
				}
			});
		
		seekBar1.setProgress(wielkoscButtona);
		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
		
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser)
			{
				wielkoscButtona = progress;
				ClearRelLayout(absolut);
				tmpX = 0;
				tmpY = 0;
				mTmpX = 0;
				mTmpY = 0;
				View v = null;
				
				rysujSiatke(inv);
				//szukajNajlepszejScierzkiClick(v);
				
				editor.putInt("wielkoscButtona", wielkoscButtona);
				editor.commit();
			}
		});

		seekBar2.setProgress(gruboscLinii*10);
		seekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			private Thread t;

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				gruboscLinii = progress/10;
				editor.putInt("gruboscLinii", gruboscLinii);
				editor.commit();
				
				
				View v = null;
				mIloscKrawedzi = 0;
				ll.removeAllViews();
				//szukajNajlepszejScierzkiClick(v);
				//rysujSiatke();
				
				//TODO
			}
		});
		
		
		
		dialog.show();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	    // TODO Auto-generated method stub
	    super.onWindowFocusChanged(hasFocus);

		    if (hasFocus) 
		    {
		    	Log.i("cykl", "hasFocus");
		    	rysujSiatke(inv);
		    	
		    	if(onActivResultOK)
		    	{
		    		Log.i("cykl", "focus resoult ok");
		    		onActivResultOK = false;
		    		GetEdges();
		    	}
		    }
		    

	}

	private void GetEdges() 
	{
		// TODO Auto-generated method stub	
		if(dijksta != null)
			for(int i = 0 ; i < dijksta.NumberOfLines ; i++)
			{
				for(int j = 0; j < dijksta.NumberOfLines; j++)
				{
					for(Edge Edg : dijksta.VertexArray[i].adjacencies)
					{
						if(Integer.parseInt(Edg.target.name) == Integer.parseInt(j +""))
						{
							Log.i("zzz", Edg.target.name + "  " + i + "  " + j + " " +tablicaPolaczen[i] + " " + tablicaPolaczen[j]);
							Mua(tablicaPolaczen[i], tablicaPolaczen[j], color);
						}
					}
				}
			}
	}
	
public boolean Mua(int a, int b, int color)
{
//	 if(tablicaPolaczenRecznych != null)
//	for (int i = 0; i < tablicaPolaczenRecznych.length; i++) 
//	 {
//		for (int j = 0; j < tablicaPolaczenRecznych.length; j++) {
//			if(tablicaPolaczenRecznych[i] == a && tablicaPolaczenRecznych[j] == b) return false;
//		}
//		 
//	 }
//	 if(tablicaPolaczen != null)
//	for (int i = 0; i < tablicaPolaczen.length; i++) 
//	 {
//		for (int j = 0; j < tablicaPolaczen.length; j++) {
//			 if(tablicaPolaczen[i] == a && tablicaPolaczen[j] == b) return false;
//		}
//		
//	 }
	 mIloscKrawedzi++;
	 iloscKrawedzi.setText("Iloœæ Krawedzi:\n>>\t" + mIloscKrawedzi );
	 iloscWierzcholkow.setText("Iloœæ wierzcho³ków\n>>\t" + count);
	 button1 = (Button)findViewById(a);
	 button2 = (Button)findViewById(b);
	 
	 
	 tablicaPolaczenRecznych[count] = a;
	 tablicaPolaczenRecznych[count+1] = b;
	 count += 2;
	 if(count>900) count = 1;
	 drawView = new DrawView(this,button1.getLeft()+(button1.getWidth()/2),button1.getTop()+(button1.getHeight()/2),button2.getLeft()+(button2.getWidth()/2),button2.getTop()+(button2.getHeight()/2),gruboscLinii, color);
	 ll.addView(drawView);
	 return true;
}
}
