package vadim.kor.gmail.com.moveobj;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;

public class MovementView extends SurfaceView implements SurfaceHolder.Callback {
    private int xPos;
    private int yPos;

    private int xVel;
    private int yVel;
    private int angle;

    private int width;
    private int height;

    private int circleRadius;
    private Paint circlePaint;

    private Bitmap ball;
    private int ballWidth;
    private int ballHeight;
    private int currentAngle;


    UpdateThread updateThread;



    public MovementView(Context context) {

        super(context);
        getHolder().addCallback(this);

        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        ballWidth = ball.getWidth();
        ballHeight = ball.getHeight();
        currentAngle = 0;
        circleRadius = 40;
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);

    //nothing

        xVel = MainActivity.vSpeed;
        yVel = MainActivity.vSpeed;
        angle = MainActivity.wSpeed;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        @SuppressLint("DrawAllocation")
        Matrix mxTransform = new Matrix();
        mxTransform.preTranslate(xPos, yPos);
        mxTransform.preRotate(currentAngle, ballWidth / 2, ballHeight / 2);
        canvas.drawBitmap(ball, mxTransform, null);
    }

    public void updatePhysics() {
        xPos += xVel;
        yPos += yVel;
        currentAngle += angle;

        if (yPos < 0 || yPos + ballHeight > height) {
            if (yPos < 0) {
                yPos = 0;
            } else {
                yPos = height - ballHeight;
            }
            yVel *= -1;
        }

        if (xPos < 0 || xPos + ballWidth > width) {
            if (xPos < 0) {
                xPos = 0;
            } else {
                xPos = width - ballWidth;
            }
            xVel *= -1;
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {

        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();

        xPos = 0;
        yPos = 0;

        updateThread = new UpdateThread(this);
        updateThread.setRunning(true);
        updateThread.start();
    }

    public void stopMovementView() {
        updateThread.setRunning(false);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;

        updateThread.setRunning(false);
        while (retry) {
            try {
                updateThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}