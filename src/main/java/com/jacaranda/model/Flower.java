package com.jacaranda.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="FLOWER")
public class Flower {
	@Id
	private int code;
	private String name;
	private String description;
	private float price;
	@ManyToOne 
	@JoinColumn(name="color")
	private Color color;
	private int stock;
	@OneToMany(mappedBy= "flower", cascade= CascadeType.ALL, orphanRemoval = true)
	private List<Purchase> purchasesList = new ArrayList<>();
	
	public Flower() {
		
	}

	public Flower(String name, String description, float price, Color color, int stock) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.color = color;
		this.stock = stock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getCode() {
		return code;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flower other = (Flower) obj;
		return code == other.code;
	}

	@Override
	public String toString() {
		return code + "<br>" + name + "         " + color.getCode() + "<br>Descripción: " + description + "<br>" + price + " €";
	}

	

}
