package imgc0312.appclassprojectgame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class TableHockeyActivity extends Activity {
    Button theBtn;
    FrameLayout leftLayout;
    MyObjectView leftPlayer;
    FrameLayout rightLayout;
    MyObjectView rightPlayer;
    MyBallView theBall;
    List<MyWallView> theWalls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_hockey);
        leftLayout = (FrameLayout)findViewById(R.id.layout_left);
        leftPlayer = (MyObjectView)findViewById(R.id.left_Player);
        rightPlayer = (MyObjectView)findViewById(R.id.right_Player);
        theBall = (MyBallView)findViewById(R.id.theBall);

        theWalls.add((MyWallView)findViewById(R.id.LeftWall));
        theWalls.add((MyWallView)findViewById(R.id.RightWall));
        theWalls.add((MyWallView)findViewById(R.id.TopWall));
        theWalls.add((MyWallView)findViewById(R.id.BottomWall));

        leftPlayer.setVisibility(View.INVISIBLE);
        rightPlayer.setVisibility(View.INVISIBLE);
        theBall.setVisibility(View.INVISIBLE);
        theBall.setRelativeObject(leftPlayer, rightPlayer, theWalls.get(0), theWalls.get(1), theWalls.get(2), theWalls.get(3));
        theBtn = (Button)findViewById(R.id.TheButton);
        theBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftPlayer.setVisibility(View.VISIBLE);
                rightPlayer.setVisibility(View.VISIBLE);
                theBall.setVisibility(View.VISIBLE);
                leftPlayer.initial(MyObjectView.iniLocation.LEFT);
                rightPlayer.initial(MyObjectView.iniLocation.RIGHT);
                theBall.initial(MyObjectView.iniLocation.CENTER);
                theBall.speedX = 10;
                theBall.speedY = 10;
                for(MyWallView wall : theWalls){
                    wall.initial();
                }
                theBall.handler.sendEmptyMessage(MyBallView.MSG_RUN);
                v.setVisibility(View.INVISIBLE);
            }
        });
        //leftLayout.setOnTouchListener(new TouchMover(leftPlayer));
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
