package es.daniel.buscaminas.logic;

import es.daniel.buscaminas.data.Box;
import es.daniel.buscaminas.data.BoxState;
import es.daniel.buscaminas.data.GameState;

public interface Game {
	public void unboxing(int x, int y);
	public void tryUnboxingNeighbors(int x, int y);
	public void onListenerUnboxingBox(Box box);
	public GameState getState();
	public void gameWin();
	public void gameOver();
	public void restart();
	public void changeBlockBox(int x, int y);
	public void onChangeBlockBox(Box box);
	public int getMinesTotal();
	public void lessCount();
	public void addCount();
}
