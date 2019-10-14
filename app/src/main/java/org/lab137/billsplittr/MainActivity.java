package org.lab137.billsplittr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    double subTotal;
    double taxRate;
    double tipRate;
    double costPerPerson;
    int splits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText numSubTotal = (EditText)findViewById(R.id.numSubTotal);
        final EditText numTaxRate = (EditText)findViewById(R.id.numTaxRate);
        final EditText numTipRate = (EditText)findViewById(R.id.numTipRate);
        final EditText numSplits = (EditText)findViewById(R.id.numSplits);

        Button btnSplit = (Button)findViewById(R.id.btnSplit);
        btnSplit.setOnClickListener(new View.OnClickListener() {
            final TextView results = ((TextView)findViewById(R.id.txtResults));
            @Override
            public void onClick(View view) {
                if (numSubTotal.length() > 0) {
                    numSubTotal.setError(null);
                    hideKeyboard((Button)view);
                    subTotal = Double.parseDouble(numSubTotal.getText().toString());
                    if (numTaxRate.length() > 0) {
                        taxRate = Double.parseDouble(numTaxRate.getText().toString()) / 100;
                    } else {
                        taxRate = 0.09;
                    }

                    if (numTipRate.length() > 0) {
                        tipRate = Double.parseDouble(numTipRate.getText().toString()) / 100;
                    } else {
                        tipRate = 0.15;
                    }

                    if (numSplits.length() > 0) {
                        splits = Integer.parseInt(numSplits.getText().toString());
                    } else {
                        splits = 2;
                    }

                    double tax = taxRate * subTotal;
                    double tip = subTotal * tipRate;
                    double total = subTotal + tax + tip;
                    double split = total / splits;

                    DecimalFormat currency = new DecimalFormat("$###,###.##");

                    String resultsStr = "Results\nTax: " + currency.format(tax) + "\n";
                    resultsStr += "Tip: " + currency.format(tip) + "\n";
                    resultsStr += "Total: " + currency.format(total) + "\n";
                    resultsStr += "Per person: " + currency.format(split);

                    results.setText(resultsStr);
                } else {

                    numSubTotal.setError("Please enter a subtotal");
                }
            }
        });
    }

    // From: https://stackoverflow.com/a/54583382/3562890
    public void hideKeyboard(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception ignored) {

        }
    }
}
