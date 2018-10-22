package imgc0312.appclassprojectgame;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/14.
 */

public class INFO extends HashMap<String, Double>{
    public final static double iniLv = 1.0;
    public final static double iniMAX_Hp = 400.0;
    public final static double iniATK = 35.0;
    public final static double iniMAX_DEF = 100.0;
    public final static double iniMAX_CHARGE = 3.0;

    INFO(boolean initial){
        super();
        if(initial){
            put("Lv", iniLv);
            put("Hp", iniMAX_Hp);
            put("MAX_Hp", iniMAX_Hp);
            put("Ori_ATK", iniATK);
            put("Buf_ATK", 0.0);
            put("DEF", 0.0);
            put("MAX_DEF", iniMAX_DEF);
            put("CHARGE", 0.0);
            put("MAX_CHARGE", iniMAX_CHARGE);
            put("Exp", 0.0);
            put("MAX_Exp", 1.0);
        }
    };

    public String normalText(){
        return new String(
                "Lv\t\t\t\t\t\t\t:\t\t\t" + this.get("Lv") + "\n" +
                "Hp\t\t\t\t\t\t:\t\t\t" + this.get("Hp") + "\n" +
                "ATK\t\t\t\t\t:\t\t\t" + (this.get("Ori_ATK") + this.get("Buf_ATK")) + "\n" +
                "DEF\t\t\t\t\t:\t\t\t" + this.get("DEF") + "\n" +
                "CHARGE\t:\t\t\t" + this.get("CHARGE") + "\n" +
                "Exp\t\t\t\t\t:\t\t\t" + this.get("Exp")
        );
    }

    public boolean effect(INFO event){
        if(event.containsKey("CHARGE"))
            if ((get("CHARGE") + event.get("CHARGE")) < 0.0)
                return false;

        for (String oneKey:event.keySet()) {
            if(containsKey(oneKey))
                put(oneKey, get(oneKey) + event.get(oneKey));
        }

        if(get("DEF") < 0.0)
            put("DEF", 0.0);
        else if(get("DEF") >= get("MAX_DEF"))
            put("DEF", get("MAX_DEF"));

        if(get("CHARGE") >= get("MAX_CHARGE"))
            put("CHARGE", get("MAX_CHARGE"));

        if(get("Exp") < 0.0)
            put("Exp", 0.0);
        else if(get("Exp") >= get("MAX_Exp")) {
            put("Exp", 0.0);
            put("Lv", get("Lv") + 1.0);
            flash();
        }

        if(get("Hp") < 0.0)
            put("Hp", 0.0);
        else if(get("Hp") >= get("MAX_Hp"))
            put("Hp", get("MAX_Hp"));

        return true;
    }

    public void flash(){
        double Lv = get("Lv");

        double newMAX_Hp = get("MAX_Hp") * Lv;
        put("Hp", newMAX_Hp - get("MAX_Hp"));
        put("MAX_Hp", newMAX_Hp);

        put("Ori_ATK", iniATK + 5 * Lv - 5);

        put("MAX_DEF", iniMAX_DEF  + 50 * Lv - 50);

        put("MAX_CHARGE" , iniMAX_CHARGE + Lv - 1);

        put("MAX_Exp", 2 * Lv - 1);
    }
}
