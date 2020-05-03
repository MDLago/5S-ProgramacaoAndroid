package br.univali.prog.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText numero1;
    private EditText numero2;

    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numero1 = findViewById(R.id.etNum1);
        numero2 = findViewById(R.id.etNum2);
        resultado = findViewById(R.id.tvResultado);
    }

    public void adicaoResultado (View v) {
        resultado.setText("");

        String num1String = numero1.getText().toString().trim();
        String num2String = numero2.getText().toString().trim();

        if (num1String.equals("") || num2String.equals("")){
            Toast.makeText(this, "Por favor, informar os dois números", Toast.LENGTH_LONG).show();
        } else {
            float res = Float.valueOf(num1String) + Float.valueOf(num2String);
            resultado.setText("Resultado: " + res);
        }
    }

    public void subtracaoResultado (View v) {
        resultado.setText("");

        String num1String = numero1.getText().toString().trim();
        String num2String = numero2.getText().toString().trim();

        if (num1String.equals("") || num2String.equals("")){
            Toast.makeText(this, "Por favor, informar os dois números", Toast.LENGTH_LONG).show();
        } else {
            float res = Float.valueOf(num1String) - Float.valueOf(num2String);
            resultado.setText("Resultado: " + res);
        }
    }
}
