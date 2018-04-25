package vadim.kor.gmail.com.moveobj;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Random;

public class MovementView extends SurfaceView implements SurfaceHolder.Callback {
    private final double PI = Math.PI;
    private final double deltaT = 0.005;
    private final double M = 0.029;
    private final double Rconst = 8.31;
    private final double T = 273;
    private final double EPS = 0.01;


    private int xPos;
    private int yPos;
    private int angle;

    private double vX;
    private double vY;
    private double vZ;

    private double wX;
    private double wY;
    private double wZ;

    private double h;
    private double ro;
    private double m;
    private double v0;
    private double w0;
    private double nu;
    private double r;
    private double g = 9.81;
    private double loss = 0.5;

    private int width;
    private int height;

    private Bitmap ball;
    private int ballWidth;
    private int ballHeight;
    private int directionX = 1;
    private int directionY = 1;

    UpdateThread updateThread;

    private Path mPath = new Path();
    private Paint mPaint;

    public MovementView(Context context) {

        super(context);
        getHolder().addCallback(this);

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        float dpHeight = outMetrics.heightPixels / getResources().getDisplayMetrics().density;

        angle = 0;
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);

        r = MainActivity.radius / 100;
        h = MainActivity.height;
        m = MainActivity.weight / 1000;
        v0 = MainActivity.vSpeed;
        w0 = MainActivity.wSpeed;
        nu = MainActivity.viscosity / 6000;
        ro = M / Rconst / T * 100000 * Math.exp(-M * g * h / Rconst / T);

        int newWidth = (int) (2 * r * dpHeight / h) == 0 ? 1 : (int) (2 * r * dpHeight / h);

        Bitmap notScaledBall = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        ball = Bitmap.createScaledBitmap(notScaledBall, newWidth, newWidth, true);

        ballWidth = ball.getWidth();
        ballHeight = ball.getHeight();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(mPath, mPaint);

        @SuppressLint("DrawAllocation")
        Matrix mxTransform = new Matrix();
        mxTransform.preTranslate(xPos, yPos);
        mxTransform.preRotate(angle, ballWidth / 2, ballHeight / 2);
        canvas.drawBitmap(ball, mxTransform, null);
    }

    public void updatePhysics() {
        mPath.lineTo(xPos, yPos);
        xPos += (int) (vX * deltaT);
        yPos += (int) (vY * deltaT);
        angle += w0;

        /*double deltaVx = 2 * PI / (3 * m) * ro * v0 * r * r * (vZ * wY - vY * wZ) /
                Math.sqrt(wX * wX + wY * wY + wZ * wZ) - 6 * PI * r * nu * vX / m;
        double deltaVy = g + 2 * PI / (3 * m) * ro * v0 * r * r * (vX * wZ - vZ * wX) /
                Math.sqrt(wX * wX + wY * wY + wZ * wZ) - 6 * PI * r * nu * vY / m;
        double deltaVz = 2 * PI / (3 * m) * ro * v0 * r * r * (vY * wX - vX * wY) /
                Math.sqrt(wX * wX + wY * wY + wZ * wZ) - 6 * PI * r * nu * vZ / m;*/

        /*double deltaVx = 2 * PI * nu * r * r * r * (vY * wZ - vZ * wY) / m;
        double deltaVy = g - 2 * PI * nu * r * r * r * (vX * wZ - vZ * wX) / m;
        double deltaVz = 2 * PI * nu * r * r * r * (vY * wX - vX * wY) / m;*/

        double deltaVx;
        double deltaVy;

        /*if (w0 < EPS) {
            deltaVx = - directionX * 6 * PI * r * nu * vX / m;
            deltaVy = g - directionY * 6 * PI * r * nu * vY / m;
        } else {
            deltaVx = directionX * (2 * PI / (3 * m) * ro * v0 * r * r * (vZ * wY - vY * wZ) /
                    Math.sqrt(wX * wX + wY * wY + wZ * wZ) - 6 * PI * r * nu * vX / m);
            deltaVy = g + directionY * (2 * PI / (3 * m) * ro * v0 * r * r * (vX * wZ - vZ * wX) /
                    Math.sqrt(wX * wX + wY * wY + wZ * wZ) - 6 * PI * r * nu * vY / m);
        }*/

        deltaVx = 10 * Math.sqrt(wX * wX + wY * wY + wZ * wZ) - 6 * PI * r * nu * vX / m;
        deltaVy = g - 10 * PI / (3 * m) * Math.sqrt(wX * wX + wY * wY + wZ * wZ) -
                6 * PI * r * nu * vY / m;

        vX += deltaVx * deltaT;
        vY += deltaVy * deltaT;

        if (yPos < 0 || yPos + ballHeight > height) {
            if (yPos < 0) {
                yPos = 0;
            } else {
                yPos = height - ballHeight;
            }
            vY *= -1 * loss;
            w0 *= loss;
            directionY *= -1;
        }

        if (xPos < 0 || xPos + ballWidth > width) {
            if (xPos < 0) {
                xPos = 0;
            } else {
                xPos = width - ballWidth;
            }
            vX *= -1 * loss;
            w0 *= loss;
            directionX *= -1;
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {

        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();

        v0 = height / h * v0;
        g = g * height / h;

        xPos = 0;
        yPos = 0;

        vX = v0;
        vY = 0;
        vZ = 0;
        wX = w0;
        wY = -1;
        wZ = -1;

        mPath.moveTo(xPos, yPos);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),
                rnd.nextInt(256));
        mPaint.setColor(color);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);

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