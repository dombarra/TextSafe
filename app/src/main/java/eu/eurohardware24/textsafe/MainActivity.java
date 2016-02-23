package eu.eurohardware24.textsafe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String MY_BANNER_UNIT_ID = "ca-app-pub-8124355001128596/3272699996";
    private static final String MY_INTERSTITIAL_UNIT_ID = "ca-app-pub-8124355001128596/4749433190";
    final Context context = this;
    EditText Text;
    TextView textgroesse;
    ImageButton textkleiner, textgroesser;
    String text;
    Float Textgroesse;
    int tg;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    InputMethodManager imm;
    boolean openedSafe = true;
    Button SafeButton;
    LinearLayout layout, flayout;
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    private AdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (LinearLayout) findViewById(R.id.ll);
        flayout = (LinearLayout) findViewById(R.id.functionsLayout);
        flayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(Text.getWindowToken(), 0);
            }
        });

        adView = new AdView(this);
        adView.setAdUnitId(MY_BANNER_UNIT_ID);
        adView.setAdSize(AdSize.BANNER);

        AdRequest adRequest = new AdRequest.Builder().build();


        adView.loadAd(adRequest);
        layout.addView(adView);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(MY_INTERSTITIAL_UNIT_ID);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();
        settings = getSharedPreferences(PREFS_NAME, 0);
        Text = (EditText) findViewById(R.id.editText);
        textgroesse = (TextView) findViewById(R.id.textsize);
        Textgroesse = Text.getTextSize();
        Textgroesse = settings.getFloat("Textgroesse", Textgroesse);
        textkleiner = (ImageButton) findViewById(R.id.textsmallerButton);
        textgroesser = (ImageButton) findViewById(R.id.textbiggerButton);
        tg = textg(Textgroesse);
        textgroesse.setText("" + tg);
        Text.setTextSize(Textgroesse);
        text = settings.getString("Text", text);
        if (text != null) {
            Text.setText("" + text);
        }
        imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        textgroesser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Textgroesse < 40) {

                    Textgroesse = Textgroesse + 1;
                    Text.setTextSize(Textgroesse);
                    tg = textg(Textgroesse);
                    textgroesse.setText("" + tg);
                    imm.hideSoftInputFromWindow(Text.getWindowToken(), 0);
                }
            }
        });

        textkleiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Textgroesse > 1) {

                    Textgroesse = Textgroesse - 1;
                    Text.setTextSize(Textgroesse);
                    tg = textg(Textgroesse);
                    textgroesse.setText("" + tg);
                    imm.hideSoftInputFromWindow(Text.getWindowToken(), 0);
                }
            }
        });

        Text = (EditText) findViewById(R.id.editText);
        SafeButton = (Button) findViewById(R.id.safeButton);
        SafeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!openedSafe) {
                    openSafeDialog();
                } else {
                    closeSafeDialog();
                }

            }
        });


        openedSafe = settings.getBoolean("openedsafe", openedSafe);
        if (!openedSafe) {
            SafeButton.setText(R.string.open_safe);
            Text.setVisibility(View.GONE);
        }

    }

    public void openSafeDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle(R.string.open_safe);

        displayInterstitial();


        final EditText passwordEditText = (EditText) dialog.findViewById(R.id.password);
        Button okButton = (Button) dialog.findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password;
                password = passwordEditText.getText().toString();
                if (password != null && !password.isEmpty()) {
                    settings = getSharedPreferences(PREFS_NAME, 0);
                    editor = settings.edit();
                    String savedpassword = settings.getString("password", password);
                    if (savedpassword.equals(password)) {
                        Text.setVisibility(View.VISIBLE);
                        openedSafe = true;
                        editor.putBoolean("openedsafe", openedSafe);
                        editor.commit();
                        SafeButton.setText(R.string.close_safe);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.wrong_password, Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_password, Toast.LENGTH_LONG).show();
                }

            }
        });
        Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void closeSafeDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle(R.string.close_safe);

        displayInterstitial();


        final EditText passwordEditText = (EditText) dialog.findViewById(R.id.password);
        Button okButton = (Button) dialog.findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = "";
                password = passwordEditText.getText().toString();
                if (password != null && !password.isEmpty()) {
                    settings = getSharedPreferences(PREFS_NAME, 0);
                    editor = settings.edit();
                    editor.putString("password", password);
                    Text.setVisibility(View.GONE);
                    openedSafe = false;
                    editor.putBoolean("openedsafe", openedSafe);
                    editor.commit();
                    SafeButton.setText(R.string.open_safe);
                    dialog.dismiss();


                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_password, Toast.LENGTH_LONG).show();
                }
            }
        });
        Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    @Override
    protected void onStop() {
        super.onStop();
        text = Text.getText().toString();
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putString("Text", text);
        Textgroesse = Text.getTextSize();
        editor.putFloat("Textgroesse", Textgroesse);
        editor.commit();

    }

    @Override
    protected void onPause() {
        adView.pause();
        super.onPause();
        text = Text.getText().toString();
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putString("Text", text);
        Textgroesse = Text.getTextSize();
        editor.putFloat("Textgroesse", Textgroesse);
        editor.commit();


    }

    @Override
    public void onResume() {
        super.onResume();
        adView.resume();
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
        text = Text.getText().toString();
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putString("Text", text);
        Textgroesse = Text.getTextSize();
        editor.putFloat("Textgroesse", Textgroesse);
        editor.commit();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void displayInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()

                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public int textg(float a) {
        int b = (int) a;
        return b;
    }

}