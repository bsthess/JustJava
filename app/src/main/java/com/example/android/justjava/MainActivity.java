/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Context;
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
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */


    public void submitOrder(View view) {

        EditText personsName = (EditText) findViewById(R.id.plain_text_input);
        String nameText = personsName.getText().toString();
        CheckBox cream = (CheckBox) findViewById(R.id.checkbox_cream);
        CheckBox choco = (CheckBox) findViewById(R.id.checkbox_choco);
        boolean hasCream = cream.isChecked();
        boolean hasChoco = choco.isChecked();
        int price = calculatePrice(hasCream, hasChoco);
        String priceMessage = createOrderSummary(nameText, price, hasCream, hasChoco);
        displayMessage(priceMessage);


    }


    // Making the String for the display message

    private String createOrderSummary(String nameText, int price, boolean creamState, boolean chocoState) {
        String addCream = getResources().getString(R.string.AddWhippedCream);
        String addChoco = getResources().getString(R.string.Addchocolate);
        String Name = getResources().getString(R.string.Name);
        String Quantity = getResources().getString(R.string.Quantity);
        String Total = getResources().getString(R.string.Total);
        String Thankyou = getResources().getString(R.string.ThankYou);

        String priceMessage = Name + nameText + "\n" + addCream + creamState + "\n" + addChoco + chocoState + "\n" + Quantity + quantity + "\n" + Total + price + "$" + "\n" + Thankyou;
        return priceMessage;


    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean hasCream, boolean hasChoco) {
        int price = 5;
        int priceOfWhippedCream = 1;
        int priceOfChocolate = 2;
        if (hasCream) {
            price += priceOfWhippedCream;
        }
        if (hasChoco) {
            price += priceOfChocolate;
        }
        price = quantity * price;
        return price;
    }

    //display the message for the order
    public void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);

    }

    /**
     * The method that increments quantity
     */

    public void increment(View view) {
        quantity += 1;
        if (quantity > 100) {
            quantity = 100;
            Context context = getApplicationContext();
            CharSequence text = "Maximum order is 100 coffees!";
            int duration = Toast.LENGTH_SHORT;
// the toast message that informs that we can not take an order bigger than 100 coffees
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        displayQuantity(quantity);
    }

    /**
     * The method that decrements quantity
     */

    public void decrement(View view) {
        quantity -= 1;
        if (quantity < 1) {
            quantity = 1;
            Context context = getApplicationContext();
            CharSequence text = "Minimum order is 1 coffee!";
            int duration = Toast.LENGTH_SHORT;
// the toast message that informs that we can not take an order bigger than 100 coffees
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }
        displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void displayQuantity(int num) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }
}