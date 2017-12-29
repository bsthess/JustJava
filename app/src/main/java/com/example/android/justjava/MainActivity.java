/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
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

//declaring static Strings for saved Bundle keynames


    public static final String QUANTITY_NUMBER = "quantity";
    public static final String DISPLAY_MESSAGE = "priceMessage";


    int quantity = 1;
    String priceMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            // Restore value of members from saved state

            quantity = savedInstanceState.getInt(QUANTITY_NUMBER);
            priceMessage = savedInstanceState.getString(DISPLAY_MESSAGE);

            displayQuantity(quantity);

        }


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
        sendEmail(nameText, priceMessage);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(QUANTITY_NUMBER, quantity);
        savedInstanceState.putString(DISPLAY_MESSAGE, priceMessage);
    }

    // Making the String for the display message

    private String createOrderSummary(String nameText, int price, boolean creamState, boolean chocoState) {
        String addCream = this.getResources().getString(R.string.WhippedCream);
        String addChoco = this.getResources().getString(R.string.Chocolate);
        String Name = this.getResources().getString(R.string.Name);
        String Quantity = this.getResources().getString(R.string.Quantity);
        String Total = this.getResources().getString(R.string.Total);
        String ThankYou = this.getResources().getString(R.string.ThankYou);

        if (creamState & chocoState) {
            priceMessage = Name + nameText + "\n" + addCream + "\n" + addChoco + "\n" + Quantity + quantity + "\n" + Total + price + "$" + "\n" + ThankYou;
            return priceMessage;
        }
        if (creamState & !chocoState) {
            priceMessage = Name + nameText + "\n" + addCream + "\n" + Quantity + quantity + "\n" + Total + price + "$" + "\n" + ThankYou;
            return priceMessage;
        }

        if (!creamState & chocoState) {
            priceMessage = Name + nameText + "\n" + addChoco + "\n" + Quantity + quantity + "\n" + Total + price + "$" + "\n" + ThankYou;
            return priceMessage;
        } else {
            priceMessage = Name + nameText + "\n" + Quantity + quantity + "\n" + Total + price + "$" + "\n" + ThankYou;
            return priceMessage;
        }

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


    public void sendEmail(String nameOfOrder, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order for " + nameOfOrder);
        intent.putExtra(Intent.EXTRA_TEXT, content);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}