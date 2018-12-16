package imgc0312.appclassprojectgame;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import java.util.Timer;
import java.util.TimerTask;

public class MyBallView extends MyObjectView {
    //MSG what
    public static final int MSG_RUN = 0x666;
    public static final int MSG_STOP = 0x777;
    public static final int MSG_KEEP = 0x123;

    MyObjectView[] relativeObject = new MyObjectView[0];
    boolean moveState = false;// when true=> move by speed
    public Handler handler = new Handler()
    {
        int i = 0;
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
        }, 0,5);
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
            if(this.isCollision(testObject)){
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

        //通知组件进行重绘
        this.invalidate();
    }

}
