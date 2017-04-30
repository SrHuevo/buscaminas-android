package es.daniel.buscaminas.view.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import es.daniel.buscaminas.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new StartNewGame(this));
    }

}
