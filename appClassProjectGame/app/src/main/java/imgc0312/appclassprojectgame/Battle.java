package imgc0312.appclassprojectgame;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Battle extends Activity {
    final static MySkill skill1 = new MySkill("skill 1", "default: this is <skill 1> \n A attack skill."){{
        outFormula.add(new MyFormula(1.0, "HP", -1.0, "ATK", -5.0));
    }};
    final static MySkill skill2 = new MySkill("skill 2", "default: this is <skill 2> \n A defence skill."){{
        inFormula.add(new MyFormula(0.5, "DEF", 0.0, "", 200.0));
    }};
    final static MySkill skill3 = new MySkill("skill 3", "default: this is <skill 3> \n A nirvana skill."){{
        inFormula.add(new MyFormula(1.0, "CHARGE", 0.0, "", -1.0));
        outFormula.add(new MyFormula(1.0, "HP", -2.5, "ATK", 0.0));
    }};
    android.content.Context ContentSpace;
    Button menuButton;
    Button setButton;
    TextView declareView;
    TextView namePlayer, nameAccident, nameEnemy;
    TextView infoPlayer,infoEnemy;
    LinearLayout battleScroll;
    TextView textModel;
    Button skillBt1,skillBt2,skillBt3;

    static final int msgSkill = 0x411;
    public Handler runHandle ;

    MyRole player;
    MyRole enemy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        ContentSpace = this;
        menuButton = (Button)findViewById(R.id.MenuButton);
        menuButton.setOnClickListener(new goBack());
        setButton = (Button)findViewById(R.id.SetButton);

        declareView = (TextView)findViewById(R.id.declareView);

        namePlayer = (TextView) findViewById(R.id.namePlayer);
        nameAccident = (TextView) findViewById(R.id.nameAccident);
        nameEnemy = (TextView) findViewById(R.id.nameEnemy);

        infoPlayer = (TextView) findViewById(R.id.infoPlayer);
        infoEnemy = (TextView) findViewById(R.id.infoEnemy);
        battleScroll = (LinearLayout) findViewById(R.id.battleScroll);
        textModel = (TextView) findViewById(R.id.textModel);

        SkillBt skillBt = new SkillBt();
        skillBt1 = (Button) findViewById(R.id.skillBt1);
        skillBt1.setOnClickListener(skillBt);
        skillBt1.setOnLongClickListener(skillBt);
        skillBt2 = (Button) findViewById(R.id.skillBt2);
        skillBt2.setOnClickListener(skillBt);
        skillBt2.setOnLongClickListener(skillBt);
        skillBt3 = (Button) findViewById(R.id.skillBt3);
        skillBt3.setOnClickListener(skillBt);
        skillBt3.setOnLongClickListener(skillBt);

        player = new MyRole("player");
        player.skills.set(0,skill1);
        player.skills.set(1,skill2);
        player.skills.set(2,skill3);
        infoPlayer.setText(player.info.normalText());
        enemy = new MyRole("enemy");
        enemy.skills.set(0,skill1);
        enemy.skills.set(1,skill2);
        enemy.skills.set(2,skill3);
        infoEnemy.setText(enemy.info.normalText());

        runHandle = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case msgSkill:
                        //Log.d("test log" , "do right");
                        declareView.setText(msg.getData().get("") + "be used !! ");
                        INFO outEffect = new INFO(false);
                        String message = player.use(player.skills.get(msg.getData().getInt("select") - 1), outEffect);
                        //Log.d("test log" , "use do right");
                        TextView addText = buildText(message);
                        //Log.d("test log" , "buildText do right");
                        battleScroll.addView(addText);
                        //Log.d("test log" , "addView do right");
                        message = enemy.get(outEffect);
                        if(message.equals(""))
                            message = " ";
                        addText = buildText(message);
                        battleScroll.addView(addText);
                        //setContentView(battleScroll);
                        break;
                }
            }
        };

    }

    protected TextView buildText(String message){
        //Log.d("message","aa" + message);
        TextView newText = new TextView(ContentSpace);
        newText.setLayoutParams(textModel.getLayoutParams());
        //newText.setTextSize(textModel.getTextSize());
        newText.setTextColor(textModel.getTextColors());
        textModel.setGravity(Gravity.CENTER_HORIZONTAL);
        newText.setGravity(Gravity.CENTER_HORIZONTAL);
        newText.setText(message);
        return newText;
    }

    protected class goBack implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Battle.this.finish();
        }
    }

    protected class SkillBt implements View.OnClickListener,View.OnLongClickListener{
        @Override
        public void onClick(View v) {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            int select = 0;
            String message = "";
            switch (v.getId()){
                case R.id.skillBt1:
                    message += "skillBt 1";
                    select = 1;
                    break;
                case R.id.skillBt2:
                    message += "skillBt 2";
                    select = 2;
                    break;
                case R.id.skillBt3:
                    message += "skillBt 3";
                    select = 3;
                    break;
                default:
                    message += "unknown bt";
            }
            message += " 被按下 !!";
            msg.what = msgSkill;
            bundle.putString("",message);
            bundle.putInt("select",select);
            msg.setData(bundle);
            runHandle.sendMessage(msg);
            //declareView.setText(message);
        }

        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()){
                case R.id.skillBt1:
                    declareView.setText(player.skills.get(0).name + "\n" + player.skills.get(0).text);
                    break;
                case R.id.skillBt2:
                    declareView.setText(player.skills.get(1).name + "\n" + player.skills.get(1).text);
                    break;
                case R.id.skillBt3:
                    declareView.setText(player.skills.get(2).name + "\n" + player.skills.get(2).text);
                    break;
                default:
                    declareView.setText("Unknown Skill Button info");
            }
            return true;
        }
    }
}
