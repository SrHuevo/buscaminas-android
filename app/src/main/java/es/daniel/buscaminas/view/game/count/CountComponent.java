package es.daniel.buscaminas.view.game.count;

import android.app.Activity;
import android.widget.TextView;

import es.daniel.buscaminas.R;
import es.daniel.buscaminas.view.game.GameActivity;

public class CountComponent {
    private static int LESS = -1;
    private static int ADD = 1;

    GameActivity context;
    TextView restView;
    Integer restToWin = 0;

    public CountComponent(Activity context, int restToWinInit) {
        restView = (TextView) context.findViewById(R.id.rest);
        setRestToWin(restToWinInit);
    }

    public void lessCount() {
        setRestToWin(LESS);
    }

    public void addCount() {
        setRestToWin(ADD);
    }

    private void setRestToWin(int leesOrAdd) {
        restToWin = restToWin + leesOrAdd;
        restView.setText(restToWin.toString());
    }
}