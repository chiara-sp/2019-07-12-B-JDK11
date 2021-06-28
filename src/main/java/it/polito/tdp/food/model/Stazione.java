package it.polito.tdp.food.model;

public class Stazione {

	private int id;
	private Food ciboInPreparazione;
	private boolean libera;
	
	public Stazione(int id) {
		super();
		this.id = id;
		this.ciboInPreparazione=null;
		libera=true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Food getCiboInPreparazione() {
		return ciboInPreparazione;
	}

	public void setCiboInPreparazione(Food ciboInPreparazione) {
		this.ciboInPreparazione = ciboInPreparazione;
	}

	public boolean isLibera() {
		return libera;
	}

	public void setLibera(boolean libera) {
		this.libera = libera;
	}
	
	
}
