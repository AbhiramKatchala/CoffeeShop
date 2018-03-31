package com.coffeeshop.coffeeshop;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {

        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.notify_me_checkbox);
        boolean checked = hasWhippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean isChecked = chocolate.isChecked();

        EditText editText = (EditText) findViewById(R.id.enter_name);
        String name = editText.getText().toString();


        int price = quantity * 5;

        if (checked) {
            int addWhippedCream = quantity;
            price += addWhippedCream;
        }
        if (isChecked) {
            int addChocolate = quantity * 2;
            price += addChocolate;
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, checked, isChecked, name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void display(int quantity) {
        TextView textView = (TextView) findViewById(R.id.quantity_text_view);
        textView.setText("" + quantity);
    }


    public String createOrderSummary(int price, boolean checked, boolean isChecked, String name) {

        String summary = getString(R.string.order_summary_name, name);
        summary += "\n" + getString(R.string.order_summary_whipped_cream) + checked;
        summary += "\n" + getString(R.string.order_summary_chocolate) + isChecked;
        summary += "\n" + getString(R.string.order_summary_quantity) + quantity;
        summary += "\n" + getString(R.string.order_summary_price) + NumberFormat.getCurrencyInstance().format(price);
        summary += "\n" + getString(R.string.thank_you);

        return summary;
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(MainActivity.this, "" + getString(R.string.increment_message), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        display(quantity);
    }

    public void decrement(View view) {

        if (quantity == 1) {
            Toast.makeText(MainActivity.this, "" + getString(R.string.decrement_message), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        display(quantity);
    }


}
