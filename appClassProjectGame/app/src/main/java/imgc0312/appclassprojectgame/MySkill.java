package imgc0312.appclassprojectgame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/14.
 */

public class MySkill {
    public List<MyFormula> outFormula = new ArrayList<MyFormula>();
    public List<MyFormula> inFormula = new ArrayList<MyFormula>();
    public String name = "null";
    public String text = "null";

    MySkill(String name, String text){
        this.name = name;
        this.text = text;
    }
    public MySkill clone(){
        MySkill copy = new MySkill(name, text);
        for (int i=0; i<3 ;i++){
            copy.outFormula.set(i,outFormula.get(i).clone());
            copy.inFormula.set(i,inFormula.get(i).clone());
        }
        return copy;
    }
}