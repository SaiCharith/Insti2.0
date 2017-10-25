package iitbombay.code_catalyst.com.insti20;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static java.lang.Thread.sleep;

/**
 * First Activity That is instantiated after opening the App which Codes the Animation in The Splashscreen and after waiting for 3.5 seconds directs to {@link MainActivity}
 * @author Code-Catalyst
 */
public class Splashscreen extends Activity {
    /**
     * Set format of window when openend
     */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /**
     * Stores the Thread which Instantiate the Splashscreen
     */
    Thread splashTread;

    /**
     * Starts the animation as soon as this activity is created and calls the function StartAnimation after setting the view to xml file activity splashscreen
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        StartAnimations();
    }

    /**
     * The Main Function of this activity which is called from onCreate method which starts the animation and take us to the {@link MainActivity}
     */
    private void StartAnimations() {
        /**
         * The animation object that loads the already created R.anim.alpha file in the java activity
         */
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        /**
         * Object of type ImageView that loads the object that has to be animated i.e. the object with id splash having the text of "Insti 2.0"
         */
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);


        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splashscreen.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();

    }

}



