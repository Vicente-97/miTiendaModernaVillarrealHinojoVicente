package com.jacaranda.model;

import java.util.HashMap;

public class Cart {
	//int itemCode int quantity;
	private HashMap<Integer, Integer> items;

	public Cart() {
		this.items = new HashMap <>();
	}

	public HashMap<Integer, Integer> getItems() {
		return items;
	}

	public void setItems(HashMap<Integer, Integer> items) {
		this.items = items;
	}
	

}
