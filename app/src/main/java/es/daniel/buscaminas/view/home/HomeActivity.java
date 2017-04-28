package es.daniel.buscaminas.view.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import es.daniel.buscaminas.R;
import es.daniel.buscaminas.view.game.GameActivity;

public class HomeActivity extends AppCompatActivity {

    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;

        final EditText width = (EditText) findViewById(R.id.input_width);
        final EditText height = (EditText) findViewById(R.id.input_height);
        final EditText mines = (EditText) findViewById(R.id.input_mines);

        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GameActivity.class);
                intent.putExtra(GameActivity.EXTRA_WIDTH, Integer.valueOf(width.getText().toString()));
                intent.putExtra(GameActivity.EXTRA_HEIGHT, Integer.valueOf(height.getText().toString()));
                intent.putExtra(GameActivity.EXTRA_MINES, Integer.valueOf(mines.getText().toString()));
                startActivity(intent);
            }
        });
    }

}
