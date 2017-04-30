package es.daniel.buscaminas.view.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.daniel.buscaminas.R;
import es.daniel.buscaminas.view.game.game.GameComponent;
import es.daniel.buscaminas.view.game.count.CountComponent;
import es.daniel.buscaminas.view.game.surrender.SurrenderComponent;
import es.daniel.buscaminas.view.game.timer.TimeComponent;
import es.daniel.buscaminas.view.home.HomeActivity;

public class GameActivity extends AppCompatActivity {

    public static final String EXTRA_WIDTH = "0";
    public static final String EXTRA_HEIGHT = "1";
    public static final String EXTRA_MINES = "2";

    Activity context;
    GameComponent gameComponent;
    CountComponent restToWinComponent;
    TimeComponent timeComponent;
    SurrenderComponent surrenderComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        context.setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Integer nBoxWidth = intent.getIntExtra(EXTRA_WIDTH, 10);
        Integer nBoxHeight = intent.getIntExtra(EXTRA_HEIGHT, 10);
        Integer nMines = intent.getIntExtra(EXTRA_MINES, 10);

        initGameComponent(nBoxWidth, nBoxHeight, nMines);
        initRestToWinComponent();
        initTimeComponent();
        initSurrenderComponent();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timeComponent.stop();
    }

    private void initSurrenderComponent() {
        surrenderComponent = new SurrenderComponent(context);
    }

    private void initTimeComponent() {
        timeComponent = new TimeComponent(context);
    }

    private void initRestToWinComponent() {
        restToWinComponent = new CountComponent(context, gameComponent.getMinesTotal());
    }

    private void initGameComponent(Integer nBoxWidth, Integer nBoxHeight, Integer nMines) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context.finish();
                        context.startActivity(new Intent(context, HomeActivity.class));
                    }
                });
        gameComponent = new GameComponent(context, nBoxWidth, nBoxHeight, nMines){
                @Override
                public void gameWin() {
                    timeComponent.stop();
                    surrenderComponent.finish(gameComponent.getState());
                    builder.setMessage("You win the game!!");
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                @Override
                public void gameOver() {
                    timeComponent.stop();
                    surrenderComponent.finish(gameComponent.getState());
                    builder.setMessage("You lose the game!!");
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                @Override
                public void lessCount() {
                    restToWinComponent.lessCount();
                }

                @Override
                public void addCount() {
                    restToWinComponent.addCount();
                }
        };
    }
}