package vadim.kor.gmail.com.moveobj;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class UpdateThread extends Thread {

    private long time;
    private final int fps = 20;
    private boolean toRun = false;
    private boolean isPaused = false;
    private MovementView movementView;
    private SurfaceHolder surfaceHolder;
    public Canvas canvas;

    public double getTime() {
        return ((int) (100 * time)) / 100d;
    }

    UpdateThread(MovementView rMovementView) {
        movementView = rMovementView;
        surfaceHolder = movementView.getHolder();
    }

    void setRunning(boolean run) {
        toRun = run;
    }

    void setPause(boolean pause) {
        isPaused = pause;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {

        long timeOfDelay = 300;
        long timeOfStart = System.currentTimeMillis();
        while (toRun) {
            if (!isPaused) {
                long cTime = System.currentTimeMillis();

                if ((cTime - time) <= (1000 / fps) && cTime > timeOfStart + timeOfDelay) {
                    canvas = null;
                    try {
                        canvas = surfaceHolder.lockCanvas(null);
                        movementView.updatePhysics();
                        movementView.onDraw(canvas);
                    } catch (NullPointerException ignored) {
                        Log.d("UpdateThread", "Null pointer exception in movementView.onDraw()");
                    } catch (Exception e) {
                        Log.d("UpdateThread", "Exception in movementView.onDraw()");
                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
                time = cTime;
            }
        }
    }
}