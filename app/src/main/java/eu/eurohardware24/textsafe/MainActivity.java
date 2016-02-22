package eu.eurohardware24.textsafe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    final Context context = this;
    Button SafeButton;
    EditText text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.editText);
        SafeButton = (Button) findViewById(R.id.safeButton);
        SafeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Close Safe");

                // set the custom dialog components - text, image and button


                Button okButton = (Button) dialog.findViewById(R.id.okButton);
                // if button is clicked, close the custom dialog
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });
                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                // if button is clicked, close the custom dialog
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        createDialog();
    }

    public void createDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Open Safe");

        // set the custom dialog components - text, image and button


        Button okButton = (Button) dialog.findViewById(R.id.okButton);
        // if button is clicked, close the custom dialog
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });
        dialog.show();

    }


}