package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view){
        if(quantity==100){
            Toast.makeText(this,"You cannot have mor than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity  = quantity + 1;
        displayQuantity(quantity);
    }
    public void decrement(View view){
        if(quantity==1){
            Toast.makeText(this,"You cannot have less than 1 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {
        CheckBox chocolateCheckBox =(CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();
        Log.v("MainActivity","Has chocolate:"+hasChocolate);

        CheckBox whippedCreamCheckBox =(CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedcream=whippedCreamCheckBox.isChecked();
        Log.v("MainActivity","Has whipped cream:"+hasWhippedcream);

        EditText editName =(EditText)findViewById(R.id.name);
        String name=editName.getText().toString();
        Log.v("MainActivity","Name: "+name);

        int price=calculatePrice(hasChocolate,hasWhippedcream);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_email_subject,name));
        intent.putExtra(Intent.EXTRA_TEXT,createOrderSummary(price,hasWhippedcream,hasChocolate,name));

        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    /**
     * Create summary of the order.
     *
     *
     */
    private String createOrderSummary(int price,boolean whippedCream,boolean chocolate,String name){
        String show=getString(R.string.order_summary_name,name);
        show+="\n"+getString(R.string.order_summary_whipped_cream,whippedCream);
        show+="\n"+getString(R.string.order_summary_chocolate,chocolate);
        show+="\n"+getString(R.string.order_summary_quantity,quantity);
        show+="\n"+getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        show+="\n"+getString(R.string.thank_you);
        return show;
    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice(boolean chocolate,boolean whippedCream) {
        int  basePrice=5;

        if(chocolate){
            basePrice=basePrice+2;
        }

        if(whippedCream){
            basePrice=basePrice+1;
        }
        return quantity * basePrice;

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numbers) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numbers);
    }

}
