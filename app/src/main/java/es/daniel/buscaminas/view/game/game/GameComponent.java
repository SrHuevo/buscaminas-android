package es.daniel.buscaminas.view.game.game;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import es.daniel.buscaminas.R;
import es.daniel.buscaminas.data.BoxView;
import es.daniel.buscaminas.data.BoxesViews;
import es.daniel.buscaminas.data.GameState;
import es.daniel.buscaminas.exception.CreateGameException;
import es.daniel.buscaminas.data.Box;
import es.daniel.buscaminas.logic.Game;
import es.daniel.buscaminas.logic.Table;

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

    public int getMinesTotal() { return game.getMinesTotal(); }

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
                ImageView boxView = new ImageView(context);
                boxView.setLayoutParams(layoutParams4Box);
                boxView.setBackgroundResource(R.mipmap.box_close);
                layoutRow.addView(boxView);
                boxesViews.add(i, j, new BoxView(boxView));
            }
        }
        return boxesViews;
    }

    private void createGame(final BoxesViews boxesViews) throws CreateGameException {
        table = new Table(nBoxWidth, nBoxHeight);
        game = new GameCoreView(this, table, nMines, boxesViews);
    }

    private void setFunctionality(BoxesViews boxesViews) {
        for(final Box box : table.getBoxesAsList()) {
            final BoxView boxView = boxesViews.get(box.getX(), box.getY());
            boxView.getImageView().setOnClickListener(new ClickBoxGame(box, game));
            boxView.getImageView().setOnLongClickListener(new LongClickBoxGame(box, game));
        }
    }

    public abstract void gameWin();

    public abstract void gameOver();

    public abstract void lessCount();

    public abstract void addCount();
}

