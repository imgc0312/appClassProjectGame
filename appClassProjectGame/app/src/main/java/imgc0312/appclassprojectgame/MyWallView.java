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

        double collosionTimeX = Double.MIN_VALUE;
        double collosionTimeY = Double.MIN_VALUE;
        if(wallTop || wallBottom){
            collosionTimeY = (effectObject.getObjectBound(WAY.BOTTOM) - this.getObjectBound(WAY.TOP)) / effectObject.speedY;
            double t = (effectObject.getObjectBound(WAY.TOP) - this.getObjectBound(WAY.BOTTOM)) / effectObject.speedY;
            if(t >  collosionTimeY)
                collosionTimeY = t;
        }
        if(wallLeft || wallRight){
            collosionTimeX = (effectObject.getObjectBound(WAY.RIGHT) - this.getObjectBound(WAY.LEFT)) / effectObject.speedX;
            double t = (effectObject.getObjectBound(WAY.LEFT) - this.getObjectBound(WAY.RIGHT)) / effectObject.speedX;
            if(t >  collosionTimeX)
                collosionTimeX = t;
        }

        //X collision
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
        super.collisionEffect(effectObject);
    }
}
