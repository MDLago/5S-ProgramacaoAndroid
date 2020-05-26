package br.univali.prog.healthcheck.adicionar;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.univali.prog.healthcheck.R;
import br.univali.prog.healthcheck.db.DB;

public class AdicionaPaciente extends AppCompatActivity {

    private DB db;

    private EditText etNome;
    private Spinner spTS;
    private EditText etRua;
    private EditText etNumero;
    private EditText etCidade;
    private Spinner spUF;
    private EditText etCelular;
    private EditText etFixo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_paciente);

        etNome = findViewById(R.id.et_PacienteNome);
        spTS = findViewById(R.id.sp_PacienteTipoSangue);
        etRua = findViewById(R.id.et_PacienteRua);
        etNumero = findViewById(R.id.et_PacienteNumero);
        etCidade = findViewById(R.id.et_PacienteCidade);
        spUF = findViewById(R.id.sp_PacienteUF);
        etCelular = findViewById(R.id.et_PacienteCelular);
        etFixo = findViewById(R.id.et_PacienteFixo);

        db = new DB(getApplicationContext());
        spinnerUF();
        spinnerTS();

    }

    //region Outros
    //tempo, 0 = curto, 1 = longo
    private void exibirMensagem(String msg, int tempo) {
        Toast.makeText(getApplicationContext(), msg, tempo).show();
    }

    private void spinnerUF() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.UF));
        spUF.setAdapter(arrayAdapter);
    }

    private void spinnerTS() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.TipoSangue));
        spTS.setAdapter(arrayAdapter);
    }

    //endregion

    //region Botões
    public void btnAdicionarPaciente(View v){
        if(campoVazio()){
            exibirMensagem("Favor preencher todos os campos.",0);
            return;
        }

        String nome = etNome.getText().toString().trim();
        int grp_Sanguineo = Integer.parseInt(spTS.getSelectedItem().toString());
        String rua= etRua.getText().toString().trim();
        int numero= Integer.parseInt(etNumero.getText().toString());
        String cidade= etCidade.getText().toString().trim();
        String uf= spUF.getSelectedItem().toString();
        String celular= etCelular.getText().toString().trim();
        String fixo= etFixo.getText().toString().trim();

        try{
            db.inserirPaciente(nome,grp_Sanguineo,rua,numero,cidade,uf,celular,fixo);
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
                spUF.getSelectedItem().toString().trim().isEmpty() ||
                etRua.getText().toString().trim().isEmpty() ||
                etNumero.getText().toString().trim().isEmpty() ||
                etCidade.getText().toString().trim().isEmpty() ||
                spUF.getSelectedItem().toString().trim().isEmpty() ||
                etCelular.getText().toString().trim().isEmpty() ||
                etFixo.getText().toString().trim().isEmpty();
    }

    //endregion
}

