package es.daniel.buscaminas.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.daniel.buscaminas.data.Box;
import es.daniel.buscaminas.data.BoxState;
import es.daniel.buscaminas.data.BoxType;
import es.daniel.buscaminas.data.GameState;
import es.daniel.buscaminas.data.Table;
import es.daniel.buscaminas.exception.CreateGameException;

public abstract class MineFinderGame implements Game{
	private int unboxingBoxes = 0;
	private final int mines;
	private GameState gameState = GameState.PLAYING;
	
	private Table table;
	
	public MineFinderGame(Table table, int mines) throws CreateGameException {
		if(table.getTotal() < mines) {
			throw new CreateGameException("There are fewer boxes available from the mines you have established");
		}
		this.mines = mines;
		this.table = table;
		List<Box> boxesWithMines = setMines();
		setValues(boxesWithMines);
	}

	@Override
	public void changeBlockBox(int x, int y)  {
		Box box = table.getBox(x, y);
		BoxState boxState;
		if(box.getState() == BoxState.BLOCK.ordinal()) {
			boxState = BoxState.NO_USED;
		} else {
			boxState = BoxState.BLOCK;
		}
		box.setState(boxState.ordinal());
		onChangeBlockBox(box);
	}
	
	@Override
	public void unboxing(int x, int y) {
		Box box = table.getBox(x, y);
		addUnboxing(box);
		if(box.getValue() == BoxType.MINE) {
			gameState = GameState.GAMEOVER;
			gameOver();
		} else if(box.getValue() == BoxType.VOID) {
			unboxingPieceFromVoidBox(box);
		}
	}
	
	@Override
	public GameState getState() {
		return gameState;
	}
	
	private List<Box> setMines() {
		List<Box> boxesWithMines = new ArrayList<>();
		List<Box> boxes = table.getBoxesAsCopyList();
		int totalAux = table.getTotal();
		for(int i = 0; i < mines; i++) {
			Random r = new Random();
			int position = r.nextInt(totalAux);
			Box box = table.getBox(boxes.get(position).getX(), boxes.get(position).getY());
			box.setValue(BoxType.MINE);
			boxes.remove(position);
			totalAux--;
			boxesWithMines.add(box);
		}
		return boxesWithMines;
	}
	
	private void setValues(List<Box> boxesWithMines) {
		for(Box box : boxesWithMines) {
			for(Box neighbor : box.getNeighbors()) {
				if(neighbor.getValue() != BoxType.MINE) {
					neighbor.setValue(neighbor.getValue() + 1);
				}
			}
		}
	}

	private void unboxingPieceFromVoidBox(Box box) {
		for(Box neighbor : box.getNeighbors()) {
			if(neighbor.getState() == BoxState.NO_USED.ordinal()){
				addUnboxing(neighbor);
				if(neighbor.getValue() == BoxType.VOID) {
					unboxingPieceFromVoidBox(neighbor);
				}
			}
		}
	}
	
	private void addUnboxing(Box box) {
		box.setState(BoxState.USED.ordinal());
		unboxingBoxes++;
		onListenerUnboxingBox(box);
		if(restToWin() == 0) {
			gameState = GameState.WIN;
			gameWin();
		}
	}

	@Override
	public int restToWin() {
		return table.getTotal() - unboxingBoxes - mines;
	}
	
	@Override
	public void restart() {
		List<Box> boxesWithMines = setMines();
		setValues(boxesWithMines);
	}
}