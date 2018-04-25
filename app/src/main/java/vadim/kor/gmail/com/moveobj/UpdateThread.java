package vadim.kor.gmail.com.moveobj;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class UpdateThread extends Thread {

    private long time;
    private final int fps = 20;
    private boolean toRun = false;
    private MovementView movementView;
    private SurfaceHolder surfaceHolder;

    UpdateThread(MovementView rMovementView) {
        movementView = rMovementView;
        surfaceHolder = movementView.getHolder();
    }

    void setRunning(boolean run) {
        toRun = run;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas canvas;
        long timeOfDelay = 300;
        long timeOfStart = System.currentTimeMillis();
        while (toRun) {

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