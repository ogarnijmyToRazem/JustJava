package com.example.android.justjava;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        EditText nameText = (EditText) findViewById(R.id.name_view);
        String name = nameText.getText().toString();
        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject) + name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     * /* @param quantity is the number of cups of coffee ordered
     * /* @param priceForOneCup the price of 1 cup
     */
    private int calculatePrice(boolean hasChocolate, boolean hasWhippedCream) {
        int pricePerOneCup = 5;
        if (hasChocolate) {
            pricePerOneCup += 2;
        }
        if (hasWhippedCream) {
            pricePerOneCup += 1;
        }
        return quantity * pricePerOneCup;
    }

    public void composeEmail(String subject, String text) {
    }

    /**
     * @param price           price of an order
     * @param hasWhippedCream check if whipped cream was ordered
     * @param hasChocolate    check if chocolate was ordered
     * @return message for order
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String addWhippedCream;
        String addChocolate;
        if(hasWhippedCream){
            addWhippedCream=getString(R.string.yes);
        } else {
            addWhippedCream=getString(R.string.no);
        }
        if(hasChocolate){
            addChocolate=getString(R.string.yes);
        } else {
            addChocolate=getString(R.string.no);
        }
        String message = getString(R.string.order_summary_name,name) +
                "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream) +
                "\n" + getString(R.string.order_summary_chocolate,addChocolate) +
                "\n" + getString(R.string.order_summary_quantity,quantity) +
                "\n" + getString(R.string.order_summary_total,price) +
                "\n" + getString(R.string.thank_you);
        return message;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, R.string.increment_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, R.string.decrement_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}
