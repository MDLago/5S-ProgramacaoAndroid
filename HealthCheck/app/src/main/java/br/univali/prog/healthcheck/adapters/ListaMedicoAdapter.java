package br.univali.prog.healthcheck.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.univali.prog.healthcheck.R;
import br.univali.prog.healthcheck.objetos.Medico;

public class ListaMedicoAdapter extends RecyclerView.Adapter<ListaMedicoAdapter.MyViewHolder> {

    private List<Medico> medicoList;
    private LayoutInflater layoutInflater;

    public ListaMedicoAdapter(Context context, List<Medico> medicoList){
        this.medicoList = medicoList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //region Obrigatório
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.listar_medico_layout, parent,false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listaMedicoNome.setText(medicoList.get(position).nome);
        holder.listaMedicoCRM.setText(medicoList.get(position).crm);
    }

    @Override
    public int getItemCount() {
        return medicoList.size();
    }

    //endregion

    //region ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView listaMedicoNome;
        public TextView listaMedicoCRM;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            listaMedicoNome = itemView.findViewById(R.id.tvListarMedicoNome);
            listaMedicoCRM = itemView.findViewById(R.id.tvListarMedicoCRM);
        }
    }

    //endregion
}
