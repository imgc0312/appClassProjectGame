package imgc0312.appclassprojectgame;

import android.app.usage.UsageEvents;
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
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            lastLocationX = locationX;
            lastLocationY = locationY;
        }
        Log.d("onTouch",  "(" +event.getX() + "," + event.getY() + ")");
        if( event.getX() >= radius && event.getX() < (this.getWidth() - radius))
            this.locationX = event.getX() - radius;
        if( event.getY() >= radius && event.getY() < (this.getHeight() - radius))
            this.locationY = event.getY() - radius;
        float[] center = this.getCenter();
        Log.d("onTouch Center",  "(" + center[0] + "," + center[1] + ")");
        if(event.getAction() != MotionEvent.ACTION_MOVE){
            lastLocationX = locationX;
            lastLocationY = locationY;
        }
//        //通知组件进行重绘
//        this.invalidate();
        return true;
    }

    @Override
    public void collisionEffect(MyObjectView effectObject) {
        float[] hereCenter = this.getCenter();
        float[] effectCenter = effectObject.getCenter();
        float xOffset = effectCenter[0] - hereCenter[0];
        float yOffset = effectCenter[1] - hereCenter[1];
        double sita = Math.atan2(yOffset, xOffset);//球心連線夾角
        double Vsita = Math.atan2(effectObject.speedY, effectObject.speedX); // 球速度角
        double sita2 = sita * 2;
        double Vsita2 = Vsita * 2;
        double newSpeedX = 0;
        double newSpeedY = 0;
        double hereSpeedX = this.locationX - this.lastLocationX;
        double hereSpeedY = this.locationY - this.lastLocationY;
        //利用鏡射算新speed
        //鏡射軸 與點間連線垂直
        double cos2Axies = Math.cos(sita2 + Math.PI);
        double sin2Axies = Math.sin(sita2 + Math.PI);
        if((Vsita2 < (sita2 - Math.PI)) || (Vsita2 > (sita2 + Math.PI))){//遠離則反射
            newSpeedX = cos2Axies * effectObject.speedX*0.82 + sin2Axies * effectObject.speedY*0.82;
            newSpeedY = sin2Axies * effectObject.speedX*0.82 - cos2Axies * effectObject.speedY*0.82;
        }
        effectObject.speedX = newSpeedX + hereSpeedX *0.03;//傳遞here 速度
        effectObject.speedY = newSpeedY + hereSpeedY *0.03;
        effectObject.locationX += (float)((this.radius + effectObject.radius) * Math.cos(sita)) - xOffset;
        effectObject.locationY += (float)((this.radius + effectObject.radius) * Math.sin(sita)) - yOffset;

        super.collisionEffect(effectObject);
    }

}
