package imgc0312.appclassprojectgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MyObjectView extends View {
    public enum iniLocation { LEFT, CENTER, RIGHT}
    public enum SHAPE { CIRCLE, RECT}
    public enum WAY {LEFT, RIGHT, TOP, BOTTOM}
    SHAPE theShape;//decide the collision detect
    //the location (X,Y) is left-top relative offset  of the object
    public float lastLocationX;
    public float lastLocationY;
    public float locationX;
    public float locationY;
    public float radius;//use in objectShape = CIRCLE
    public float theWidth;//use in objectShape = RECT
    public float theHeight;//use in objectShape = RECT

    public double speedX;    //the X speed of move
    public double speedY; //the Y speed of move
    //the graphic image
    Bitmap bitmap;
    Paint paint = new Paint();

    public MyObjectView(Context context, AttributeSet set) {
        super(context, set);
        theShape = SHAPE.CIRCLE;
        lastLocationX = 0;
        lastLocationY = 0;
        locationX = 0;
        locationY = 0;
        radius = 0;
        theWidth = 0;
        theHeight = 0;
        speedX = 0;
        speedY = 0;
        bitmap = null;
        paint.setColor(Color.WHITE);
    }

    public float[] getCenter(){//abosolute position
        float[] centerPoint = new float[2];

        switch (theShape){
            case CIRCLE:
                centerPoint[0] = locationX + radius + this.getLeft();
                centerPoint[1] = locationY + radius + this.getTop();
            case RECT:
                centerPoint[0] = locationX + theWidth/2 + this.getLeft();
                centerPoint[1] = locationY + theHeight/2 + this.getTop();
        }
        return centerPoint;
    }

    public float getObjectBound(WAY way){//abosolute position
        switch (way){
            case LEFT:
                return locationX + this.getLeft();
            case RIGHT:
                switch (theShape) {
                    case CIRCLE:
                        return locationX + 2* radius + this.getLeft();
                    case RECT:
                        return locationX + theWidth + this.getLeft();
                }
            case TOP:
                return locationY + this.getTop();
            case BOTTOM:
                switch (theShape) {
                    case CIRCLE:
                        return locationY + 2* radius + this.getTop();
                    case RECT:
                        return locationY + theHeight + this.getTop();
                }
        }
        return 0.0f;
    }

    public void initial(){
        //Log.d("onInitail nullarg", "(" +this.getWidth() + "," + this.getHeight() + ")");
        locationX = 0;
        locationY = 0;
        lastLocationX = locationX;
        lastLocationY = locationY;
        switch (theShape) {
            case CIRCLE:
                theWidth = (theHeight = 2* radius);
                break;
            case RECT:
                theWidth = this.getRight() - this.getLeft();
                theHeight = this.getBottom() - this.getTop();
                break;
        }
        speedX = 0;
        speedY = 0;
        //re draw
        this.invalidate();
    }

    public void initial(iniLocation locationMode){
        //Log.d("onInitail nullarg", "(" +this.getWidth() + "," + this.getHeight() + ")");
        switch (theShape) {
            case CIRCLE:
                theWidth = (theHeight = 2* radius);
                break;
            case RECT:
                theWidth = this.getRight() - this.getLeft();
                theHeight = this.getBottom() - this.getTop();
                break;
        }
        switch (locationMode){
            case LEFT:
                locationX = (float)this.getWidth() / 4 - theWidth/2;
                locationY = (float)this.getHeight() / 2 - theHeight/2;
                break;
            case CENTER:
                locationX = (float)this.getWidth() / 2 - theWidth/2;
                locationY = (float)this.getHeight() / 2 - theHeight/2;
                break;
            case RIGHT:
                locationX = (float)this.getWidth() * 3 / 4 - theWidth/2;
                locationY = (float)this.getHeight() / 2 - theHeight/2;
                break;
        }
        lastLocationX = locationX;
        lastLocationY = locationY;
        speedX = 0;
        speedY = 0;
        //re draw
        this.invalidate();
    }

    public void initial(float x, float y){
        //Log.d("onInitail nullarg", "(" +this.getWidth() + "," + this.getHeight() + ")");
        locationX = x;
        locationY = y;
        lastLocationX = locationX;
        lastLocationY = locationY;
        speedX = 0;
        speedY = 0;
        switch (theShape) {
            case CIRCLE:
                theWidth = (theHeight = 2* radius);
                break;
            case RECT:
                theWidth = this.getRight() - this.getLeft();
                theHeight = this.getBottom() - this.getTop();
                break;
        }
        //re draw
        this.invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.d("onDraw",  "(" +locationX  + "+" + activeRect.left + "," + locationY + "+" + activeRect.top + ")");
        switch (theShape){
            case RECT:
                //Log.d("onDraw","RECT " + this.toString());
                canvas.drawRect(locationX, locationY, locationX +theWidth, locationY +theHeight, paint);
                break;
            case CIRCLE:
                //Log.d("onDraw","CIRCLE " + this.toString());

                canvas.drawCircle(locationX + radius, locationY + radius, radius, paint);
                break;
        }
    }

    public boolean isCollision(MyObjectView testObject){
        switch (testObject.theShape){
            case RECT:
                return isCollisionWithRect(this.getObjectBound(WAY.LEFT), this.getObjectBound(WAY.TOP), this.radius*2, this.radius*2,
                        testObject.getObjectBound(WAY.LEFT), testObject.getObjectBound(WAY.TOP), testObject.theWidth, testObject.theHeight);
            case CIRCLE:
                return isCollisionWithCircle(this.getCenter(), this.radius, testObject.getCenter(), testObject.radius);
        }
        return false;
    }

    public static boolean isCollisionWithRect(float x1, float y1, float w1, float h1,
                                              float x2, float y2, float w2, float h2) {
        if (x1 >= x2 && x1 >= x2 + w2) {
            return false;
        } else if (x1 <= x2 && x1 + w1 <= x2) {
            return false;
        } else if (y1 >= y2 && y1 >= y2 + h2) {
            return false;
        } else if (y1 <= y2 && y1 + h1 <= y2) {
            return false;
        }
        return true;
    }

    public static boolean isCollisionWithCircle(float[] center1, float radius1, float[] center2, float radius2){
        return (Math.pow(center1[0] - center2[0], 2) + Math.pow(center1[1] - center2[1], 2) < Math.pow(radius1 + radius2, 2));
    }

    public void collisionEffect(MyObjectView effectObject){
        return;// do noting
    }

    protected static double getSita(float[] center1, float[] center2){
        double X = center2[0] - center1[0];
        double Y = center2[1] - center1[1];
        return Math.atan2(Y, X);
    }

}
