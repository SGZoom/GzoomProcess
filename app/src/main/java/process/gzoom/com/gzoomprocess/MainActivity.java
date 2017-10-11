package process.gzoom.com.gzoomprocess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.GZBindView;
import com.example.GZClickView;

import process.gzoom.com.appapi.GZoomViewBinder;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @GZBindView(R.id.textView)
    TextView textView;

    @GZBindView(R.id.button)
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GZoomViewBinder.bind(this);
        textView.setText("Gzoom's annotation compiler");
    }
    /**自定义-给button设定监听器*/
    @GZClickView(R.id.button)
    public void buttonClick()
    {
        Log.d("gzoom","this is button,help me!");
    }



    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
