package com.example.ncpow.pi_remote;
/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p/>
 * package com.example.android.justjava;
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;

    /**
     * This method is called when the order button is clicked.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream) {
            basePrice += 1;
        }
        if ( addChocolate ) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    public void submitOrder(View view) {
        EditText name = (EditText) findViewById(R.id.name);
        String userName = name.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, userName, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));

        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + userName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void increment(View view) {
        if ( quantity == 100 ){
                Toast.makeText(this, "You cannot have more than 100 cups of coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }


    public void decrement(View view) {
        if (quantity == 1 ) {
            Toast.makeText(this, "You cannot have less than 1 cup of coffee!", Toast.LENGTH_SHORT).show();
        return;
        }
        quantity--;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private String createOrderSummary(int price, String userName,boolean addWhippedCream, boolean addChocolate ) {
        String priceMessage = "Name: " + userName;
        priceMessage += "\n" + getString(R.string.whipped_cream) + "? " + addWhippedCream;
        priceMessage += "\n" + getString(R.string.chocolate) + "? " + addChocolate;
        priceMessage += "\n" + getString(R.string.quantity) + ": " + quantity;
        priceMessage += "\n" + getString(R.string.total) + " $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}