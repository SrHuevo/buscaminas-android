package es.daniel.buscaminas.data;

import android.graphics.Color;
import android.widget.TextView;

import es.daniel.buscaminas.R;

public class BoxView {
    private TextView textView;

    public BoxView(TextView textView) {
        this.textView = textView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void unboxing(Integer value) {
        switch (value) {
            case BoxType.MINE:
                textView.setBackgroundResource(R.mipmap.box_mine);
                return;
            case 1:
                textView.setTextColor(Color.RED);
                break;
            case 2:
                textView.setTextColor(Color.YELLOW);
                break;
            case 3:
                textView.setTextColor(Color.BLUE);
                break;
            case 4:
                textView.setTextColor(Color.GREEN);
                break;
            case 5:
                textView.setTextColor(Color.BLACK);
                break;
            case 6:
                textView.setTextColor(Color.CYAN);
                break;
            case 7:
                textView.setTextColor(Color.MAGENTA);
                break;
            case 8:
                textView.setTextColor(Color.DKGRAY);
                break;
        }
        textView.setBackgroundResource(R.mipmap.box_open);
        if(value != BoxType.VOID) {
            textView.setText(value.toString());
        }
    }

    public void changeBlockBox(Integer state) {
        switch (BoxState.fromOrdinal(state)) {
            case BLOCK :
                textView.setBackgroundResource(R.mipmap.box_mark);
                break;
            case NO_USED :
                textView.setBackgroundResource(R.mipmap.box_close);
                break;
        }
    }
}
