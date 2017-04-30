package es.daniel.buscaminas.view.game.game;

import android.view.View;

import es.daniel.buscaminas.data.Box;
import es.daniel.buscaminas.data.BoxState;
import es.daniel.buscaminas.logic.Game;

public class ClickBoxGame implements View.OnClickListener {

    Box box;
    Game game;

    ClickBoxGame(Box box, Game game) {
        this.box = box;
        this.game = game;
    }

    @Override
    public void onClick(View v) {
        if(box.getState() == BoxState.NO_USED.ordinal()) {
            game.unboxing(box.getX(), box.getY());
        } else {
            game.tryUnboxingNeighbors(box.getX(), box.getY());
        }
    }
}
