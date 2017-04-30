package es.daniel.buscaminas.view.game.surrender;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import es.daniel.buscaminas.R;
import es.daniel.buscaminas.data.GameState;
import es.daniel.buscaminas.view.home.HomeActivity;

public class SurrenderComponent {

    final Activity context;
    final Button button;

    public SurrenderComponent(final Activity context) {
        this.context = context;

        button = (Button) context.findViewById(R.id.surrender);

        setOnClickListener();
    }

    private void setOnClickListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
                context.startActivity(new Intent(context, HomeActivity.class));
            }
        });
    }

    public void finish(GameState gameState) {
        switch (gameState) {
            case GAMEOVER:
                button.setBackgroundResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp);
                break;
            case WIN:
                button.setBackgroundResource(R.drawable.ic_sentiment_very_satisfied_black_24dp);
                break;
        }
    }

}
