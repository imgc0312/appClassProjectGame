package imgc0312.appclassprojectgame;

import android.support.annotation.NonNull;
import android.util.Log;

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
    public List<MySkill> skills = new ArrayList <MySkill> (3);

    MyRole(String name){
        this.name = name;
        skills.add(new MySkill("null", "no skill"));
        skills.add(new MySkill("null", "no skill"));
        skills.add(new MySkill("null", "no skill"));
    }

    public String use(MySkill choose, INFO outEffect) {//generate choose effect by info & store ineffect message & return outEffect
        String message;
        INFO inEffect = new INFO(false);
        outEffect.clear();

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

        boolean useSuccess = info.effect(inEffect);
        if(!useSuccess)
            return (name + " CHARGE 不足 " + choose.name + " 失敗 ");

        for (MyFormula one:choose.outFormula){
            try {
                double value = one.count(info);
                if(value == 0.0) {
                    Log.d("skill use", "fail");
                    continue;
                }
                if(outEffect.containsKey(one.T))
                    outEffect.put(one.T, inEffect.get(one.T) + value);
                else
                    outEffect.put(one.T, value);
            } catch (Exception e) {
                Log.e("my code except", e.toString());
            }
        }

        message = "";
        message += name + " 使用了 " + choose.name;
        for(String oneKey:inEffect.keySet()) {
            message += "\n" + name + " 的 " + oneKey + " 產生 " + inEffect.get(oneKey) + " 的改變";
        }
        return  message;
    }

    public String get(INFO effect){// receive effect from outside & store the message
        info.effect(effect);
        String message = "";
        if(!effect.isEmpty())
            message += name + " 受到了影響 ";
        for(String oneKey:effect.keySet()){
            message += "\n" + name + " 的 " + oneKey + " 受到 " + effect.get(oneKey) +" 的改變";
        }
        return message;
    }
}
