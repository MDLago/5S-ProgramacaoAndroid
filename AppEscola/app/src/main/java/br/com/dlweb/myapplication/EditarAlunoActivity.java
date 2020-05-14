package br.com.dlweb.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditarAlunoActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText etNome;
    EditText etIdade;
    Spinner spCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_aluno);

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

        Intent valores = getIntent();
        etNome.setText(valores.getStringExtra("nome"));
        etIdade.setText(valores.getStringExtra("idade"));
        String cursoExtra = valores.getStringExtra("curso");
        int aux = 0 ;
        for (String c : cursos) {
            if (c.equals(cursoExtra)) {
                break;
            }
            aux ++;
        }
        spCurso.setSelection(aux);

        final String id = valores.getStringExtra("id");

        Button clickEditar = findViewById(R.id.btnEditar);
        clickEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD(id);
            }
        });

        Button clickExcluir = findViewById(R.id.btnExcluir);
        clickExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirBD(id);
            }
        });

    }

    private void salvarBD(String id) {
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
            sql.append("UPDATE aluno SET ");
            sql.append("nome = '" + nome + "', ");
            sql.append("idade = " + idade + ", ");
            sql.append("curso = '" + nomeCurso + "' ");
            sql.append("WHERE _id = " + id + ";");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Aluno atualizado", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            Intent i = new Intent(getApplicationContext(), ListarAlunoActivity.class);
            startActivity(i);
            db.close();
        }
    }

    private void excluirBD(String id) {
        db = openOrCreateDatabase("escolas.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM aluno ");
        sql.append("WHERE _id = " + id + ";");
        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(), "Aluno excluído", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent i = new Intent(getApplicationContext(), ListarAlunoActivity.class);
        startActivity(i);
        db.close();
    }
}
