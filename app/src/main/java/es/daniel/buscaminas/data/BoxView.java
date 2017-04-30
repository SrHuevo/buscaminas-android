package es.daniel.buscaminas.data;

import android.widget.ImageView;

import es.daniel.buscaminas.R;

public class BoxView {
    private ImageView imageView;

    public BoxView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void unboxing(Integer value) {
        switch (value) {
            case BoxType.MINE:
                imageView.setBackgroundResource(R.mipmap.box_mine);
                break;
            case 1:
                imageView.setBackgroundResource(R.mipmap.box_open_1);
                break;
            case 2:
                imageView.setBackgroundResource(R.mipmap.box_open_2);
                break;
            case 3:
                imageView.setBackgroundResource(R.mipmap.box_open_3);
                break;
            case 4:
                imageView.setBackgroundResource(R.mipmap.box_open_4);
                break;
            case 5:
                imageView.setBackgroundResource(R.mipmap.box_open_5);
                break;
            case 6:
                imageView.setBackgroundResource(R.mipmap.box_open_6);
                break;
            case 7:
                imageView.setBackgroundResource(R.mipmap.box_open_7);
                break;
            case 8:
                imageView.setBackgroundResource(R.mipmap.box_open_8);
                break;
            case BoxType.VOID:
                imageView.setBackgroundResource(R.mipmap.box_open);
                break;
        }
    }

    public void changeBlockBox(Integer state) {
        switch (BoxState.fromOrdinal(state)) {
            case BLOCK :
                imageView.setBackgroundResource(R.mipmap.box_mark);
                break;
            case NO_USED :
                imageView.setBackgroundResource(R.mipmap.box_close);
                break;
            case QUESTION :
                imageView.setBackgroundResource(R.mipmap.box_question);
                break;
        }
    }
}
