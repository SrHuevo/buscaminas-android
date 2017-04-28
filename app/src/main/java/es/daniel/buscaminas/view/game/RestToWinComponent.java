package es.daniel.buscaminas.view.game;

import android.app.Activity;
import android.widget.TextView;

import es.daniel.buscaminas.R;

public class RestToWinComponent {
    GameActivity context;
    TextView restView;

    public RestToWinComponent(Activity context, int restToWinInit) {
        restView = (TextView) context.findViewById(R.id.rest);
        setRestToWin(restToWinInit);
    }

    public void setRestToWin(int hoyMany) {
        restView.setText(Integer.toString(hoyMany));
    }
}
