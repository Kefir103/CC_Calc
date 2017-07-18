package com.example.cc_calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.cc_calc.R.id.numberButton_0;


public class MainActivity extends AppCompatActivity {
    protected Button[] buttons = new Button[16];
    protected int[] buttons_id = {numberButton_0, R.id.numberButton_1, R.id.numberButton_2,
            R.id.numberButton_3, R.id.numberButton_4, R.id.numberButton_5, R.id.numberButton_6,
            R.id.numberButton_7, R.id.numberButton_8, R.id.numberButton_9, R.id.numberButton_10,
            R.id.numberButton_11, R.id.numberButton_12, R.id.numberButton_13, R.id.numberButton_14,
            R.id.numberButton_15};
    public TextView textViewNumber, textViewResult;
    protected Button resultButton, clearButton;
    protected String stringNumber = "";
    protected String resultString = "";
    protected  int number = 0;
    protected int numberPosition = 0;
    protected Integer[] chosenCC = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    public Spinner spinnerIn, spinnerOut;
    private static final String ResultTAG = "ResultTAG";
    private static final String ButtonsTAG = "ButtonsTAG";



    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewNumber = (TextView) findViewById(R.id.textViewNumber);
        textViewResult = (TextView) findViewById(R.id.textViewResult);


        resultButton = (Button) findViewById(R.id.resultButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (Button) findViewById(buttons_id[i]);
            buttons[i].setOnClickListener(buttonsInOnClickListener);
        }
        resultButton.setOnClickListener(buttonsInOnClickListener);
        clearButton.setOnClickListener(buttonsInOnClickListener);


        spinnerIn = (Spinner) findViewById(R.id.startCC);
        spinnerOut = (Spinner) findViewById(R.id.endCC);
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, chosenCC);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIn.setAdapter(spinnerAdapter);
        spinnerOut.setAdapter(spinnerAdapter);
        spinnerIn.setSelection(8);
        spinnerIn.setOnItemSelectedListener(spinnerInOnItemSelectedListener);
        spinnerOut.setOnItemSelectedListener(spinnerOutOnItemSelectedListener);




    }

    OnItemSelectedListener spinnerInOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            for (int i = 0; i < buttons_id.length; i++) {
                buttons[i].setEnabled(false);
            }
            for (int i = 0; i < position+2; i++) {
                buttons[i].setEnabled(true);
            }
            stringNumber = "";
            textViewNumber.setText(textViewNumber.getHint());
            textViewNumber.setTextColor(textViewNumber.getHintTextColors());
        }

        @Override
        public void onNothingSelected (AdapterView<?> parent) {

        }
    };

    OnItemSelectedListener spinnerOutOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            resultString = "";
            numberPosition = position+2;
            textViewResult.setTextColor(textViewResult.getHintTextColors());
            textViewResult.setText(textViewResult.getHint());
        }

        @Override
        public void onNothingSelected (AdapterView<?> parent) {

        }
    };


    View.OnClickListener  buttonsInOnClickListener = new OnClickListener() {
        @Override
        public void onClick (View v) {
            switch (v.getId()) {
                case R.id.numberButton_0:
                    number *= 10;
                    number += 0;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber != "") {
                        stringNumber += "0";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_1:
                    number *= 10;
                    number += 1;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "1";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "1";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_2:
                    number *= 10;
                    number += 2;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "2";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "2";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_3:
                    number *= 10;
                    number += 3;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "3";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "3";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_4:
                    number *= 10;
                    number += 4;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "4";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "4";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_5:
                    number *= 10;
                    number += 5;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "5";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "5";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_6:
                    number *= 10;
                    number += 6;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "6";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "6";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_7:
                    number *= 10;
                    number += 7;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "7";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "7";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_8:
                    number *= 10;
                    number += 8;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "8";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "8";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_9:
                    number *= 10;
                    number += 9;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "9";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "9";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_10:
                    number *= 100;
                    number += 10;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "A";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "A";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_11:
                    number *= 100;
                    number += 11;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "B";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "B";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_12:
                    number *= 100;
                    number += 12;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "C";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "C";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_13:
                    number *= 100;
                    number += 13;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "D";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "D";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_14:
                    number *= 100;
                    number += 14;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));

                    if (stringNumber == "") {
                        stringNumber = "E";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "E";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_15:
                    number *= 100;
                    number += 15;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));

                    if (stringNumber == "") {
                        stringNumber = "F";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "F";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.resultButton:
                    textViewResult.setTextColor(getResources().getColor(android.R.color.black));
                    searchResult(numberPosition);
                    textViewResult.setText(resultString);
                    break;
                case R.id.clearButton:
                    stringNumber = "";
                    resultString = "";
                    number = 0;
                    textViewNumber.setText(textViewNumber.getHint());
                    textViewNumber.setTextColor(textViewNumber.getHintTextColors());
                    textViewResult.setText(textViewResult.getHint());
                    textViewResult.setTextColor(textViewResult.getHintTextColors());
                    Log.d(ResultTAG, stringNumber + " " + resultString + " " + number + " " + numberPosition);
                    Toast.makeText(MainActivity.this, "Result is clear", Toast.LENGTH_SHORT)
                          .show();
                    break;
            }

        }

    };

    protected String searchResult (int position){
        int result;
        Log.d(ResultTAG, "position: " + position);
        while (number != 0){
            result = number % position;
            Log.d(ResultTAG, "number: " + number);
            number /= position;
            Log.d(ResultTAG, "result: " + result);
            resultString = result + resultString;
            Log.d(ResultTAG, "resultString: " + resultString);
        }

        return resultString;
    }
}