package imgc0312.appclassprojectgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {//AppCompatActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = (Button)findViewById(R.id.TheButton);
        startButton.setOnClickListener(new GameStart());
        Button loadButton = (Button)findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new GameStart());
        Button exitButton = (Button)findViewById(R.id.ExitButton);
        exitButton.setOnClickListener(new EXIT());
    }

    protected class GameStart implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent().setClass(MainActivity.this, TableHockeyActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    protected class EXIT implements View.OnClickListener{
        public void onClick(View v) {
            finish();
            onDestroy();
            System.exit(0);
        }
    }
}
