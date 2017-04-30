package es.daniel.buscaminas.view.home;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import es.daniel.buscaminas.R;
import es.daniel.buscaminas.view.game.GameActivity;

public class StartNewGame implements View.OnClickListener {

    Activity activity;
    EditText editTextWidth;
    EditText editTextHeight;
    EditText editTextMines;


    public StartNewGame(Activity activity) {
        this.activity = activity;
        editTextWidth = (EditText) activity.findViewById(R.id.input_width);
        editTextHeight = (EditText) activity.findViewById(R.id.input_height);
        editTextMines = (EditText) activity.findViewById(R.id.input_mines);
    }

    private boolean validateParams() {
        boolean isValid = true;
        Integer width = toInt(editTextWidth);
        Integer height = toInt(editTextHeight);
        Integer mines = toInt(editTextMines);
        if(width < 10) {
            editTextWidth.setError(activity.getResources().getString(R.string.error_too_low));
            isValid = false;
        }
        if(height < 10) {
            editTextHeight.setError(activity.getResources().getString(R.string.error_too_low));
            isValid = false;
        }
        if(mines < 10) {
            editTextMines.setError(activity.getResources().getString(R.string.error_too_low));
            isValid = false;
        }
        if(width * height * 0.4 < mines) {
            editTextMines.setError(activity.getResources().getString(R.string.error_too_mines));
            isValid = false;
        }
        return isValid;
    }

    private int toInt(EditText editText) {
        String value = editText.getText().toString();
        return Integer.valueOf(value);
    }

    @Override
    public void onClick(View v) {
        if(!validateParams()) {
            return;
        }
        Intent intent = new Intent(v.getContext(), GameActivity.class);
        intent.putExtra(GameActivity.EXTRA_WIDTH, toInt(editTextWidth));
        intent.putExtra(GameActivity.EXTRA_HEIGHT, toInt(editTextHeight));
        intent.putExtra(GameActivity.EXTRA_MINES, toInt(editTextMines));
        v.getContext().startActivity(intent);
    }
}
