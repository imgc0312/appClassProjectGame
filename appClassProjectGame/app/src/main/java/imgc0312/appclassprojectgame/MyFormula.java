package imgc0312.appclassprojectgame;

import android.util.Log;

import java.util.Random;

/**
 * Created by Administrator on 2018/10/15.
 */

public class MyFormula {
    static Random RD = new Random();
    double r;   //rate
    String T;   //target
    double n;
    String A;
    double c;
    //T = nA + c
    MyFormula(double rate, String target, double n, String Arg, double c){
        this.r = rate;
        this.T = target;
        this.n = n;
        this.A = Arg;
        this.c = c;
    }
    public double count(INFO info) throws Exception {
        if (RD.nextDouble() > r ) {
            Log.d("countRD"," " + r);
            return 0.0;
        }
        if(A.equals("")) {
            Log.d("count A"," equals null");
            return c;
        }
        else if(A.equals("ATK")){
            return (n * info.get("Ori_ATK") + info.get("Buf_ATK") + c);
        }
        else if(!info.containsKey(A))
            throw new Exception("count: !info.containsKey " + A);
        return (n * info.get(A) + c);
    }
    public MyFormula clone(){
        return new MyFormula(r,T,n,A,c);
    }
}
