package com.finalproject.ragil.finalproject.util;

import org.springframework.stereotype.Component;

@Component
public class Counter {

	private int counter;

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = this.counter+counter;
	}
	
	public void reset() {
		this.counter = 0;
	}
	
	
}
