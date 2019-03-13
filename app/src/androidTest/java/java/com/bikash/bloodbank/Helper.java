package java.com.bikash.bloodbank;


import android.content.Context;
import android.widget.Toast;

public class Helper {

    public static final String NAME = "Name";

    public static final String EMAIL = "Email";

    public static final String GENDER = "Gender";

    public static final String AGE = "Age";

    public static final String WEIGHT = "Weight";

    public static final String HIGHT = "Hight";

    public static final int SELECT_PICTURE = 2000;

    public static boolean isValidEmail(String email){
        if(email.contains("@")){
            return true;
        }
        return false;
    }

    public static void displayMessageToast(Context context, String displayMessage){
        Toast.makeText(context, displayMessage, Toast.LENGTH_LONG).show();
    }
}
