package imgc0312.appclassprojectgame;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

public class MyWallView extends MyObjectView {
    //enable which dir collision
    public boolean wallLeft = false;
    public boolean wallRight = false;
    public boolean wallTop = false;
    public boolean wallBottom = false;

    public MyWallView(Context context, AttributeSet set) {
        super(context, set);
        theShape = SHAPE.RECT;
        theWidth = this.getRight() - this.getLeft();
        theHeight = this.getBottom() - this.getTop();
        paint.setColor(Color.WHITE);
    }

    @Override
    public void collisionEffect(MyObjectView effectObject) {

        double backOutTimeX = Double.MAX_VALUE;
        double backOutTimeY = Double.MAX_VALUE;
        if(wallTop){
            double t = (effectObject.getObjectBound(WAY.BOTTOM) - this.getObjectBound(WAY.TOP)) / effectObject.speedY;
            if((t >= 0) && (t <  backOutTimeY))
                backOutTimeY = t;
        }
        if(wallBottom){
            double t = (effectObject.getObjectBound(WAY.TOP) - this.getObjectBound(WAY.BOTTOM)) / effectObject.speedY;
            if((t >= 0) && (t <  backOutTimeY))
                backOutTimeY = t;
        }
        if(wallLeft){
            double t = (effectObject.getObjectBound(WAY.RIGHT) - this.getObjectBound(WAY.LEFT)) / effectObject.speedX;
            if((t >= 0) && (t <  backOutTimeX))
                backOutTimeX = t;
        }
        if(wallRight){
            double t = (effectObject.getObjectBound(WAY.LEFT) - this.getObjectBound(WAY.RIGHT)) / effectObject.speedX;
            if((t >= 0) && (t <  backOutTimeX))
                backOutTimeX = t;
        }

        //X collision
        if(backOutTimeY >= backOutTimeX * 0.95){
            if ((effectObject.getObjectBound(WAY.LEFT) < this.getObjectBound(WAY.RIGHT)) && (effectObject.speedX < 0) && this.wallRight) {
                Log.d("Wall collision", "cL");
                effectObject.speedX *= -0.99;
                effectObject.locationX -= effectObject.getObjectBound(WAY.LEFT) - this.getObjectBound(WAY.RIGHT);
            }
            else if ((effectObject.getObjectBound(WAY.RIGHT) > this.getObjectBound(WAY.LEFT)) && (effectObject.speedX > 0) && this.wallLeft) {
                Log.d("Wall collision", "cR");
                effectObject.speedX *= -0.99;
                effectObject.locationX -= effectObject.getObjectBound(WAY.RIGHT) - this.getObjectBound(WAY.LEFT);
            }
        }

        //Y collision
        if(backOutTimeX >= backOutTimeY * 0.95){
            if ((effectObject.getObjectBound(WAY.TOP) < this.getObjectBound(WAY.BOTTOM)) && (effectObject.speedY < 0) && this.wallBottom) {
                Log.d("Wall collision", "cT");
                effectObject.speedY *= -0.99;
                effectObject.locationY -= effectObject.getObjectBound(WAY.TOP) - this.getObjectBound(WAY.BOTTOM);
            }
            else if ((effectObject.getObjectBound(WAY.BOTTOM) > this.getObjectBound(WAY.TOP)) && (effectObject.speedY > 0) && this.wallTop) {
                Log.d("Wall collision", "cB");
                effectObject.speedY *= -0.99;
                effectObject.locationY -= effectObject.getObjectBound(WAY.BOTTOM) - this.getObjectBound(WAY.TOP);
            }
        }
        super.collisionEffect(effectObject);
    }
}
