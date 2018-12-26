package imgc0312.appclassprojectgame;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyBallView extends MyObjectView {
    public static int flashPriod = 5;
    public static double moveSlow = 0.003;//the speed loss when move

    //MSG what
    public static final int MSG_RUN = 0x666;
    public static final int MSG_STOP = 0x777;
    public static final int MSG_KEEP = 0x123;
    int flashCount = 0;
    MyObjectView[] relativeObject = new MyObjectView[0];
    boolean moveState = false;// when true=> move by speed
    Handler gameEvent = null;
    public Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            //判断信息是否为本应用发出的
            switch (msg.what){
                case MSG_KEEP://keep move
                    if(moveState)
                        move();//try move
                    break;
                case MSG_RUN:
                    moveState = true;
                    break;
                case MSG_STOP:
                    moveState = false;
            }
            super.handleMessage(msg);
        }
    };

    public MyBallView(Context context, AttributeSet set) {
        super(context, set);
        theShape = SHAPE.CIRCLE;
        radius = 100;
        paint.setColor(Color.RED);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //发送一条空信息来通知系统改变前景图片
                handler.sendEmptyMessage(MSG_KEEP);
            }
        }, 0,flashPriod);
    }

    public void connect(Handler gameEvent){
        this.gameEvent = gameEvent;
    }

    public void setRelativeObject(MyObjectView... args){
        if(args != null)
            relativeObject = args;
    }

    void move(){//try to move
        lastLocationX = locationX;
        lastLocationY = locationY;
        locationX += speedX;
        locationY += speedY;
        speedX*=0.999;
        speedY*=0.999;
        float[] center = this.getCenter();
//        if ((center[0] < 0) && (speedX < 0))
//            speedX *= -1;
//        else if ((center[0] >= this.getWidth()) && (speedX > 0))
//            speedX *= -1;
//        if ((center[1] < 0) && (speedY < 0))
//            speedY *= -1;
//        else if ((center[1] >= this.getHeight()) && (speedY > 0))
//            speedY *= -1;

        for(MyObjectView testObject : relativeObject){// test collision
            //Log.d("Collision Test", testObject.toString());
            if(this.isCollision(testObject)){
                Log.d("Collision Test", testObject.toString());
//                float[] testCenter = testObject.getCenter();
//                if((speedX < 0) && (center[0] > testCenter[0]))
//                    speedX *= -1;
//                else if ((speedX > 0) && (center[0] < testCenter[0]))
//                    speedX *= -1;
//                if((speedY < 0) && (center[1] > testCenter[1]))
//                    speedY *= -1;
//                else if ((speedY > 0) && (center[1] < testCenter[1]))
//                    speedY *= -1;
                testObject.collisionEffect(this);
            }
        }

//        //通知组件进行重绘
//        if(flashCount%5 == 0){// reduce flash times
//            this.invalidate();
//            flashCount = 1;
//        }
//        else {
//            flashCount++;
//        }

        if(this.getObjectBound(WAY.RIGHT) < this.getLeft()){
            this.invalidate();
            handler.sendEmptyMessage(MSG_STOP);
            if(gameEvent != null){
                gameEvent.sendEmptyMessage(TableHockeyActivity.MSG_RIGHTWIN);
            }
        }
        else if(this.getObjectBound(WAY.LEFT) > this.getRight()){
            this.invalidate();
            handler.sendEmptyMessage(MSG_STOP);
            if(gameEvent != null){
                gameEvent.sendEmptyMessage(TableHockeyActivity.MSG_LEFTWIN);
            }
        }
    }
}
