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

public class MovementView extends SurfaceView implements SurfaceHolder.Callback {
    private int xPos;
    private int yPos;

    private int xVel;
    private int yVel;

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

        xVel = 2;
        yVel = 2;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        //canvas.drawCircle(xPos, yPos, circleRadius, circlePaint);
        @SuppressLint("DrawAllocation")
        Matrix mxTransform = new Matrix();
        mxTransform.preTranslate(xPos, yPos);
        mxTransform.preRotate(currentAngle, ballWidth / 2, ballHeight / 2);
        canvas.drawBitmap(ball, mxTransform, null);
    }

    public void updatePhysics() {
        xPos += xVel;
        yPos += yVel;
        currentAngle += 2;
        if (yPos - circleRadius < 0 || yPos + circleRadius > height) {
            if (yPos - circleRadius < 0) {
                yPos = circleRadius;
            }else{
                yPos = height - circleRadius;
            }
            yVel *= -1;
        }
        if (xPos - circleRadius < 0 || xPos + circleRadius > width) {
            if (xPos - circleRadius < 0) {
                xPos = circleRadius;
            } else {
                xPos = width - circleRadius;
            }
            xVel *= -1;
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {

        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();

        xPos = width / 2;
        yPos = circleRadius;

        updateThread = new UpdateThread(this);
        updateThread.setRunning(true);
        updateThread.start();
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