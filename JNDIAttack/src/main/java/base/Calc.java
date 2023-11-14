package base;

import java.io.Serializable;

/**
 * @author Whoopsunix
 */
public class Calc implements Serializable {
    static {
        try{
            Runtime.getRuntime().exec("open -a Calculator.app");
//            Runtime.getRuntime().exec("calc");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
