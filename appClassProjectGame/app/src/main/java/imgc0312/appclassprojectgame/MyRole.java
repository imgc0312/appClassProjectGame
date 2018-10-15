package imgc0312.appclassprojectgame;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Administrator on 2018/10/14.
 */

public class MyRole {
    static Random rd = new Random();
    public String name = "null";
    public INFO info = new INFO(true);
    public List<MySkill> skills = new ArrayList <> (3);

    MyRole(String name){
        this.name = name;
    }

    public INFO use(MySkill choose, String message) {//generate choose effect by info & store ineffect message & return outEffect
        INFO inEffect = new INFO(false);
        INFO outEffect = new INFO(false);

        for (MyFormula one:choose.inFormula){
            try {
                double value = one.count(info);
                if(value == 0.0)
                    continue;
                if(inEffect.containsKey(one.T))
                    inEffect.put(one.T, inEffect.get(one.T) + value);
                else
                    inEffect.put(one.T, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (MyFormula one:choose.outFormula){
            try {
                double value = one.count(info);
                if(value == 0.0)
                    continue;
                if(outEffect.containsKey(one.T))
                    outEffect.put(one.T, inEffect.get(one.T) + value);
                else
                    outEffect.put(one.T, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        message = "";
        message += name + " 使用了 " + choose.name;
        for(String oneKey:inEffect.keySet()){
            message += "<br/>" + name + " 的 " + oneKey + " 產生 " + inEffect.get(oneKey) +" 的改變";
        }
        return  outEffect;
    }

    public void get(INFO effect, String message){// receive effect from outside & store the message
        message = "";
        if(!effect.isEmpty())
            message += name + " 受到了影響 ";
        for(String oneKey:effect.keySet()){
            message += "<br/>" + name + " 的 " + oneKey + " 受到 " + effect.get(oneKey) +" 的改變";
        }
    }
}
