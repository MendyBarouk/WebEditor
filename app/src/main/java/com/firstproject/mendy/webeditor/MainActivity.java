package com.firstproject.mendy.webeditor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String WEB_STRING = "com.firstproject.mendy.webeditor.WEB_STRING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView htmlTextView = (TextView) findViewById(R.id.activity_main_html_text_view);
        htmlTextView.setTag("html");
        TextView cssTextView = (TextView) findViewById(R.id.activity_main_css_text_view);
        cssTextView.setTag("css");
        TextView javaScriptTextView = (TextView) findViewById(R.id.activity_main_java_script_text_view);
        javaScriptTextView.setTag("javaScript");


        ArrayList<String> htmltagList = getResourcesStringArrayHtml();

        //String[] htmlTag = getResources().getStringArray(R.array.html);
        Log.d("Mendy : ", htmltagList.toString());

        MultiAutoCompleteTextView autoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.fragment_html_multiautocompletetextview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, htmltagList);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.Tokenizer() {
            @Override
            public int findTokenStart(CharSequence charSequence, int cursor) {
                int i = cursor;

                while (i > 0 && charSequence.charAt(i - 1) != ' ') {
                    i--;
                }
                while (i < cursor && charSequence.charAt(i) == ' ') {
                    i++;
                }
                Log.d("Mendy", "findTokenStart(CharSequence charSequence, int cursor) : " +
                        "charSequence=" + charSequence + " cursor=" + cursor + " i="+i);

                return i;
            }

            @Override
            public int findTokenEnd(CharSequence charSequence, int cursor) {
                int i = cursor;
                int len = charSequence.length();

                while (i < len) {
                    if (charSequence.charAt(i) == ' ') {
                        return i;
                    } else {
                        i++;
                    }
                }

                Log.d("Mendy", "findTokenEnd(CharSequence charSequence, int cursor) : " +
                        "charSequence=" + charSequence + " cursor=" + cursor + cursor + " i="+i);
                return len;
            }

            @Override
            public CharSequence terminateToken(CharSequence charSequence) {
                Log.d("Mendy", "terminateToken(CharSequence charSequence : " +
                        "charSequence=" + charSequence);
                int i = charSequence.length();

                while (i > 0 && charSequence.charAt(i - 1) == ' ') {
                    i--;
                }

                if (i > 0 && charSequence.charAt(i - 1) == ' ') {
                    return charSequence;
                } else {
                    if (charSequence instanceof Spanned) {
                        SpannableString sp = new SpannableString(charSequence + " ");
                        TextUtils.copySpansFrom((Spanned) charSequence, 0, charSequence.length(),
                                Object.class, sp, 0);
                        return sp;
                    } else {
                        return charSequence + " ";
                    }
                }
            }
        });

        htmlTextView.setOnClickListener(this);
        cssTextView.setOnClickListener(this);
        javaScriptTextView.setOnClickListener(this);
    }

    private ArrayList<String> getResourcesStringArrayHtml() {
        String[] htmlTagArr = getResources().getStringArray(R.array.html);
        ArrayList<String> htmlTagList = new ArrayList<>();
        for (String htmlTag:htmlTagArr) {
            htmlTagList.add(htmlTag);
        }
        return htmlTagList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        WebString webString = new WebString(((EditText) findViewById(R.id.fragment_html_multiautocompletetextview)).getText().toString(),
                ((EditText) findViewById(R.id.fragment_css_edit_text)).getText().toString(),
                ((EditText) findViewById(R.id.fragment_java_script_edit_text)).getText().toString());

        Intent intent = new Intent(this, RunActivity.class);
        intent.putExtra(WEB_STRING, webString);
        startActivity(intent);
        return true;
    }

    @Override
    public void onClick(View view) {
        LinearLayout.LayoutParams firstParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        firstParams.weight = 1;
        LinearLayout.LayoutParams secondParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        secondParams.weight = 0;
        switch ((String) view.getTag()) {
            case "html": {
                //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                getFragmentManager().findFragmentById(R.id.activity_main_html_fragment).getView().setLayoutParams(firstParams);
                getFragmentManager().findFragmentById(R.id.activity_main_css_fragment).getView().setLayoutParams(secondParams);
                getFragmentManager().findFragmentById(R.id.activity_main_java_scrpt_fragment).getView().setLayoutParams(secondParams);
                break;
            }
            case "css": {
                getFragmentManager().findFragmentById(R.id.activity_main_css_fragment).getView().setLayoutParams(firstParams);
                getFragmentManager().findFragmentById(R.id.activity_main_html_fragment).getView().setLayoutParams(secondParams);
                getFragmentManager().findFragmentById(R.id.activity_main_java_scrpt_fragment).getView().setLayoutParams(secondParams);
                break;
            }
            case "javaScript": {
                getFragmentManager().findFragmentById(R.id.activity_main_java_scrpt_fragment).getView().setLayoutParams(firstParams);
                getFragmentManager().findFragmentById(R.id.activity_main_html_fragment).getView().setLayoutParams(secondParams);
                getFragmentManager().findFragmentById(R.id.activity_main_css_fragment).getView().setLayoutParams(secondParams);
                break;
            }
        }
    }
}
