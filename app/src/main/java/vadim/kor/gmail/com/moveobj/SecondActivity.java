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

    private boolean isRunning = true;

    @Override
    public void onClick(View view) {
        if (isRunning) {
            movementView.updateThread.setPause(true);

            double vXParam = movementView.getvX();
            double vYParam = movementView.getvY();
            double wParam = movementView.getW();
            double xParam = movementView.getXpos();
            double yParam = movementView.getYpos();

            String parameters = "Скорость по оси Ox:   " + String.valueOf(vXParam) + " м/с\nСкорость по оси Oy:   " +
                    String.valueOf(vYParam) + " м/с\nУгловая скорость:   " + String.valueOf(wParam) +
                    " рад/с\nКоордината по оси Ox:   " + String.valueOf(xParam) +
                    " м\nКоордината по оси Oy:   " + String.valueOf(yParam) + " м";

            AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
            builder.setTitle("Параметры полёта")
                    .setMessage(parameters)
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    movementView.updateThread.setPause(false);
                                    isRunning = true;
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            isRunning = false;
        }
    }
}