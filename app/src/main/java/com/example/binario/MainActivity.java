package com.example.binario;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText inputTop, inputBottom;
    private TextView outBin, outDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Set up edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputTop = findViewById(R.id.inputTop);
        inputBottom = findViewById(R.id.inputBottom);
        outBin = findViewById(R.id.outBin);
        outDec = findViewById(R.id.outDec);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonSubtract = findViewById(R.id.buttonSubtract);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);

        buttonAdd.setOnClickListener(v -> performOperation('+'));
        buttonSubtract.setOnClickListener(v -> performOperation('-'));
        buttonMultiply.setOnClickListener(v -> performOperation('*'));
    }

    private void performOperation(char operation) {
        String binaryTop = inputTop.getText().toString();
        String binaryBottom = inputBottom.getText().toString();

        if (binaryTop.isEmpty() || binaryBottom.isEmpty()) {
            Toast.makeText(this, "Please enter both binary numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int num1 = BinToDec(binaryTop); 
            int num2 = BinToDec(binaryBottom);

            int result = 0;
            switch (operation) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
            }

            outBin.setText(result == 0 ? "0" : DecToBin(result));
            outDec.setText(String.valueOf(result));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid binary input", Toast.LENGTH_SHORT).show();
        }
    }

    public int BinToDec(String bin) {
        int dec = 0;
        int base = 1;
        int len = bin.length();
        for (int i = len - 1; i >= 0; i--) {
            if (bin.charAt(i) == '1') {
                dec += base;
            }
            base *= 2;
        }
        return dec;
    }

    public String DecToBin(int dec) {
        StringBuilder bin = new StringBuilder();
        while (dec > 0) {
            bin.insert(0, dec % 2);
            dec /= 2;
        }
        return bin.toString();
    }
}