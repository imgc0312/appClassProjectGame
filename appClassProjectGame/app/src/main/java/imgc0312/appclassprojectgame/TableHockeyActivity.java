package imgc0312.appclassprojectgame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TableHockeyActivity extends Activity {
    Button theBtn,theBtn2;
    MyObjectView leftPlayer;
    MyObjectView rightPlayer;
    MyBallView theBall;
    List<MyWallView> theWalls = new ArrayList<>();
    TextView[] winView = new TextView[2];
    public static Handler gameEvent;
    public static final int MSG_FLASH = 0x000;
    public static final int MSG_GAMEINI = 0x001;
    public static final int MSG_LEFTWIN = 0x002;
    public static final int MSG_RIGHTWIN = 0x003;
    Timer timer = new Timer();
    TimerTask timeTask = new TimerTask() {
        public void run() {
            //Log.d("Timer Test", " test");
            if(gameEvent != null)
                gameEvent.sendEmptyMessage(MSG_FLASH);// flash screen
        }
    };
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_hockey);
        leftPlayer = (MyObjectView)findViewById(R.id.left_Player);
        rightPlayer = (MyObjectView)findViewById(R.id.right_Player);
        theBall = (MyBallView)findViewById(R.id.theBall);

        theWalls.add((MyWallView)findViewById(R.id.LeftWall1));
        theWalls.get(theWalls.size()-1).wallRight = true;
        theWalls.get(theWalls.size()-1).wallBottom = true;
        theWalls.add((MyWallView)findViewById(R.id.LeftWall2));
        theWalls.get(theWalls.size()-1).wallRight = true;
        theWalls.get(theWalls.size()-1).wallTop = true;
        theWalls.add((MyWallView)findViewById(R.id.RightWall1));
        theWalls.get(theWalls.size()-1).wallLeft = true;
        theWalls.get(theWalls.size()-1).wallBottom = true;
        theWalls.add((MyWallView)findViewById(R.id.RightWall2));
        theWalls.get(theWalls.size()-1).wallLeft = true;
        theWalls.get(theWalls.size()-1).wallTop = true;
        theWalls.add((MyWallView)findViewById(R.id.TopWall));
        theWalls.get(theWalls.size()-1).wallBottom = true;
        theWalls.add((MyWallView)findViewById(R.id.BottomWall));
        theWalls.get(theWalls.size()-1).wallTop = true;

        winView[0] = (TextView) findViewById(R.id.textViewLeft);
        winView[1] = (TextView) findViewById(R.id.textViewRight);

        leftPlayer.setVisibility(View.INVISIBLE);
        rightPlayer.setVisibility(View.INVISIBLE);
        theBall.setVisibility(View.INVISIBLE);
        theBall.setRelativeObject(leftPlayer, rightPlayer, theWalls.get(0), theWalls.get(1), theWalls.get(2), theWalls.get(3), theWalls.get(4), theWalls.get(5));
        theBtn = (Button)findViewById(R.id.TheButton);
        theBtn2 = (Button)findViewById(R.id.TheButton2);
        theBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameEvent.sendEmptyMessage(MSG_GAMEINI);
            }
        });
        theBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onDestroy();
            }
        });
        winView[0].setVisibility(View.INVISIBLE);
        winView[1].setVisibility(View.INVISIBLE);
        //leftLayout.setOnTouchListener(new TouchMover(leftPlayer));

        gameEvent = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                //判断信息是否为本应用发出的
                switch (msg.what){
                    case MSG_FLASH:
                        //通知组件进行重绘
                        if(leftPlayer != null)
                            leftPlayer.invalidate();
                        if(rightPlayer != null)
                            rightPlayer.invalidate();
                        if(theBall != null)
                            theBall.invalidate();
                        break;
                    case MSG_GAMEINI:
                        leftPlayer.setVisibility(View.VISIBLE);
                        rightPlayer.setVisibility(View.VISIBLE);
                        theBall.setVisibility(View.VISIBLE);
                        leftPlayer.initial(MyObjectView.iniLocation.LEFT);
                        rightPlayer.initial(MyObjectView.iniLocation.RIGHT);
                        theBall.initial(MyObjectView.iniLocation.CENTER);
                        theBall.speedX = 0;
                        theBall.speedY = 0;
                        for(MyWallView wall : theWalls){
                            wall.initial();
                            Log.d("wall debug ", "(" + wall.getObjectBound(MyObjectView.WAY.LEFT) + " , " + wall.getObjectBound(MyObjectView.WAY.TOP));
                            Log.d("wall debug ", "  " + wall.theWidth + " , " + wall.theHeight + ")");
                        }
                        theBall.handler.sendEmptyMessage(MyBallView.MSG_RUN);
                        theBtn.setVisibility(View.INVISIBLE);
                        theBtn2.setVisibility(View.INVISIBLE);
                        winView[0].setVisibility(View.INVISIBLE);
                        winView[1].setVisibility(View.INVISIBLE);
                        break;
                    case MSG_LEFTWIN:
                        theBtn.setVisibility(View.VISIBLE);
                        theBtn2.setVisibility(View.VISIBLE);
                        winView[0].setVisibility(View.VISIBLE);
                        winView[0].setText(R.string.WIN);
                        winView[0].setTextColor(Color.WHITE);
                        winView[1].setVisibility(View.VISIBLE);
                        winView[1].setText(R.string.LOSE);
                        winView[1].setTextColor(Color.RED);
                        break;
                    case MSG_RIGHTWIN:
                        theBtn.setVisibility(View.VISIBLE);
                        theBtn2.setVisibility(View.VISIBLE);
                        winView[1].setVisibility(View.VISIBLE);
                        winView[1].setText(R.string.WIN);
                        winView[1].setTextColor(Color.WHITE);
                        winView[0].setVisibility(View.VISIBLE);
                        winView[0].setText(R.string.LOSE);
                        winView[0].setTextColor(Color.RED);
                        break;
                }
                super.handleMessage(msg);
            }
        };
        theBall.connect(gameEvent);

        timer.schedule(timeTask,20, 20);//flash task
    }

    protected class TouchMover implements View.OnTouchListener{
        MyObjectView controller;

        public TouchMover(){
            super();
            this.controller = null;
        }
        public TouchMover(MyObjectView controller){
            super();
            this.controller = controller;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(controller!= null){
                Log.d("onTouch", "(" +event.getX() + "," + event.getY() + ")");
                controller.locationX = event.getX();
                controller.locationY = event.getY();
                controller.invalidate();
            }
            return true;
        }
    }
}
