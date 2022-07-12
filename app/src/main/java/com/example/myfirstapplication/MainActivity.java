package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView resultField;
    private TextView operationField;
    private TextView numberField;//пользовательский элемент
    Double operand = null;          //аргумент
    String lastOperation = "=";     //последняя операция

    // сохранение данных
    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putString("OPERATION", lastOperation);
        if(operand!=null)
            instanceState.putDouble("OPERAND", operand);
    }

    //Восстановление данных
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getParcelable("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultField = findViewById(R.id.resultField);
        operationField = findViewById(R.id.operationField);
        numberField = findViewById(R.id.numberField);
    }

    public void Number_onClick(View view) {
        Button button = (Button) view;
        numberField.append(button.getText());
        if (lastOperation.equals("=") && operand != null) { operand = null; }
    }

    public void Operation_onClick(View view) {
        Button button = (Button) view;
        String oper = button.getText().toString();
        String number = numberField.getText().toString();

        if (number.length() > 0) {
            number = number.replace(",", ".");
            try { performOperation(Double.valueOf(number), oper); }
            catch (NumberFormatException ex) { numberField.setText(""); }
        }
        lastOperation = oper;
        operationField.setText(lastOperation);
    }

    public void performOperation(Double number, String operation) {
        if (operand == null) { operand = number; }
        else {
            if (lastOperation.equals("=")) { lastOperation = operation; }
            switch (lastOperation) {
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number == 0) { operand = 0.0; }
                    else { operand /= number; }
                    break;
                case "×":
                    operand *= number;
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;
                case "%":
                    operand %= number;
                    break;
                case "C":
                    operand *= 0;
                    break;
            }
        }
        resultField.setText(operand.toString().replace(".", ","));
        numberField.setText("");
    }
}