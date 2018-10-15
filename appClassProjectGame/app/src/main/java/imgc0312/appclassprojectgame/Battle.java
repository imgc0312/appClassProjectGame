package imgc0312.appclassprojectgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Battle extends Activity {
    final static MySkill skill1 = new MySkill("skill 1", "default: this is &lt;skill 1&rt; <br/> A attack skill."){{
        outFormula.add(new MyFormula(1.0, "HP", -1.0, "ATK", 0.0));
    }};
    final static MySkill skill2 = new MySkill("skill 2", "default: this is &lt;skill 2&rt; <br/> A defence skill."){{
        inFormula.add(new MyFormula(0.5, "DEF", 0.0, "", 200.0));
    }};
    final static MySkill skill3 = new MySkill("skill 3", "default: this is &lt;skill 3&rt; <br/> A nirvana skill."){{
        inFormula.add(new MyFormula(1.0, "CHARGE", 0.0, "", -1.0));
        outFormula.add(new MyFormula(1.0, "HP", -2.5, "ATK", 0.0));
    }};

    Button menuButton;
    Button setButton;
    TextView declareView;
    TextView namePlayer, nameAccident, nameEnemy;
    TextView infoPlayer,infoEnemy;
    LinearLayout battleScroll;
    Button skillBt1,skillBt2,skillBt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
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

        skillBt1 = (Button) findViewById(R.id.skillBt1);
        skillBt2 = (Button) findViewById(R.id.skillBt2);
        skillBt3 = (Button) findViewById(R.id.skillBt3);

        MyRole player = new MyRole("player");
        player.skills.set(0,skill1);
        player.skills.set(1,skill2);
        player.skills.set(2,skill3);
        MyRole enemy = new MyRole("enemy");
        enemy.skills.set(0,skill1);
        enemy.skills.set(1,skill2);
        enemy.skills.set(2,skill3);
    }

    protected class goBack implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Battle.this.finish();
        }
    }

    protected class SkillBt implements View.OnClickListener{
        @Override
        public void onClick(View v) {

        }
    }
}
