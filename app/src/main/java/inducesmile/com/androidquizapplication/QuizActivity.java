package inducesmile.com.androidquizapplication;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuizActivity extends ActionBarActivity {

    private RadioGroup radioGroup;
    private RadioButton optionOne;
    private RadioButton optionTwo;
    private RadioButton optionThree;
    TextView question;

    JSONArray addonArray;
    ArrayList<String> addonsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        question= (TextView) findViewById(R.id.quiz_question);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        optionOne = (RadioButton) findViewById(R.id.radio0);
        optionTwo = (RadioButton) findViewById(R.id.radio1);
        optionThree = (RadioButton) findViewById(R.id.radio2);
        addonsList = new ArrayList<>();
        question.setText("who is your favourite poet?");
        showService();
    }

    private void showService() {
        StringRequest stringrequest = new StringRequest(Request.Method.GET,
                "http://nerdcastlebd.com/web_service/banglapoems/index.php/poets/all/format/json",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            addonArray = jsonObject.getJSONArray("poets");
                            for (int i = 0; i < addonArray.length(); i++) {

                                String addonsName = addonArray.getJSONObject(i)
                                        .getString("name");
                                final String biography = addonArray.getJSONObject(i)
                                        .getString("biography");
                                addonsList.add(addonsName);
                            }
                            optionOne.setText(addonsList.get(0));
                            optionTwo.setText(addonsList.get(1));
                            optionThree.setText(addonsList.get(2));


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });

        AppController.getInstance().addToRequestQueue(stringrequest);


    }


    private int getSelectedAnswer(int radioSelected) {

        int answerSelected = 0;
        if (radioSelected == R.id.radio0) {
            answerSelected = 1;
        }
        if (radioSelected == R.id.radio1) {
            answerSelected = 2;
        }
        if (radioSelected == R.id.radio2) {
            answerSelected = 3;
        }

        return answerSelected;
    }

    public void send(View view) {

        final String data = "poet=nazmul";
        //Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
        StringRequest request = new StringRequest(Request.Method.GET, "http://192.168.1.112/api/post.php?" + data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    private void uncheckedRadioButton() {
        optionOne.setChecked(false);
        optionTwo.setChecked(false);
        optionThree.setChecked(false);
        //optionThree.setChecked(false);

    }

}
