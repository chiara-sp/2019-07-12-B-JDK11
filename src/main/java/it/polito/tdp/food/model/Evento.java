package it.polito.tdp.food.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento>{

	public enum EventType{
		inizio_prepazione,
		fine_preparazione,
	}
	
	private EventType tipo;
	private Double tempo;
	private Stazione stazione;
	public Evento(EventType tipo, double tempo, Stazione stazione) {
		super();
		this.tipo = tipo;
		this.tempo = tempo;
		this.stazione = stazione;
	}
	public EventType getTipo() {
		return tipo;
	}
	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}
	public double getTempo() {
		return tempo;
	}
	public void setTempo(double tempo) {
		this.tempo = tempo;
	}
	public Stazione getStazione() {
		return stazione;
	}
	public void setStazione(Stazione stazione) {
		this.stazione = stazione;
	}
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.tempo.compareTo(o.tempo);
	}
	
	
}
