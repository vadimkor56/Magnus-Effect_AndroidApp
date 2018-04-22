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
    private final double PI = Math.PI;
    private final double deltaT = 0.1;
    private final double g = 9.81;

    private int xPos;
    private int yPos;
    private int angle;

    private double vX;
    private double vY;
    private double vZ;

    private double ro;
    private double m;
    private double v0;
    private double w0;
    private double nu;
    private double r;

    private int width;
    private int height;

    private Bitmap ball;
    private int ballWidth;
    private int ballHeight;

    UpdateThread updateThread;

    public MovementView(Context context) {

        super(context);
        getHolder().addCallback(this);

        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        ballWidth = ball.getWidth();
        ballHeight = ball.getHeight();
        angle = 0;
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);

        ro = 1.29;
        m = 0.6;
        v0 = 50;
        w0 = -PI;
        nu = 0.0000171;
        r = 0.13;


        /*ro = MainActivity.density;
        m = MainActivity.weight;
        v0 = MainActivity.vSpeed;
        w0 = MainActivity.wSpeed;
        nu = MainActivity.viscosity;*/
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        @SuppressLint("DrawAllocation")
        Matrix mxTransform = new Matrix();
        mxTransform.preTranslate(xPos, yPos);
        mxTransform.preRotate(angle, ballWidth / 2, ballHeight / 2);
        canvas.drawBitmap(ball, mxTransform, null);
    }

    public void updatePhysics() {
        xPos += (int) (vX * deltaT);
        yPos += (int) (vY * deltaT);
        angle += w0;

        double deltaVx = 0;
        double deltaVy = g - 2 * PI / (3 * m) * ro * v0 * r * r * vZ - 6 * PI * r * nu * vY / m;
        double deltaVz = - 6 * PI * r * nu * vZ / m;

        vX += deltaVx * deltaT;
        vY += deltaVy * deltaT;
        vZ += deltaVz * deltaT;

        if (yPos < 0 || yPos + ballHeight > height) {
            if (yPos < 0) {
                yPos = 0;
            } else {
                yPos = height - ballHeight;
            }
            vY *= -1;
        }

        if (xPos < 0 || xPos + ballWidth > width) {
            if (xPos < 0) {
                xPos = 0;
            } else {
                xPos = width - ballWidth;
            }
            vX *= -1;
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {

        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();

        xPos = 0;
        yPos = 0;
        vX = v0;
        vY = 0;
        vZ = 0;

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