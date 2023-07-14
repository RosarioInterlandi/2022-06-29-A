package it.polito.tdp.itunes.model;

import java.util.Objects;

public class EdgeModel {
	private Album a1;
	private Album a2;
	Integer peso;
	public EdgeModel(Album a1, Album a2, Integer peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	public Album getA1() {
		return a1;
	}

	public Album getA2() {
		return a2;
	}

	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "EdgeModel [a1=" + a1 + ", a2=" + a2 + ", peso=" + peso + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(a1, a2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EdgeModel other = (EdgeModel) obj;
		return Objects.equals(a1, other.a1) && Objects.equals(a2, other.a2);
	}
	
	
}
