package es.daniel.buscaminas.view.game.count;

import android.app.Activity;
import android.widget.TextView;

import es.daniel.buscaminas.R;
import es.daniel.buscaminas.logic.MineFinderGame;
import es.daniel.buscaminas.view.game.GameActivity;

public class CountComponent implements MineFinderGame.OnLessCountListener, MineFinderGame.OnAddCountListener {
    private static int LESS = -1;
    private static int ADD = 1;

    GameActivity context;
    TextView restView;
    Integer restToWin = 0;

    public CountComponent(Activity context, MineFinderGame game) {
        restView = (TextView) context.findViewById(R.id.rest);
        setRestToWin(game.getMinesTotal());
        game.addOnLessCountListeners(this);
        game.addOnLessCountListeners(this);
    }

    @Override
    public void onLessCount() {
        setRestToWin(LESS);
    }

    @Override
    public void onAddCount() {
        setRestToWin(ADD);
    }

    private void setRestToWin(int leesOrAdd) {
        restToWin = restToWin + leesOrAdd;
        restView.setText(restToWin.toString());
    }
}