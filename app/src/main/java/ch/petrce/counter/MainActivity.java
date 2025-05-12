package ch.petrce.counter;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button upButton;
    private Button downButton;
    private EditText countEditText;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        upButton = findViewById(R.id.upButton); // get upButton
        downButton = findViewById(R.id.downButton); // get downButton

        upButton.setOnClickListener(this); // onClickListener
        downButton.setOnClickListener(this); // onClickListener

        countEditText = findViewById(R.id.countEditText); // get numberfield
        SharedPreferences prefs = getSharedPreferences("counter_prefs", MODE_PRIVATE); // Persistente Daten welche über mehrere Sessions beibehalten werden
        int savedCount = prefs.getInt("count_value", 0); // Set default value to "0"
        countEditText.setText(String.valueOf(savedCount)); // set Value to numberfield

        Button resetButton = findViewById(R.id.resetButton); // get resetbutton  +  onClickListener for resetbutton
        resetButton.setOnClickListener(v -> {
            countEditText.setText("0"); // value to be set when button pressed
            SharedPreferences.Editor editor = getSharedPreferences("counter_prefs", MODE_PRIVATE).edit(); // get current SharedPreference
            editor.putInt("count_value", 0); // set value to zero
            editor.apply(); // save at the SharedPreference
        });

    }

    // Onclick event-listener
    @Override
    public void onClick(View view) {
        int current; // make empty int
        try {
            current = Integer.parseInt(countEditText.getText().toString()); // get current "current" value
        } catch (NumberFormatException e) {
            current = 0; // exeption
        }

        if (view.getId() == R.id.upButton) {
            current++; // if upButton pressed increase
        } else if (view.getId() == R.id.downButton) {
            current--; // if upButton pressed decrease
        }
        countEditText.setText(String.valueOf(current)); // set value of numberfield

        SharedPreferences.Editor editor = getSharedPreferences("counter_prefs", MODE_PRIVATE).edit(); // get current SharedPreference
        editor.putInt("count_value", current); // set value to "current"
        editor.apply(); // save at the SharedPreference

    }
}