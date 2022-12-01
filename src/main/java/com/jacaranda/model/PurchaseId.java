package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

//import javax.persistence.Column;
//import javax.persistence.Embeddable;

//@Embeddable
public class PurchaseId implements Serializable{
	
//https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/

	private static final long serialVersionUID = 3556125921316204632L;
	
	//@Column(name="username")
	private String user;
	//@Column(name="flower_code")
	private int flower;
	//@Column(name="date")
	private LocalDate date;

	public PurchaseId() {
		
	}

	public String getUserId() {
		return user;
	}

	public void setUserId(String userId) {
		this.user = userId;
	}

	public int getFlowerId() {
		return flower;
	}

	public void setFlowerId(int flowerId) {
		this.flower = flowerId;
	}

	public LocalDate getDateId() {
		return date;
	}

	public void setDateId(LocalDate dateId) {
		this.date = dateId;
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
		PurchaseId other = (PurchaseId) obj;
		return Objects.equals(date, other.date) && flower == other.flower && Objects.equals(user, other.user);
	}
	

	

	



	
	

}
