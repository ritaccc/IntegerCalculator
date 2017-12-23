package com.integercalculator.ritaccc.integercalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7,
            btn8, btn9, btnPlus, btnMinus, btnMult, btnDiv, btnClear, btnEqual;

    TextView mScreen;
    Integer mFirstVal, mSecondVal;
    String mLastOps, mCurOps;
    boolean mAfterOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnEqual = (Button) findViewById(R.id.btnEqual);
        mScreen = (TextView) findViewById(R.id.textView);
        mScreen.setText("");

        setOnClickNumber(btn0, "0");
        setOnClickNumber(btn1, "1");
        setOnClickNumber(btn2, "2");
        setOnClickNumber(btn3, "3");
        setOnClickNumber(btn4, "4");
        setOnClickNumber(btn5, "5");
        setOnClickNumber(btn6, "6");
        setOnClickNumber(btn7, "7");
        setOnClickNumber(btn8, "8");
        setOnClickNumber(btn9, "9");

        setOnClickOperators(btnPlus, "+");
        setOnClickOperators(btnMinus, "-");
        setOnClickOperators(btnMult, "*");
        setOnClickOperators(btnDiv, "/");
        setOnClickOperators(btnEqual, "=");

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Everything is reset to null.
               clear();
            }
        });
    }

    public void clear() {
        mFirstVal = null;
        mLastOps = null;
        mCurOps = null;
        mAfterOps = false;
        mScreen.setText("");
    }

    public void setOnClickNumber(final Button btn, final String number) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mScreen.getText() + "").equals("OVERFLOW")
                        || (mScreen.getText() + "").equals("Error")) {
                    clear();
                }
                // if there is no number before * or /, show error
                if (mFirstVal == null
                        && mCurOps != null
                        && (mCurOps.equals("*") || mCurOps.equals("/"))) {
                    mScreen.setText("Error");
                    return;
                }
                // If number typed right after operator, clear the screen.
                if (mAfterOps) {
                    if (mCurOps.equals("=")) {
                       clear();
                    } else {
                        mScreen.setText(null);
                    }
                    mAfterOps = false;
                }
                // Handle the case when leading number is 0.
                String lastText = mScreen.getText() + "";
                if (lastText.equals("0")) {
                    lastText = "";
                }
                String currentText = lastText + number;
                if (Integer.parseInt(currentText) > 9999999
                        || Integer.parseInt(currentText) < -9999999) {
                    mScreen.setText("OVERFLOW");
                    return;
                }
                mScreen.setText(currentText);
            }
        });
    }

    public void setOnClickOperators(final Button btn, final String op) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mScreen.getText() + "").equals("OVERFLOW")
                        || (mScreen.getText() + "").equals("Error")) {
                    clear();
                }
                // Handle the situation when there is no number typed before * or /
                if ((mScreen.getText() + "").equals("")) {
                    if (op.equals("*") || op.equals("/")) {
                        mFirstVal = null;
                    } else if (op.equals("+") || op.equals("-") || op.equals("=")) {
                        mFirstVal = 0;
                    }
                } else {
                    int currentText = Integer.parseInt(mScreen.getText() + "");
                    if (mFirstVal == null) {
                        mFirstVal = currentText;
                    } else {
                        mLastOps = mCurOps;
                        if (!mAfterOps) {
                            // If one operation if right after another, then skip operate method call
                            try {
                                mFirstVal = (int) operate(mFirstVal, currentText, mLastOps);
                                if (mFirstVal > 9999999 || mFirstVal <-9999999) {
                                    mScreen.setText("OVERFLOW");
                                    return;
                                }
                            } catch (Exception ex) {
                                mScreen.setText("Error");
                                return;
                            }
                            mScreen.setText(String.valueOf(mFirstVal));
                            mLastOps = null;
                        }
                    }
                }
                mCurOps = op;
                mAfterOps = true;
            }
        });
    }

    public Object operate(Integer a, Integer b, String op) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    return "Error";
                } else {
                    // Meet the requirement of -5 / 2 = -3
                    if (a < 0) {
                        return (int)((float)a / b - 0.5);
                    } else {
                        return (int)((float)a / b + 0.5);
                    }
                }
        }
        return "Error";
    }
}