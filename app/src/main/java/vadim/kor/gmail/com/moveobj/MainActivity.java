package vadim.kor.gmail.com.moveobj;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    Button startBtn;
    SeekBar densityBar;
    SeekBar weightBar;
    SeekBar vSpeedBar;
    SeekBar wSpeedBar;
    SeekBar viscosityBar;

    TextView textDensity;
    TextView textWeight;
    TextView textVSpeed;
    TextView textWSpeed;
    TextView textViscosity;

    static int density;
    static int vSpeed;
    static int wSpeed;
    static int weight;
    static int viscosity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.startButton);
        startBtn.setOnClickListener(this);

        densityBar = findViewById(R.id.densityBar);
        weightBar = findViewById(R.id.weightBar);
        vSpeedBar = findViewById(R.id.vSpeedBar);
        wSpeedBar = findViewById(R.id.wSpeedBar);
        viscosityBar = findViewById(R.id.viscosityBar);

        densityBar.setOnSeekBarChangeListener(this);
        weightBar.setOnSeekBarChangeListener(this);
        vSpeedBar.setOnSeekBarChangeListener(this);
        wSpeedBar.setOnSeekBarChangeListener(this);
        viscosityBar.setOnSeekBarChangeListener(this);

        textDensity = findViewById(R.id.textDensity);
        textWeight = findViewById(R.id.textWeight);
        textVSpeed = findViewById(R.id.textVSpeed);
        textWSpeed = findViewById(R.id.textWSpeed);
        textViscosity = findViewById(R.id.textViscosity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                density = densityBar.getProgress();
                vSpeed = vSpeedBar.getProgress();
                wSpeed = wSpeedBar.getProgress();
                weight = weightBar.getProgress();
                viscosity = viscosityBar.getProgress();

                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.densityBar:
                textDensity.setText("Плотность среды (кг/м^3) - " + String.valueOf(progress));
                break;
            case R.id.weightBar:
                textWeight.setText("Масса мяча (кг) - " + String.valueOf(progress));
                break;
            case R.id.vSpeedBar:
                textVSpeed.setText("Начальная скорость (м/с) - " + String.valueOf(progress));
                break;
            case R.id.wSpeedBar:
                textWSpeed.setText("Угловая скорость (рад/с) - " + String.valueOf(progress));
                break;
            case R.id.viscosityBar:
                textViscosity.setText("Вязкость (мкПа * c) - " + String.valueOf(progress));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
