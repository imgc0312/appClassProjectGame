package imgc0312.appclassprojectgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyPlayerView extends MyObjectView {
    public MyPlayerView(Context context, AttributeSet set) {
        super(context, set);
        theShape = SHAPE.CIRCLE;
        radius = 75;
        paint.setColor(Color.BLUE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        lastLocationX = locationX;
        lastLocationY = locationY;
        Log.d("onTouch",  "(" +event.getX() + "," + event.getY() + ")");
        if( event.getX() >= radius && event.getX() < (this.getWidth() - radius))
            this.locationX = event.getX();
        if( event.getY() >= radius && event.getY() < (this.getHeight() - radius))
            this.locationY = event.getY();
        float[] center = this.getCenter();
        Log.d("onTouch Center",  "(" + center[0] + "," + center[1] + ")");
        //通知组件进行重绘
        this.invalidate();
        return true;
    }

    @Override
    public void collisionEffect(MyObjectView effectObject) {
        float[] hereCenter = this.getCenter();
        float[] effectCenter = effectObject.getCenter();
        float xOffset = effectCenter[0] - hereCenter[0];
        float yOffset = effectCenter[1] - hereCenter[1];
        double sita = Math.atan2(yOffset, xOffset);
        double newSpeedX = 0;
        double newSpeedY = 0;
        //利用鏡射算新speed
        //鏡射軸 與點間連線垂直
        double cos2Axies = Math.cos(2 * sita + Math.PI);
        double sin2Axies = Math.sin(2 * sita + Math.PI);

        newSpeedX = cos2Axies * effectObject.speedX + sin2Axies * effectObject.speedY;
        newSpeedY = sin2Axies * effectObject.speedX - cos2Axies * effectObject.speedY;
        effectObject.speedX = newSpeedX;
        effectObject.speedY = newSpeedY;
        effectObject.locationX += (float)((this.radius + effectObject.radius) * Math.cos(sita)) - xOffset;
        effectObject.locationY += (float)((this.radius + effectObject.radius) * Math.sin(sita)) - yOffset;

        super.collisionEffect(effectObject);
    }

}
