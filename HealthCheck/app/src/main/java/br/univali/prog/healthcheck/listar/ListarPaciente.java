package br.univali.prog.healthcheck.listar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import br.univali.prog.healthcheck.R;
import br.univali.prog.healthcheck.adapters.PacienteRecyclerAdapter;
import br.univali.prog.healthcheck.db.DB;
import br.univali.prog.healthcheck.dominio.Paciente;
import br.univali.prog.healthcheck.editar.EditaPaciente;
import br.univali.prog.healthcheck.interfaces.RecyclerOnClickListerner;

public class ListarPaciente extends AppCompatActivity implements RecyclerOnClickListerner {

    private RecyclerView rv_ListaPaciente;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_paciente);

        db = new DB(getApplicationContext());

        rv_ListaPaciente = findViewById(R.id.rv_ListaPaciente);
        rv_ListaPaciente.setHasFixedSize(true);
        rv_ListaPaciente.setLayoutManager(getLinearLayout());
        rv_ListaPaciente.setAdapter(rvAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();

        rv_ListaPaciente.setAdapter(rvAdapter());
    }

    //tempo, 0 = curto, 1 = longo
    private void exibirMensagem(String msg,int tempo){
        Toast.makeText(getApplicationContext(),msg,tempo).show();
    }

    private void abrirEditarPaciente(int id){
        Intent i = new Intent(getApplicationContext(), EditaPaciente.class);
        i.putExtra("id",id);
        startActivity(i);
    }

    private LinearLayoutManager getLinearLayout(){
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(RecyclerView.VERTICAL);

        return llm;
    }

    private PacienteRecyclerAdapter rvAdapter(){
        List<Paciente> list;
       PacienteRecyclerAdapter adapter;

        try{
            list = db.buscarPaciente();
            adapter = new PacienteRecyclerAdapter(getApplicationContext(),list);
            adapter.setRecyclerOnClickListerner(this);
            return adapter;
        }catch (SQLException e){
            exibirMensagem(e.getMessage(),1);
        }
        return null;
    }

    // Position é o ID do paciente no banco de dados
    @Override
    public void onClickListener(View v, int position) {
        abrirEditarPaciente(position);

    }

}
