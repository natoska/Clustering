package pl.edu.pw.elka.grupowanie.algorithms.cluster;

import java.util.Set;

import com.rits.cloning.Cloner;

import pl.edu.pw.elka.grupowanie.algorithms.metrics.Modularity;
import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.Graph;
/***
 * 
 * @author Natalia Biaduń
 *
 * @param <V> vertex
 * @param <E> edge
 */

public class GirvanNewmanClusterer<V,E> implements Algorithm<V, E> 
{
	
	public Set<Set<V>> getCommunities(Graph<V, E> G) 
	{
		double max_modularity=0;
		Cloner cloner=new Cloner();
		Graph<V,E> graph = cloner.deepClone(G);
		
		Set<Set<V>> clusterSet, bestSet=null;
		WeakComponentClusterer<V,E> wcSearch = new WeakComponentClusterer<V,E>();
    	
		int startEdges=graph.getEdgeCount();
        for (int k=0; k<startEdges;k++) 
        {
            BetweennessCentrality<V,E> bc = new BetweennessCentrality<V,E>(graph);
            E to_remove = null;
            double score = 0;
            for (E e : graph.getEdges())
                if (bc.getEdgeScore(e) > score)
                {
                    to_remove = e;
                    score = bc.getEdgeScore(e);
                }
            
            graph.removeEdge(to_remove);
            clusterSet = wcSearch.transform(graph);
            
            double m=Modularity.calculateModularity(graph, clusterSet);
                        
            if((m <1) && (m>0) && (m > max_modularity))
            {
            			max_modularity=m;
            			bestSet=clusterSet;
            }        
        }
		
		return bestSet;		
	}	
}
