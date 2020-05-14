package br.com.dlweb.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AdicionarAlunoActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText etNome;
    EditText etIdade;
    Spinner spCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_aluno);

        etNome = findViewById(R.id.etNome);
        etIdade = findViewById(R.id.etIdade);
        spCurso = findViewById(R.id.spCurso);

        String[] cursos = new String[] {
                "Ciência da Computação",
                "Engenharia de Computação",
                "Sistemas para Internet"
        };
        ArrayAdapter<String> spArrayAdapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, cursos);
        spCurso.setAdapter(spArrayAdapter);

        Button clickAdicionar = findViewById(R.id.btnAdicionar);
        clickAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD();
            }
        });
    }

    private void salvarBD () {
        String nome = etNome.getText().toString().trim();
        String idade = etIdade.getText().toString().trim();
        if(nome.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (idade.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a idade!", Toast.LENGTH_LONG).show();
        } else {
            db = openOrCreateDatabase("escolas.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            String nomeCurso = spCurso.getSelectedItem().toString();
            sql.append("INSERT INTO aluno(nome,idade,curso) VALUES (");
            sql.append("'" + nome + "', ");
            sql.append(idade + ", ");
            sql.append("'" + nomeCurso + "'");
            sql.append(");");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Aluno inserido", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            etNome.setText("");
            etIdade.setText("");
            spCurso.setSelection(0);
            db.close();

        }
    }
}
