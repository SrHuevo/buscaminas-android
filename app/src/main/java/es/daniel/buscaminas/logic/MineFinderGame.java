package es.daniel.buscaminas.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.daniel.buscaminas.data.Box;
import es.daniel.buscaminas.data.BoxState;
import es.daniel.buscaminas.data.BoxType;
import es.daniel.buscaminas.data.GameState;
import es.daniel.buscaminas.exception.CreateGameException;

public class MineFinderGame {

	private List<OnLessCountListener> onLessCountListeners = new ArrayList<>();
	private List<OnAddCountListener> onAddCountListeners = new ArrayList<>();
	private List<OnGameWinListener> onGameWinListeners = new ArrayList<>();
	private List<OnGameOverListener> onGameOverListeners = new ArrayList<>();
	private List<OnChangeBlockBoxListener> onChangeBlockBoxListeners = new ArrayList<>();
	private List<OnUnboxingListener> onUnboxingListeners = new ArrayList<>();

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

	public void changeBlockBox(int x, int y)  {
		Box box = table.getBox(x, y);
		BoxState boxState;
		switch (BoxState.fromOrdinal(box.getState())) {
			case NO_USED:
				boxState = BoxState.BLOCK;
				callOnLessCountListeners();
				break;
			case BLOCK:
				boxState = BoxState.QUESTION;
				callOnAddCountListeners();
				break;
			case QUESTION:
				boxState = BoxState.NO_USED;
				break;
			default:
				boxState = BoxState.fromOrdinal(box.getState());
		}
		box.setState(boxState.ordinal());
		callOnChangeBlockBoxListeners(box);
	}
	
	public void unboxing(int x, int y) {
		Box box = table.getBox(x, y);
		addUnboxing(box);
		if(box.getValue() == BoxType.MINE) {
			gameState = GameState.GAMEOVER;
			callOnGameOverListeners();
		} else if(box.getValue() == BoxType.VOID) {
			unboxingPieceFromVoidBox(box);
		}
	}

	public void tryUnboxingNeighbors(int x, int y) {
		Box box = table.getBox(x, y);
		int valueAux = box.getValue();
		for(Box neighbor : box.getNeighbors()) {
			if(BoxState.fromOrdinal(neighbor.getState()) == BoxState.BLOCK) {
				valueAux--;
			}
			if(valueAux == 0) {
				break;
			}
		}
		if(valueAux == 0) {
			unboxingNeighborsNoBlock(box);
		}
	}

	private void unboxingNeighborsNoBlock(Box box) {
		for(Box neighbor : box.getNeighbors()) {
			if(BoxState.fromOrdinal(neighbor.getState()) == BoxState.NO_USED ||
					BoxState.fromOrdinal(neighbor.getState()) == BoxState.QUESTION) {
				addUnboxing(neighbor);
				if(neighbor.getValue() == BoxType.MINE) {
					callOnGameOverListeners();
				}
				if(neighbor.getValue() == BoxType.VOID) {
					unboxingPieceFromVoidBox(neighbor);
				}
			}
		}
	}
	
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
		callOnUnboxingListener(box);
		if(restToWin() == 0) {
			gameState = GameState.WIN;
			callOnGameWinListeners();
		}
	}

	public int getMinesTotal() {
		return mines;
	}

	private int restToWin() {
		return table.getTotal() - unboxingBoxes - mines;
	}

	public void restart() {
		List<Box> boxesWithMines = setMines();
		setValues(boxesWithMines);
	}

	public void addOnLessCountListeners(OnLessCountListener onLessCountListener) {
		onLessCountListeners.add(onLessCountListener);
	}

	public void addOnAddCountListeners(OnAddCountListener onAddCountListener) {
		onAddCountListeners.add(onAddCountListener);
	}

	public void addOnGameWinListeners(OnGameWinListener onGameWinListener) {
		onGameWinListeners.add(onGameWinListener);
	}

	public void addOnGameOverListeners(OnGameOverListener onGameOverListener) {
		onGameOverListeners.add(onGameOverListener);
	}

	public void addOnChangeBlockBoxListeners(OnChangeBlockBoxListener onChangeBlockBoxListener) {
		onChangeBlockBoxListeners.add(onChangeBlockBoxListener);
	}

	public void addOnUnboxingListener(OnUnboxingListener onUnboxingListener) {
		onUnboxingListeners.add(onUnboxingListener);
	}

	private void callOnLessCountListeners() {
		for(OnLessCountListener onLessCountListener : onLessCountListeners) {
			onLessCountListener.onLessCount();
		}
	}

	private void callOnAddCountListeners() {
		for(OnAddCountListener onAddCountListener : onAddCountListeners) {
			onAddCountListener.onAddCount();
		}
	}

	private void callOnGameWinListeners() {
		for(OnGameWinListener onGameWinListener : onGameWinListeners) {
			onGameWinListener.onGameWin();
		}
	}

	private void callOnGameOverListeners() {
		for(OnGameOverListener onGameOverListener : onGameOverListeners) {
			onGameOverListener.onGameOver();
		}
	}

	private void callOnChangeBlockBoxListeners(Box box) {
		for(OnChangeBlockBoxListener onChangeBlockBoxListener : onChangeBlockBoxListeners) {
			onChangeBlockBoxListener.onChangeBlockBox(box);
		}
	}

	private void callOnUnboxingListener(Box box) {
		for(OnUnboxingListener onUnboxingListener : onUnboxingListeners) {
			onUnboxingListener.onUnboxing(box);
		}
	}

	public interface OnLessCountListener {
		void onLessCount();
	}

	public interface OnAddCountListener {
		void onAddCount();
	}

	public interface OnGameWinListener {
		void onGameWin();
	}

	public interface OnGameOverListener {
		void onGameOver();
	}

	public interface OnChangeBlockBoxListener {
		void onChangeBlockBox(Box box);
	}

	public interface OnUnboxingListener {
		void onUnboxing(Box box);
	}
}