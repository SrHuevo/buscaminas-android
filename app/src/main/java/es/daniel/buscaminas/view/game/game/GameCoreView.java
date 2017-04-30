package es.daniel.buscaminas.view.game.game;
import es.daniel.buscaminas.data.Box;
import es.daniel.buscaminas.data.BoxesViews;
import es.daniel.buscaminas.logic.Table;
import es.daniel.buscaminas.exception.CreateGameException;
import es.daniel.buscaminas.logic.MineFinderGame;

public class GameCoreView extends MineFinderGame {

    GameComponent gameComponent;
    BoxesViews boxesViews;

    public GameCoreView(
            GameComponent gameComponent,
            Table table,
            int nMines,
            BoxesViews boxesViews
    ) throws CreateGameException {
        super(table, nMines);
        this.gameComponent = gameComponent;
        this.boxesViews = boxesViews;
    }

    @Override
    public void gameWin() {
        gameComponent.gameWin();
    }

    @Override
    public void gameOver() {
        gameComponent.gameOver();
    }

    @Override
    public void onListenerUnboxingBox(Box box) {
        boxesViews.get(box.getX(), box.getY()).unboxing(box.getValue());
    }

    @Override
    public void onChangeBlockBox(Box box) {
        boxesViews.get(box.getX(), box.getY()).changeBlockBox(box.getState());
    }

    @Override
    public void lessCount() {
        gameComponent.lessCount();
    }

    @Override
    public void addCount() {
        gameComponent.addCount();
    }
}
