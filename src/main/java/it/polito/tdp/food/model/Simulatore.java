package it.polito.tdp.food.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.food.model.Evento.EventType;

public class Simulatore {
	
	//coda degli eventi
	PriorityQueue<Evento> queue;
	
	//modello del mondo 
	Graph<Food,DefaultWeightedEdge> grafo;
	Model model;
	List<Stazione> stazioni;
	Double start;
	
	//dati di input
	int K;
	Food partenza;
	
	//dati output 
	long durata;
	List<Food> preparati;

	public Simulatore(SimpleDirectedWeightedGraph<Food,DefaultWeightedEdge> grafo, int K, Food partenza, Model model) {
		this.model=model;
		this.start=0.0;
		queue= new PriorityQueue<>();
		this.grafo=grafo;
		this.K=K;
		preparati= new LinkedList<>();
		
		this.partenza=partenza;
		stazioni= new LinkedList<>();
		for(int i=0; i<K; i++) {
			stazioni.add(new Stazione(i));
		}
		queue.add(new Evento(EventType.inizio_prepazione,start,stazioni.get(0)));
		stazioni.get(0).setCiboInPreparazione(partenza);
		stazioni.get(0).setLibera(false);
		System.out.println("cibo "+partenza.toString()+" in preprazione");
		//queue.add(new Evento(EventType.fine_preparazione,start.plusHours((long) model.getVicini(partenza).get(0).getPeso()),stazioni.get(0)));
		
		
		for(int i=1;i<K; i++) {
			
			Food prox=model.getVicini(partenza).get(i-1).getFood();
			if(prox!=null) {
				queue.add(new Evento(EventType.inizio_prepazione,start,stazioni.get(i)));
			stazioni.get(i).setCiboInPreparazione(prox);
			stazioni.get(i).setLibera(false);
			System.out.println("cibo "+model.getVicini(partenza).get(i-1).getFood().toString()+" in preprazione");
			}
			//queue.add(new Evento(EventType.fine_preparazione,start.plusHours((long) model.getVicini(stazioni.get(i).getCiboInPreparazione()).get(0).getPeso()),stazioni.get(0)));
		}
		
	}
	
	public void run() {
		Evento e= queue.poll();
		Food cibo= e.getStazione().getCiboInPreparazione();
		Stazione stazione= e.getStazione();
	
		while(e!=null) {
			switch (e.getTipo()) {
				case inizio_prepazione:
					Food prossimo= model.getVicini(cibo).get(0).getFood();
					queue.add(new Evento(EventType.fine_preparazione,e.getTempo()+model.getVicini(cibo).get(0).getPeso(),stazione));
					stazione.setLibera(false);
					stazione.setCiboInPreparazione(prossimo);
					
					System.out.println("cibo "+ prossimo.toString()+" in preprazione");
					break;
					
				case fine_preparazione:
					stazione.setLibera(true);
					preparati.add(cibo);
					durata+= model.getVicini(cibo).get(0).getPeso();
					System.out.println("cibo "+cibo.toString()+" preparato");
					Food prossi= model.getVicini(cibo).get(0).getFood();
					if(!preparati.contains(prossi) && prossi!=null) {
						for(Stazione s: stazioni) {
							if(s.isLibera()) {
								queue.add(new Evento(EventType.inizio_prepazione,e.getTempo(), s));
								
								break;
							}
						}
					}
					else {
						for(int i=1; i<model.getVicini(cibo).size(); i++) {
							Food pros= model.getVicini(cibo).get(i).getFood();
							if(!preparati.contains(pros)) {
								for(Stazione s: stazioni) {
									if(s.isLibera()) {
										queue.add(new Evento(EventType.inizio_prepazione,e.getTempo(), s));
										
										break;
									}
								}
						}
					}
					}
			}
		}
	}
	public List<Food> preparati(){
		return preparati;
	}
	public long durata() {
		return durata;
	}
}
