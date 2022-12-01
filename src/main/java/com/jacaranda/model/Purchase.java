package com.jacaranda.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PURCHASE")
@IdClass(PurchaseId.class)
public class Purchase {
	
//	@EmbeddedId
//	private PurchaseId id;
	
	@Id
	@ManyToOne
	@JoinColumn(
			name="username",
			insertable=false, updatable = false
			)
//	@ManyToOne
//	@MapsId("userId")
	private User user;
	@Id
	@ManyToOne
	@JoinColumn(
			name="flower_code",
			insertable=false, updatable = false
			)
//	@ManyToOne
//	@MapsId("flowerId")
	private Flower flower;
	private int quantity;
	private float price;
	@Id
//	@ManyToOne
//	@JoinColumn(
//			name="date",
//			insertable=false, updatable = false
//			)
	
//	@MapsId("dateId")
	@Column(name="date")
	private LocalDate date;
	

	public Purchase() {
		
	}

	public Purchase(User user, Flower flower, LocalDate date, int quantity, float price) {
		this.user = user;
		this.flower = flower;
		this.date = date;
		this.quantity = quantity;
		this.price = price;
	}

	public User getUsername() {
		return user;
	}

	public void setUsername(User user) {
		this.user = user;
	}

	public Flower getFlower() {
		return flower;
	}

	public void setFlower(Flower flower) {
		this.flower = flower;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, flower, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Purchase other = (Purchase) obj;
		return Objects.equals(date, other.date) && Objects.equals(flower, other.flower)
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Artículo: " + flower + "<br> Fecha: " + date + "<br> Cantidad: " + quantity
				+ "precio: " + price;
	}
	

}
