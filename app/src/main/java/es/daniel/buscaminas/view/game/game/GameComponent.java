package es.daniel.buscaminas.view.game.game;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import es.daniel.buscaminas.R;
import es.daniel.buscaminas.data.BoxView;
import es.daniel.buscaminas.data.BoxesViews;
import es.daniel.buscaminas.data.GameState;
import es.daniel.buscaminas.exception.CreateGameException;
import es.daniel.buscaminas.data.Box;
import es.daniel.buscaminas.lib.ViewHelper;
import es.daniel.buscaminas.logic.MineFinderGame;
import es.daniel.buscaminas.logic.Table;

public class GameComponent implements MineFinderGame.OnUnboxingListener, MineFinderGame.OnChangeBlockBoxListener {

    private static final int marginInDps = 2;
    private static final int minBoxInDps = 38;

    private Activity context;
    private int nBoxWidth;
    private int nBoxHeight;
    private int nMines;
    private int widthBoxPxs;
    private int marginBoxPxs;
    private Table table;
    private MineFinderGame game;
    BoxesViews boxesViews;

    public GameComponent(Activity context, Integer nBoxWidth, Integer nBoxHeight, Integer nMines) {
        this.context = context;
        this.nBoxWidth = nBoxWidth;
        this.nBoxHeight = nBoxHeight;
        this.nMines = nMines;

        ViewHelper viewHelper = new ViewHelper(context.getResources().getDisplayMetrics());
        marginBoxPxs = viewHelper.dpsToPx(marginInDps);
        widthBoxPxs = viewHelper.calcWidth(
                viewHelper.dpsToPx(minBoxInDps), marginBoxPxs, nBoxWidth);

        LinearLayout layoutGame = (LinearLayout) context.findViewById(R.id.layout_game);
        boxesViews = paintGame(layoutGame);
        try {
            createGame();
            setFunctionality();
        } catch (CreateGameException e) {
            Log.e("GameActivity", "onCreate", e);
        }
    }

    public GameState getState() {
        return game.getState();
    }

    public int getMinesTotal() { return game.getMinesTotal(); }

    public MineFinderGame getGame() {
        return game;
    }

    private BoxesViews paintGame(LinearLayout layoutGame) {
        BoxesViews boxesViews = new BoxesViews(nBoxWidth, nBoxHeight);
        for(int j = 0; j < nBoxHeight; j++) {
            LinearLayout layoutRow = newLayoutRow();
            layoutGame.addView(layoutRow);
            for(int i = 0; i < nBoxWidth; i++) {
                ImageView boxView = newBoxView();
                layoutRow.addView(boxView);
                boxesViews.add(i, j, new BoxView(boxView));
            }
        }
        return boxesViews;
    }

    private LinearLayout newLayoutRow() {
        LinearLayout layoutRow = new LinearLayout(context);
        layoutRow.setOrientation(LinearLayout.HORIZONTAL);
        layoutRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return layoutRow;
    }

    private ImageView newBoxView() {
        ImageView boxView = new ImageView(context);
        boxView.setLayoutParams(getLayoutParams4Box());
        boxView.setBackgroundResource(R.mipmap.box_close);
        return boxView;
    }

    private LinearLayout.LayoutParams getLayoutParams4Box() {
        LinearLayout.LayoutParams layoutParams4Box = new LinearLayout.LayoutParams(widthBoxPxs, widthBoxPxs);
        layoutParams4Box.setMargins(marginBoxPxs, marginBoxPxs, marginBoxPxs, marginBoxPxs);
        return layoutParams4Box;
    }

    private void createGame() throws CreateGameException {
        table = new Table(nBoxWidth, nBoxHeight);
        game = new MineFinderGame(table, nMines);
        game.addOnUnboxingListener(this);
        game.addOnChangeBlockBoxListeners(this);
    }

    private void setFunctionality() {
        for(final Box box : table.getBoxesAsList()) {
            final BoxView boxView = boxesViews.get(box.getX(), box.getY());
            boxView.getImageView().setOnClickListener(new ClickBoxGame(box, game));
            boxView.getImageView().setOnLongClickListener(new LongClickBoxGame(box, game));
        }
    }

    @Override
    public void onChangeBlockBox(Box box) {
        boxesViews.get(box.getX(), box.getY()).changeBlockBox(box.getState());
    }

    @Override
    public void onUnboxing(Box box) {
        boxesViews.get(box.getX(), box.getY()).unboxing(box.getValue());
    }

}

