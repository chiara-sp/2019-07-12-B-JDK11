package it.polito.tdp.food.model;

public class Vicino implements Comparable<Vicino>{

	private Food food;
	private Double peso;
	public Vicino(Food food, double peso) {
		super();
		this.food = food;
		this.peso = peso;
	}
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Vicino o) {
		// TODO Auto-generated method stub
		return this.peso.compareTo(o.peso);
	}
	
	
}
