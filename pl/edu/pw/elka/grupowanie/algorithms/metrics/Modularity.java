package pl.edu.pw.elka.grupowanie.algorithms.metrics;

import java.util.Set;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;

public class Modularity<V, E> 
{
	public static <V,E> double calculateModularity(Graph<V,E> g, Set<Set<V>> division)
	{
	
		if(division.size()<=1) return 0;

		int n=division.size();
		int tab[][] = new int[n][n];

		for (E edge:g.getEdges())
		{
			Pair<V> ends=g.getEndpoints(edge);
			int x=findSet(ends.getFirst(), division);
			int y=findSet(ends.getSecond(), division); 
			tab[x][y]++;
			
		}
		double mat[][] = new double[n][n];
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				mat[i][j]=(double)tab[i][j]/g.getEdgeCount();
				
		double modularity=0;
		for(int i=0; i<n; i++)
		{		
			double row=0;
			for(int j=0; j<n; j++)
			{
				row+=mat[i][j];								
			}
			modularity+=mat[i][i]-row*row;
		}
				
		return modularity;
		
	}

					
		private static <V> int findSet(V v, Set<Set<V>> division) 
		{
			int i=0;
			for(Set<V> s: division)
			{
				for(V vv: s)
				{ 
					if(v==vv)
					{
				
						return i;
					}
				}
				i++;
			}
			return 0;
		}
		
}
