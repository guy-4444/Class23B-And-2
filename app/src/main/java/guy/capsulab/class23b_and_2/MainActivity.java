package guy.capsulab.class23b_and_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

    private EditText main_EDT_name;
    private EditText main_EDT_gross;
    private MaterialButton main_BTN_calculate;
    private MaterialTextView main_LBL_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_EDT_name = findViewById(R.id.main_EDT_name);
        main_EDT_gross = findViewById(R.id.main_EDT_gross);
        main_BTN_calculate = findViewById(R.id.main_BTN_calculate);
        main_LBL_result = findViewById(R.id.main_LBL_result);

        main_BTN_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();
            }
        });
    }

    private void calculate() {

    }

}