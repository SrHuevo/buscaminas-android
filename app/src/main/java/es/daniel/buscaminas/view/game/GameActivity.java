package es.daniel.buscaminas.view.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.daniel.buscaminas.R;
import es.daniel.buscaminas.logic.MineFinderGame;
import es.daniel.buscaminas.view.game.game.GameComponent;
import es.daniel.buscaminas.view.game.count.CountComponent;
import es.daniel.buscaminas.view.game.surrender.SurrenderComponent;
import es.daniel.buscaminas.view.game.timer.TimeComponent;
import es.daniel.buscaminas.view.home.HomeActivity;

public class GameActivity extends AppCompatActivity implements MineFinderGame.OnGameWinListener, MineFinderGame.OnGameOverListener {

    public static final String EXTRA_WIDTH = "0";
    public static final String EXTRA_HEIGHT = "1";
    public static final String EXTRA_MINES = "2";

    Activity context;
    MineFinderGame game;
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

        MineFinderGame game = initGameComponent(nBoxWidth, nBoxHeight, nMines);
        initRestToWinComponent(game);
        initTimeComponent(game);
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

    private void initTimeComponent(MineFinderGame game) {
        timeComponent = new TimeComponent(context);
    }

    private void initRestToWinComponent(MineFinderGame game) {
        restToWinComponent = new CountComponent(context, game);
    }

    private MineFinderGame initGameComponent(Integer nBoxWidth, Integer nBoxHeight, Integer nMines) {

        GameComponent gameComponent = new GameComponent(context, nBoxWidth, nBoxHeight, nMines);
        game = gameComponent.getGame();
        game.addOnGameWinListeners(this);
        game.addOnGameOverListeners(this);
        return game;
    }

    @Override
    public void onGameWin() {
        timeComponent.stop();
        surrenderComponent.finish(game.getState());
        AlertDialog.Builder builder = getBuilder();
        builder.setMessage("You win the game!!");
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onGameOver() {
        timeComponent.stop();
        surrenderComponent.finish(game.getState());
        AlertDialog.Builder builder = getBuilder();
        builder.setMessage("You lose the game!!");
        AlertDialog alert = builder.create();
        alert.show();
    }

    private AlertDialog.Builder getBuilder() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context.finish();
                        context.startActivity(new Intent(context, HomeActivity.class));
                    }
                });
        return builder;
    }

}