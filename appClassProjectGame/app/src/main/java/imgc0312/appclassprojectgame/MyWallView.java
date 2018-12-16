package imgc0312.appclassprojectgame;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

public class MyWallView extends MyObjectView {
    public MyWallView(Context context, AttributeSet set) {
        super(context, set);
        theShape = SHAPE.RECT;
        theWidth = this.getWidth();
        theHeight = this.getHeight();
        paint.setColor(Color.WHITE);
    }


    @Override
    public void collisionEffect(MyObjectView effectObject) {

        if ((effectObject.getObjectBound(WAY.LEFT) < this.getObjectBound(WAY.RIGHT)) && (effectObject.speedX < 0))
            effectObject.speedX *= -1;
        else if ((effectObject.getObjectBound(WAY.RIGHT) >= this.getObjectBound(WAY.LEFT)) && (effectObject.speedX > 0))
            effectObject.speedX *= -1;
        if ((effectObject.getObjectBound(WAY.BOTTOM) < this.getObjectBound(WAY.TOP)) && (effectObject.speedY < 0))
            effectObject.speedY *= -1;
        else if ((effectObject.getObjectBound(WAY.TOP) >= this.getObjectBound(WAY.BOTTOM)) && (effectObject.speedY > 0))
            effectObject.speedY *= -1;

        super.collisionEffect(effectObject);
    }
}
