package pl.edu.pw.elka.grupowanie.algorithms.cluster;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.collections15.bag.TreeBag;
import edu.uci.ics.jung.graph.Graph;
/***
 * Community Detection for Large Scale Social Network Sites - Ingyu Lee & Byung-Won On
 *  2012 Proceedings of IEEE Southeastcon
 * 
 * @author Natalia Biaduń
 *
 * @param <V> vertex
 * @param <E> edge
 */


public class PeerPressureVotingClusterer<V,E> implements Algorithm<V, E> 
{

	public Set<Set<V>> getCommunities(Graph<V, E> G) 
	{
		HashMap<V, V> leaders, oldLeaders = null;  
		leaders = new HashMap<V,V>();
		
		//centroid selection - choosing leaders by number of neighbors of vertex
		for(V vertex:G.getVertices())
		{			
			int maxDegree = 0;
			V leader=null;
			for(V neighbour:G.getNeighbors(vertex))
			{				
				if(G.getNeighborCount(neighbour) > maxDegree)
				{
					maxDegree=G.getNeighborCount(neighbour);
					leader=neighbour;
				}
			}
			if(G.getNeighborCount(vertex)>maxDegree)
				leader=vertex;
			leaders.put(vertex, leader);
		}
		
		System.out.println( leaders.toString());
		
		
		//centroid evolving
		while(!(leaders.equals(oldLeaders)))/////////////////////////////stworzyc warunek stopu////???????????????????????!!!!U*#(&(Y($&H(Fndjeu
		 {				 
			oldLeaders=leaders;
			 leaders = new HashMap<V,V>();
			 for(V vertex:G.getVertices())
			 {
				 	TreeBag<V> localLeader = new TreeBag<V>();
				 	localLeader.add(oldLeaders.get(vertex));
				 	for(V neighbor:G.getNeighbors(vertex))
				 	{
				 		localLeader.add(oldLeaders.get(neighbor));				 		
				 	}
				 	
					
					int popularity=0;
					V mostPopular=null;
					for(V vv:localLeader)
					{
						if(localLeader.getCount(vv)>popularity)
						{
							popularity=localLeader.getCount(vv);
							mostPopular=vv;
						}
					}
					leaders.put(vertex, mostPopular);
			}			 
		 }
		 
		//splitting vertexs into communities
		 Set<Set<V>> communities=new HashSet<Set<V>>();
		 Set<Entry<V,V>> pairs=leaders.entrySet();
		 for (V v:leaders.values())
		 {
			 Set<V> community=new HashSet<V>();
			 for(Entry<V,V> e:pairs)
			 {
				 if(e.getValue().equals(v))
				 {
					 community.add(e.getKey());
				 } 
		
			 }
			 communities.add(community);	 
			 pairs.removeAll(community);
		 }
		 	
		return communities;
	}

}
