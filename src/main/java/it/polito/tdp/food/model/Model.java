package it.polito.tdp.food.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	FoodDao dao;
	Map<Integer,Food> cibi;
	SimpleDirectedWeightedGraph<Food,DefaultWeightedEdge> grafo;
	List<Food> vertici;
	Simulatore sim;
	public Model() {
		dao= new FoodDao();
		cibi= new HashMap<>();
		dao.listAllFoods(cibi);
		vertici= new LinkedList<>();
		
	}
	public void creaGrafo(int porzioni) {
		
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		vertici= dao.getVertici(cibi, porzioni);
		Graphs.addAllVertices(grafo, vertici);
		
		for(Adiacenza a : dao.getArchi(cibi)) {
			if(grafo.vertexSet().contains(a.getF1())&& grafo.vertexSet().contains(a.getF2())) {
				Graphs.addEdge(grafo, a.getF1(), a.getF2(), a.getPeso());
			}
		}
				
	}
	public int numVertici() {
		// TODO Auto-generated method stub
		return this.grafo.vertexSet().size();
	}

	public int numArchi() {
		// TODO Auto-generated method stub
		return this.grafo.edgeSet().size();
	}
	public List<Food> getVertici() {
		Collections.sort(vertici);
		return vertici;
	}
	public List<Vicino> getVicini(Food f){
		if(grafo==null) {
			return null;
		}
		List<Vicino> result= new LinkedList<>();
		for(DefaultWeightedEdge edge: grafo.outgoingEdgesOf(f)) {
			double peso= grafo.getEdgeWeight(edge);
			Food arrivo= Graphs.getOppositeVertex(grafo, edge, f);
			result.add(new Vicino(arrivo,peso));
		}
		Collections.sort(result);
		return result;
	}
	public List<Food> simula(int K, Food partenza) {
		if(grafo==null)
			return null;
		sim= new Simulatore(this.grafo,K, partenza,this);
		sim.run();
		List<Food> prep= sim.preparati;
		return prep;
		
	}
	
	public long durata() {
		if(sim!=null)
			return sim.durata;
		return 0;
	}
}
