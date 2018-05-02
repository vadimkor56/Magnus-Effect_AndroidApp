package vadim.kor.gmail.com.moveobj;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    Button startBtn;
    SeekBar heightBar;
    SeekBar weightBar;
    SeekBar vSpeedBar;
    SeekBar wSpeedBar;
    SeekBar viscosityBar;
    SeekBar radiusBar;

    TextView textHeight;
    TextView textWeight;
    TextView textVSpeed;
    TextView textWSpeed;
    TextView textViscosity;
    TextView textRadius;

    static double height;
    static double vSpeed;
    static double wSpeed;
    static double weight;
    static double viscosity;
    static double radius;
    static double tempr;

    private String heightBarName;
    private String weightBarName;
    private String vSpeedBarName;
    private String wSpeedBarName;
    private String viscosityBarName;
    private String radiusBarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.startButton);
        startBtn.setOnClickListener(this);

        heightBar = findViewById(R.id.heightBar);
        weightBar = findViewById(R.id.weightBar);
        vSpeedBar = findViewById(R.id.vSpeedBar);
        wSpeedBar = findViewById(R.id.wSpeedBar);
        viscosityBar = findViewById(R.id.temprBar);
        radiusBar = findViewById(R.id.radiusBar);

        heightBar.setOnSeekBarChangeListener(this);
        weightBar.setOnSeekBarChangeListener(this);
        vSpeedBar.setOnSeekBarChangeListener(this);
        wSpeedBar.setOnSeekBarChangeListener(this);
        viscosityBar.setOnSeekBarChangeListener(this);
        radiusBar.setOnSeekBarChangeListener(this);

        textHeight = findViewById(R.id.textHeight);
        textWeight = findViewById(R.id.textWeight);
        textVSpeed = findViewById(R.id.textVSpeed);
        textWSpeed = findViewById(R.id.textWSpeed);
        textViscosity = findViewById(R.id.textTempr);
        textRadius = findViewById(R.id.textRadius);

        heightBarName = getString(R.string.heightBarName);
        weightBarName = getString(R.string.weightBarName);
        vSpeedBarName = getString(R.string.vSpeedBarName);
        wSpeedBarName = getString(R.string.wSpeedBarName);
        viscosityBarName = getString(R.string.viscosityBarName);
        radiusBarName = getString(R.string.radiusBarName);

        changeText(heightBar, heightBar.getProgress());
        changeText(weightBar, weightBar.getProgress());
        changeText(vSpeedBar, vSpeedBar.getProgress());
        changeText(wSpeedBar, wSpeedBar.getProgress());
        changeText(viscosityBar, viscosityBar.getProgress());
        changeText(radiusBar, radiusBar.getProgress());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                height = heightBar.getProgress();
                vSpeed = vSpeedBar.getProgress();
                wSpeed = wSpeedBar.getProgress();
                weight = weightBar.getProgress();
                tempr = viscosityBar.getProgress();
                radius = radiusBar.getProgress();

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
            case R.id.heightBar:
                newText = heightBarName + " " + String.valueOf(progress);
                textHeight.setText(newText);
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
            case R.id.temprBar:
                newText = viscosityBarName + " " + String.valueOf(progress);
                textViscosity.setText(newText);
                break;
            case R.id.radiusBar:
                newText = radiusBarName + " " + String.valueOf(progress);
                textRadius.setText(newText);
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
