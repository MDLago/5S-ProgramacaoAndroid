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


    private String getNumString(int campo){
        switch(campo){
            case 1:
                return numero1.getText().toString().trim();
            case 2:
                return numero2.getText().toString().trim();
            default:
                return null;
        }
    }

    private boolean checkStringIsEmpty(String string){
        return string.isEmpty();
    }

    private float convertStringToFloat(String string){
        return Float.valueOf(string);
    }

    private void showResult(Float res){
        resultado.setText("Resultado: " + res);
    }

    private void exibirAlertaLongo(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void adicaoResultado (View v) {
        resultado.setText("");

        String num1String = getNumString(1);
        String num2String = getNumString(2);

        if(checkStringIsEmpty(num1String) || checkStringIsEmpty(num2String)){
            exibirAlertaLongo(getResources().getString(R.string.Campo_numero_vazio));
            return;
        }

        float num1 = convertStringToFloat(num1String);
        float num2 = convertStringToFloat(num2String);

        float res = num1 + num2;
        showResult(res);
    }

    public void subtracaoResultado (View v) {
        resultado.setText("");

        String num1String = getNumString(1);
        String num2String = getNumString(2);

        if(checkStringIsEmpty(num1String) || checkStringIsEmpty(num2String)){
            exibirAlertaLongo(getResources().getString(R.string.Campo_numero_vazio));
            return;
        }

        float num1 = convertStringToFloat(num1String);
        float num2 = convertStringToFloat(num2String);

        float res = num1 - num2;
        showResult(res);
    }

    public void subtracaoResultado (View v) {
        resultado.setText("");

        String num1String = getNumString(1);
        String num2String = getNumString(2);

        if(checkStringIsEmpty(num1String) || checkStringIsEmpty(num2String)){
            exibirAlertaLongo(getResources().getString(R.string.Campo_numero_vazio));
            return;
        }

        float num1 = convertStringToFloat(num1String);
        float num2 = convertStringToFloat(num2String);

        float res = num1 * num2;
        showResult(res);
    }

    public void subtracaoResultado (View v) {
        resultado.setText("");

        String num1String = getNumString(1);
        String num2String = getNumString(2);

        if(checkStringIsEmpty(num1String) || checkStringIsEmpty(num2String)){
            exibirAlertaLongo(getResources().getString(R.string.Campo_numero_vazio));
            return;
        }

        float num1 = convertStringToFloat(num1String);
        float num2 = convertStringToFloat(num2String);

        float res = num1 / num2;
        showResult(res);
    }
}
