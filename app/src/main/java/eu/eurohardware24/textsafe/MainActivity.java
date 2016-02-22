package eu.eurohardware24.textsafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText text = (EditText) findViewById(R.id.editText);
        text.setVisibility(View.GONE);
    }


}