package es.daniel.buscaminas.view.game;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.daniel.buscaminas.R;
import es.daniel.buscaminas.data.BoxView;
import es.daniel.buscaminas.data.BoxesViews;
import es.daniel.buscaminas.data.GameState;
import es.daniel.buscaminas.exception.CreateGameException;
import es.daniel.buscaminas.data.Box;
import es.daniel.buscaminas.data.BoxState;
import es.daniel.buscaminas.logic.Game;
import es.daniel.buscaminas.logic.MineFinderGame;
import es.daniel.buscaminas.data.Table;

public abstract class GameComponent {

    private static final int marginInDps = 2;
    private static final int minWidthBoxInDps = 38;

    private final int displayWidthInPx;
    private final float density;
    private final float minWidthBoxInPx;
    private final float marginInPx;

    private Activity context;
    private LinearLayout.LayoutParams layoutParams4Row;
    private LinearLayout layoutGame;
    private int widthBox;
    private LinearLayout.LayoutParams layoutParams4Box;
    private int nBoxWidth = 4;
    private int nBoxHeight = 4;
    private int nMines = 3;
    private Table table;
    private Game game;

    public GameComponent(Activity context, Integer nBoxWidth, Integer nBoxHeight, Integer nMines) {
        this.context = context;
        this.nBoxWidth = nBoxWidth;
        this.nBoxHeight = nBoxHeight;
        this.nMines = nMines;

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        displayWidthInPx =  displayMetrics.widthPixels;
        density = displayMetrics.density;
        marginInPx = marginInDps * density;
        minWidthBoxInPx = minWidthBoxInDps * density;

        initParams();

        BoxesViews boxesViews = paintGame();
        try {
            createGame(boxesViews);
            setFunctionality(boxesViews);
        } catch (CreateGameException e) {
            Log.e("GameActivity", "onCreate", e);
        }
    }

    public GameState getState() {
        return game.getState();
    }

    private void initParams() {
        layoutGame = (LinearLayout) context.findViewById(R.id.layout_game);

        layoutParams4Row = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        widthBox = calcWidth();

        layoutParams4Box = new LinearLayout.LayoutParams(widthBox, widthBox);
        layoutParams4Box.setMargins((int)marginInPx, (int)marginInPx, (int)marginInPx, (int)marginInPx);
    }

    private int calcWidth() {
        if(displayWidthInPx > (minWidthBoxInPx + marginInPx * 2) * nBoxWidth) {
            return (int)(displayWidthInPx / nBoxWidth - marginInPx * 2);
        } else {
            return (int)minWidthBoxInPx;
        }
    }

    private BoxesViews paintGame() {
        BoxesViews boxesViews = new BoxesViews(nBoxWidth, nBoxHeight);
        for(int j = 0; j < nBoxHeight; j++) {
            LinearLayout layoutRow = new LinearLayout(context);
            layoutRow.setOrientation(LinearLayout.HORIZONTAL);
            layoutRow.setLayoutParams(layoutParams4Row);
            layoutGame.addView(layoutRow);
            for(int i = 0; i < nBoxWidth; i++) {
                TextView boxView = new TextView(context);
                boxView.setLayoutParams(layoutParams4Box);
                boxView.setBackgroundResource(R.mipmap.box_close);
                boxView.setTextSize(TypedValue.COMPLEX_UNIT_PX, widthBox * 0.8f);
                boxView.setGravity(Gravity.CENTER);
                layoutRow.addView(boxView);
                boxesViews.add(i, j, new BoxView(boxView));
            }
        }
        return boxesViews;
    }

    private void createGame(final BoxesViews boxesViews) throws CreateGameException {
        table = new Table(nBoxWidth, nBoxHeight);


        game = new MineFinderGame(table, nMines){
            @Override
            public void gameWin() {
                GameComponent.this.gameWin();
            }

            @Override
            public void gameOver() {
                GameComponent.this.gameOver();
            }

            @Override
            public void onListenerUnboxingBox(Box box) {
                boxesViews.get(box.getX(), box.getY()).unboxing(box.getValue());
                GameComponent.this.restToWin(game.restToWin());
            }

            @Override
            public void onChangeBlockBox(Box box) {
                boxesViews.get(box.getX(), box.getY()).changeBlockBox(box.getState());
            }
        };
        for(int i = 0; i < nBoxHeight; i++) {
            for(int j = 0; j < nBoxWidth; j++) {
                int val = table.getBox(j, i).getValue();
                System.out.print(" " + (val == -1 ? val : " " + val));
            }
            System.out.println();
        }
    }

    private void setFunctionality(BoxesViews boxesViews) {
        for(final Box box : table.getBoxesAsList()) {
            final BoxView boxView = boxesViews.get(box.getX(), box.getY());
            boxView.getTextView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(box.getState() == BoxState.NO_USED.ordinal()) {
                        game.unboxing(box.getX(), box.getY());
                    }
                }
            });
            boxView.getTextView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(box.getState() != BoxState.USED.ordinal()) {
                        game.changeBlockBox(box.getX(), box.getY());
                    }
                    return true;
                }
            });
        }
    }

    public int getRestToWin() {
        return game.restToWin();
    }

    public abstract void gameWin();

    public abstract void gameOver();

    public abstract void restToWin(int howMany);
}

