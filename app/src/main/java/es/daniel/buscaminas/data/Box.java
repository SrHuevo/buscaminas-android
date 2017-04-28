package es.daniel.buscaminas.data;

import java.util.ArrayList;
import java.util.List;

public class Box {
	private int value = 0;
	private int state = 0;
	private int x;
	private int y;
	private List<Box> neighbors;
	
	public Box(int x, int y) {
		this.x = x;
		this.y = y;
		this.neighbors = new ArrayList<>();
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void addNeighbor(Box box) {
		neighbors.add(box);
	}
	
	public List<Box> getNeighbors() {
		return neighbors;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}