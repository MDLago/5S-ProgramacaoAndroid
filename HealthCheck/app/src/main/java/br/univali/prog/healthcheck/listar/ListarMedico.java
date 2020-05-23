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
import br.univali.prog.healthcheck.adapters.MedicoRecyclerAdapter;
import br.univali.prog.healthcheck.db.DB;
import br.univali.prog.healthcheck.dominio.Medico;
import br.univali.prog.healthcheck.editar.EditaMedico;
import br.univali.prog.healthcheck.interfaces.RecyclerOnClickListerner;

public class ListarMedico extends AppCompatActivity implements RecyclerOnClickListerner {


    private RecyclerView rv_ListaMedico;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_medico);

        db = new DB(getApplicationContext());

        rv_ListaMedico = findViewById(R.id.rv_ListaMedico);
        rv_ListaMedico.setHasFixedSize(true);
        rv_ListaMedico.setLayoutManager(getLinearLayout());
        rv_ListaMedico.setAdapter(rvAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();

        rv_ListaMedico.setAdapter(rvAdapter());
    }

    //tempo, 0 = curto, 1 = longo
    private void exibirMensagem(String msg,int tempo){
        Toast.makeText(getApplicationContext(),msg,tempo).show();
    }

    private void abrirEditarMedico(int id){
        Intent i = new Intent(getApplicationContext(), EditaMedico.class);
        i.putExtra("id",id);
        startActivity(i);
    }

    private LinearLayoutManager getLinearLayout(){
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(RecyclerView.VERTICAL);

        return llm;
    }

    private MedicoRecyclerAdapter rvAdapter(){
        List<Medico> list;
        MedicoRecyclerAdapter adapter;

        try{
           list = db.buscarMedico();
           adapter = new MedicoRecyclerAdapter(getApplicationContext(),list);
           adapter.setRecyclerOnClickListerner(this);
           return adapter;
        }catch (SQLException e){
            exibirMensagem(e.getMessage(),1);
        }
            return null;
    }

    // Position é o ID do medico no banco de dados
    @Override
    public void onClickListener(View v, int position) {
        abrirEditarMedico(position);

    }


}
