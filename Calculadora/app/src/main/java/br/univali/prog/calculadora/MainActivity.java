package br.univali.prog.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText numero1;
    private EditText numero2;
    private ArrayList<String> resultados;
    private SQLiteDatabase db;

    private ListView lvResultado;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numero1 = findViewById(R.id.etNum1);
        numero2 = findViewById(R.id.etNum2);
        lvResultado = findViewById(R.id.lvResultado);

        resultados = new ArrayList<String>();
        adapter = historicoOperacoes(resultados);
        lvResultado.setAdapter(adapter);
    }

    public enum Operacao{
        ADICAO,SUBTRACAO,DIVISAO,MULTIPLICACAO;
    }

    private ArrayAdapter<String> historicoOperacoes(ArrayList resultados){
        return new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, resultados);
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

    private void addResult(float num1, float num2, Operacao op, Float res){
        switch (op){
            case ADICAO:
                resultados.add(num1 + " + " + num2 + " = " + res);
                break;
            case SUBTRACAO:
                resultados.add(num1 + " - " + num2 + " = " + res);
                break;
            case MULTIPLICACAO:
                resultados.add(num1 + " * " + num2 + " = " + res);
                break;
            case DIVISAO:
                resultados.add(num1 + " / " + num2 + " = " + res);
                break;
            default:
        }
        updateResult();
    }

    private void updateResult(){
        adapter.notifyDataSetChanged();
    }

    private void exibirAlertaLongo(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void adicaoResultado (View v) {

        String num1String = getNumString(1);
        String num2String = getNumString(2);

        if(checkStringIsEmpty(num1String) || checkStringIsEmpty(num2String)){
            exibirAlertaLongo(getResources().getString(R.string.Campo_numero_vazio));
            return;
        }

        float num1 = convertStringToFloat(num1String);
        float num2 = convertStringToFloat(num2String);

        float res = num1 + num2;
        addResult(num1,num2,Operacao.ADICAO,res);
    }

    public void subtracaoResultado (View v) {

        String num1String = getNumString(1);
        String num2String = getNumString(2);

        if(checkStringIsEmpty(num1String) || checkStringIsEmpty(num2String)){
            exibirAlertaLongo(getResources().getString(R.string.Campo_numero_vazio));
            return;
        }

        float num1 = convertStringToFloat(num1String);
        float num2 = convertStringToFloat(num2String);

        float res = num1 - num2;
        addResult(num1,num2,Operacao.SUBTRACAO,res);
    }

    public void multiplicacaoResultado (View v) {

        String num1String = getNumString(1);
        String num2String = getNumString(2);

        if(checkStringIsEmpty(num1String) || checkStringIsEmpty(num2String)){
            exibirAlertaLongo(getResources().getString(R.string.Campo_numero_vazio));
            return;
        }

        float num1 = convertStringToFloat(num1String);
        float num2 = convertStringToFloat(num2String);

        float res = num1 * num2;
        addResult(num1,num2,Operacao.MULTIPLICACAO,res);
    }

    public void divisaoResultado (View v) {

        String num1String = getNumString(1);
        String num2String = getNumString(2);

        if(checkStringIsEmpty(num1String) || checkStringIsEmpty(num2String)){
            exibirAlertaLongo(getResources().getString(R.string.Campo_numero_vazio));
            return;
        }

        float num1 = convertStringToFloat(num1String);
        float num2 = convertStringToFloat(num2String);

        if (num2 == 0){
            exibirAlertaLongo("Divisão por zero");
            return;
        }

        float res = num1 / num2;
        addResult(num1,num2,Operacao.DIVISAO,res);
    }

    public void limparhistorico(View v){
        resultados.clear();
        updateResult();
    }

    ////////////////////////////////////////////////

    private void criarBancoDados(){
        db = openOrCreateDatabase("calculadora.db", Context.MODE_PRIVATE,null);

        StringBuilder sql_Create_Resultado = new StringBuilder();
        sql_Create_Resultado.append("CREATE TABLE IF NOT EXIST resultado" +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "operacao VARCHAR(1), " +
                "num1 FLOAT(8,2), " +
                "num2 FLOAT(8,2), " +
                "resultado FLOAT(8,2) " +
                ")");

        try{
            db.execSQL(sql_Create_Resultado.toString());
        } catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void gravaResultado(String op, String n1, String n2, Float r) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERTO INTO resultado (operacao, num1, num2, resultado)" +
                "VALUES (");
        sql.append("'" + op + "',");
        sql.append(n1 + ",");
        sql.append(n2 + ",");
        sql.append(r);
        sql.append(")");

        try{
            db.execSQL(sql.toString());
        } catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
