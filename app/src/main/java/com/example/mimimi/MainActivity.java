package com.example.mimimi;

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

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

        String vocales_minusculas = "aáàäâãåąāeéèëêęēėoóòöôõōøuúùüûū";
        String vocales_mayusculas = "aáàäâãåąāeéèëêęēėoóòöôõōøuúùüûū".toUpperCase();

        char[] text = new char[0];
        if (!input.getText().toString().equals("")) {
            text = input.getText().toString().toCharArray();
        } else {
            if (clipboard.hasPrimaryClip()) {
                text = clipboard.getPrimaryClip().getItemAt(0).getText().toString().toCharArray();
            }
        }

        String res = "";

        for (char character : text) {
            if (vocales_minusculas.indexOf(character) != -1) {
                character = 'i';
            }
            if (vocales_mayusculas.indexOf(character) != -1) {
                character = 'I';
            }
            res += character;
        }
        output.setText(res);
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
