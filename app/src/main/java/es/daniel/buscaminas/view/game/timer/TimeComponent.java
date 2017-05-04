package es.daniel.buscaminas.view.game.timer;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import java.util.Date;

import es.daniel.buscaminas.R;

public class TimeComponent {

    final Activity context;
    final TextView textView;
    final AsyncTask<String, String, String> timer;

    public TimeComponent(final Activity context) {
        this.context = context;

        textView = (TextView) context.findViewById(R.id.timer);

        timer = buildTimer();
        timer.execute();
    }

    public void stop() {
        timer.cancel(false);
    }

    private AsyncTask<String, String, String> buildTimer() {
        return new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground (String...params){
                    long start = new Date().getTime();
                    for (; ; ) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        long actual = new Date().getTime();
                        publishProgress(getTime(actual, start));
                        if (isCancelled()) {
                            break;
                        }
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate (String...values){
                    super.onProgressUpdate(values);
                    textView.setText(values[0]);
                }
        };
    }

    private String getTime(long actual, long start) {
        long time = actual - start;
        long mins = time / 60000;
        long secs = time / 1000 - mins * 60;
        return String.format("%02d:%02d", mins, secs);
    }
}
