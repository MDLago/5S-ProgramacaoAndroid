package br.univali.prog.healthcheck.adicionar;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.univali.prog.healthcheck.db.DB;
import br.univali.prog.healthcheck.R;

public class AdicionaMedico extends AppCompatActivity {

    private DB db;

    private EditText etNome;
    private  EditText etCRM;
    private EditText etRua;
    private  EditText etNumero;
    private  EditText etCidade;
    private   Spinner spUF;
    private  EditText etCelular;
    private   EditText etFixo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_medico);

        etNome = findViewById(R.id.et_MedicoNome);
        etCRM = findViewById(R.id.et_MedicoCRM);
        etRua = findViewById(R.id.et_MedicoRua);
        etNumero = findViewById(R.id.et_MedicoNumero);
        etCidade = findViewById(R.id.et_MedicoCidade);
        spUF = findViewById(R.id.sp_MedicoUF);
        etCelular = findViewById(R.id.et_MedicoCelular);
        etFixo = findViewById(R.id.et_MedicoFixo);

        db = new DB(getApplicationContext());
        spinnerUF();
    }

    //region Outros
    //tempo, 0 = curto, 1 = longo
    private void exibirMensagem(String msg,int tempo){
        Toast.makeText(getApplicationContext(),msg,tempo).show();
    }

    private void spinnerUF(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.UF));
        spUF.setAdapter(arrayAdapter);
    }
    //endregion

    //region Botões
    public void btnAdicionarMedico(View v){
        if(campoVazio()){
            exibirMensagem("Favor preencher todos os campos.",0);
            return;
        }

        String nome = etNome.getText().toString().trim();
        String crm = etCRM.getText().toString().trim();
        String rua= etRua.getText().toString().trim();
        int numero= Integer.parseInt(etNumero.getText().toString());
        String cidade= etCidade.getText().toString().trim();
        String uf= spUF.getSelectedItem().toString();
        String celular= etCelular.getText().toString().trim();
        String fixo= etFixo.getText().toString().trim();

        try{
            db.inserirMedico(nome,crm,rua,numero,cidade,uf,celular,fixo);
        }catch (SQLException e){
            exibirMensagem(e.getMessage(),1);
            return;
        }

        exibirMensagem("Adicionado com sucesso",0);
        this.finish();
    }
    public void btnVoltar(View v){
        this.finish();
    }

    //endregion

    //region VALIDAÇÕES

    private boolean campoVazio(){
        return etNome.getText().toString().trim().isEmpty() ||
                etCRM.getText().toString().trim().isEmpty() ||
                etRua.getText().toString().trim().isEmpty() ||
                etNumero.getText().toString().trim().isEmpty() ||
                etCidade.getText().toString().trim().isEmpty() ||
                spUF.getSelectedItem().toString().trim().isEmpty() ||
                etCelular.getText().toString().trim().isEmpty() ||
                etFixo.getText().toString().trim().isEmpty();
    }

    //endregion
}
