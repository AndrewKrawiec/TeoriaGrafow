package com.ath.graf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import android.os.Environment;
import android.widget.Button;

public class Dijkstra
{
	public static int NumberOfLines;
	public static Vertex[] VertexArray;
	
    public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);
		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
		}
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

	static String readFile(String filename) throws IOException {
		
		//File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grafy/");

		//Get the text file
		File f = new File(filename);
		
		BufferedReader br = new BufferedReader(new FileReader(f));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
    	
	public static int CountLines(String Graf)
	{
		int CountLines = 0;
		   for(int i = 0; i< Graf.length();i++)
		      {
		      	if(Graf.charAt(i) == '\n')
		      	{
		      		CountLines++;
		      	}      		
		      }   
		   return CountLines;
	}
	
	public static void ReadGraphFromString(String Graf)
	{
		int j = 0;
	    for (int i = 0; i < Graf.length();) 
	    {
		    for (; j < NumberOfLines; j++) 
		    {	
		    	VertexArray[j].adjacencies = new ArrayList<Edge>();
		    	
		    	int Linia = 0;
		    	if(j>0)
		    		i++;
		    	
		    	while(Graf.charAt(i) != '\n')
		    	{
		    		if(Graf.charAt(i) == ' ')
		    			i++;
		    		
		    		if(Graf.charAt(i) != 0)
		    		{
		    			String ReadNumber = "";
		    			do
		    			{
		    				if(Graf.charAt(i) == '\n')
		    					break;
		    					
		    				ReadNumber = ReadNumber + Graf.charAt(i);
		    				i++;
		    			}while(Graf.charAt(i) != ' ');

		    			if(Integer.parseInt(ReadNumber) > 0)
		    			{
		    				VertexArray[j].adjacencies.add(new Edge(VertexArray[Linia], Integer.parseInt(ReadNumber)));
		    			}
		    		}
		    		
		    		else
		    			i++;
		
			    	Linia++;
		    	}
			}
		    i = Graf.length();
	    }
	}
	
	public static void FormVertices()
	{
		 for (int i = 0; i < NumberOfLines; i++) 
		    {
				VertexArray[i] = new Vertex(i + "");
			}
	}
	
	public static List<Vertex> LookForShortestPath(Vertex vertex, Vertex vertex2) 
	{
		computePaths(vertex); //sk¹d
	    System.out.println("Distance to " + vertex2 + ": " + vertex2.minDistance); //dok¹d
	    List<Vertex> path = getShortestPathTo(vertex2);
	    return path;
	}
	
	public static void WriteGraphToFile(String FileName) throws Exception, 
	UnsupportedEncodingException
		{			
			PrintWriter writer = new PrintWriter(FileName + ".txt", "UTF-8");
			
			for(int i = 0; i < NumberOfLines; i++)
			{
				String Linia = "";
				for(int j = 0; j < NumberOfLines; j++)
				{
					boolean GotYa = false;
					for(Edge Edg : VertexArray[i].adjacencies)
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
				writer.println(Linia);		
			}	
			writer.close();
		}
	
	public static void LetsDoThis(String file) throws IOException
	{
		String Graf = readFile(file);
		
		Graf = Graf.substring(0, Graf.indexOf('*'));
		
	    NumberOfLines = CountLines(Graf);

	    VertexArray = new Vertex[NumberOfLines];
	    
	    FormVertices();  
	    
	    ReadGraphFromString(Graf);
	    
//	    LookForShortestPath(VertexArray[1], VertexArray[3]); //Je¿eli infinity ---> nie ma œcie¿ki.
	}
	
	private static void GetEdges() 
	{
		// TODO Auto-generated method stub	
			for(int i = 0; i<NumberOfLines; i++)
			{
			
				for(int j = 0; j < NumberOfLines; j++)
				{
					for(Edge Edg : VertexArray[i].adjacencies)
					{
						if(Integer.parseInt(Edg.target.name) == j)
						{
							// Tutaj ³¹czysz buttony Button[i] z Button[j]
						}
					}
				}
			
			}
	}
	

}