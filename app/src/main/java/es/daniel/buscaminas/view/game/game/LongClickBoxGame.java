package es.daniel.buscaminas.view.game.game;

import android.view.View;

import es.daniel.buscaminas.data.Box;
import es.daniel.buscaminas.data.BoxState;
import es.daniel.buscaminas.logic.MineFinderGame;

public class LongClickBoxGame implements View.OnLongClickListener {

    Box box;
    MineFinderGame game;

    LongClickBoxGame(Box box, MineFinderGame game) {
        this.box = box;
        this.game = game;
    }

    @Override
    public boolean onLongClick(View v) {
        if(box.getState() != BoxState.USED.ordinal()) {
            game.changeBlockBox(box.getX(), box.getY());
        }
        return true;
    }
}
