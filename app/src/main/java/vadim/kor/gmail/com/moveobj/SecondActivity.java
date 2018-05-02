package vadim.kor.gmail.com.moveobj;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    MovementView movementView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movementView = new MovementView(this);
        movementView.setOnClickListener(this);
        setContentView(movementView);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        movementView.stopMovementView();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                movementView.stopMovementView();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isRunning = true;

    @Override
    public void onClick(View view) {
        if (isRunning) {
            movementView.updateThread.setRunning(false);

            double v = Math.sqrt(movementView.vX * movementView.vX +
                    movementView.vY * movementView.vY);
            String parameters = "Скорость - " + String.valueOf(v) + " м/с";
            AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
            builder.setTitle("Параметры полёта")
                    .setMessage(parameters)
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            isRunning = false;
        } else {
            movementView.updateThread.setRunning(true);
            isRunning = true;
        }

    }
}