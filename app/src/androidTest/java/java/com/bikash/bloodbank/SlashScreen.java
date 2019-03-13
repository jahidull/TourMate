package java.com.bikash.bloodbank;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lazy_programmer.tourmate.R;
import com.google.firebase.auth.FirebaseAuth;

public class SlashScreen extends AppCompatActivity {
  private Button btn;
  FirebaseAuth mAuth;
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashTread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);
        StartAnimations();
        mAuth = FirebaseAuth.getInstance();

//        mAuth = ((FirebaseApplication)getApplication()).getFirebaseAuth();
      //  ((FirebaseApplication)getApplication()).checkUserLogin(SlashScreen.this);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3000) {
                        sleep(150);
                        waited += 200;
                    }

                    Intent intent = new Intent(SlashScreen.this,
                            LogIn.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SlashScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SlashScreen.this.finish();
                }

            }
        };
        splashTread.start();

    }
}
