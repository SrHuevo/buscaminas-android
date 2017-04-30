package es.daniel.buscaminas.logic;

import java.util.ArrayList;
import java.util.List;

import es.daniel.buscaminas.data.Box;


public class Table {
	private final int width;
	private final int height;
	private final int total;
	private final List<Box> boxes;
	private final List<List<Box>> boxesMatrix;
	
	public Table(int width, int height) {
		total = height * width;
		this.height = height;
		this.width = width;
		boxes = new ArrayList<>(total);
		boxesMatrix = new ArrayList<>(width);
		inicialice();
	}

	public void inicialice() {
		initBoxes();
		setNeighbor();
	}
	
	public Box getBox(int x, int y) {
		return boxesMatrix.get(x).get(y);
	}
	
	public void restar() {
		for(Box box : boxes) {
			box.setValue(0);
		}
	}
	
	private void initBoxes() {
		for(int x = 0; x < width; x++) {
			boxesMatrix.add(new ArrayList<Box>(height));
			for(int y = 0; y < height; y++) {
				Box box = new Box(x, y);
				boxes.add(box);
				boxesMatrix.get(x).add(box);
			}
		}
	}
	
	private void setNeighbor() {
		for(Box box : boxes) {
			int x = box.getX();
			int y = box.getY();
			for(int i = getStart(x, 0); i < getEnd(x, width); i++) {
				for(int j = getStart(y, 0); j < getEnd(y, height); j++) {
					if(boxesMatrix.get(i).get(j) != box) {
						box.addNeighbor(boxesMatrix.get(i).get(j));
					}
				}
			}
		}
	}
	
	private int getStart(int num, int first) {
		return Math.max(first, num - 1);
	}
		
	private int getEnd(int num, int last) {
		return Math.min(last, num + 2);
	}
	
	public int getTotal() {
		return total;
	}

	public List<Box> getBoxesAsList() {
		return boxes;
	}

	public List<Box> getBoxesAsCopyList() {
		return new ArrayList<>(boxes);
	}

	public List<List<Box>> getBoxesAsCopyMatrix() {
		return new ArrayList<>(boxesMatrix);
	}

	public int getWidth() {return width;}

	public int getHeight() {return height;}
}
