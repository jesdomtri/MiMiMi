package com.example.mimimi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText input;
    private TextView output;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        input = (TextInputEditText) findViewById(R.id.input);
        output = (TextView) findViewById(R.id.output);
        button = (Button) findViewById(R.id.button);
    }

    public void mimimi(View view) {
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        String minusculas = "aeou";
        String tildes = "áéóú";
        String mayusculas = "AEOU";

        char[] vocales1 = minusculas.toCharArray();
        char[] vocales2 = tildes.toCharArray();
        char[] vocales3 = mayusculas.toCharArray();

        char[] text = input.getText().toString().toCharArray();

        String res = "";

        for (char c : text) {
            for (char d : vocales1) {
                if (c == d) {
                    c = 'i';
                }
            }
            for (char d : vocales2) {
                if (c == d) {
                    c = 'í';
                }
            }
            for (char d : vocales3) {
                if (c == d) {
                    c = 'I';
                }
            }
            res += c;
        }
        if(res.equals("") && clipboard.hasPrimaryClip()){
            output.setText(clipboard.getPrimaryClip().toString());
        }else{
            output.setText(res);
        }
        output.setMovementMethod(new ScrollingMovementMethod());
        ClipData clip = ClipData.newPlainText("simple text", output.getText());
        clipboard.setPrimaryClip(clip);
        hideKeyboard();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
