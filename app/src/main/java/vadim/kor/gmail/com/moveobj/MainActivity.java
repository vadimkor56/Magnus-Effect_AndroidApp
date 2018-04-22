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

    private String densityBarName;
    private String weightBarName;
    private String vSpeedBarName;
    private String wSpeedBarName;
    private String viscosityBarName;

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

        densityBarName = getString(R.string.densityBarName);
        weightBarName = getString(R.string.weightBarName);
        vSpeedBarName = getString(R.string.vSpeedBarName);
        wSpeedBarName = getString(R.string.wSpeedBarName);
        viscosityBarName = getString(R.string.viscosityBarName);

        changeText(densityBar, densityBar.getProgress());
        changeText(weightBar, weightBar.getProgress());
        changeText(vSpeedBar, vSpeedBar.getProgress());
        changeText(wSpeedBar, wSpeedBar.getProgress());
        changeText(viscosityBar, viscosityBar.getProgress());
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
        changeText(seekBar, progress);
    }

    private void changeText(SeekBar seekBar, int progress) {
        String newText;
        switch (seekBar.getId()) {
            case R.id.densityBar:
                newText = densityBarName + " " + String.valueOf(progress);
                textDensity.setText(newText);
                break;
            case R.id.weightBar:
                newText = weightBarName + " " + String.valueOf(progress);
                textWeight.setText(newText);
                break;
            case R.id.vSpeedBar:
                newText = vSpeedBarName + " " + String.valueOf(progress);
                textVSpeed.setText(newText);
                break;
            case R.id.wSpeedBar:
                newText = wSpeedBarName + " " + String.valueOf(progress);
                textWSpeed.setText(newText);
                break;
            case R.id.viscosityBar:
                newText = viscosityBarName + " " + String.valueOf(progress);
                textViscosity.setText(newText);
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
