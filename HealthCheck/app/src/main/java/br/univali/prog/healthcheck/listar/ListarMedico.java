package br.univali.prog.healthcheck.listar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.SQLException;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import br.univali.prog.healthcheck.R;
import br.univali.prog.healthcheck.adapters.ListaMedicoAdapter;
import br.univali.prog.healthcheck.db.DB;
import br.univali.prog.healthcheck.objetos.Medico;

public class ListarMedico extends AppCompatActivity {


    RecyclerView rv_ListaMedico;
    DB db;

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

    //tempo, 0 = curto, 1 = longo
    private void exibirMensagem(String msg,int tempo){
        Toast.makeText(getApplicationContext(),msg,tempo).show();
    }


    private LinearLayoutManager getLinearLayout(){
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(RecyclerView.VERTICAL);

        return llm;
    }

    private ListaMedicoAdapter rvAdapter(){
        List<Medico> list;
        ListaMedicoAdapter adapter;

        try{
           list = db.buscarMedico();
           adapter = new ListaMedicoAdapter(getApplicationContext(),list);
           return adapter;
        }catch (SQLException e){
            exibirMensagem(e.getMessage(),1);
        }
            return null;
    }
}
