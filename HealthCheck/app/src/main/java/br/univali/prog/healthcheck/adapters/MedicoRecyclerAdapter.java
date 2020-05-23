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
import br.univali.prog.healthcheck.dominio.Medico;
import br.univali.prog.healthcheck.interfaces.RecyclerOnClickListerner;

public class MedicoRecyclerAdapter extends RecyclerView.Adapter<MedicoRecyclerAdapter.MyViewHolder> {

    private List<Medico> medicoList;
    private LayoutInflater layoutInflater;
    private RecyclerOnClickListerner recyclerOnClickListerner;

    public MedicoRecyclerAdapter(Context context, List<Medico> medicoList){
        this.medicoList = medicoList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //region Obrigatório
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View v = layoutInflater.inflate(R.layout.recycle_layout, viewGroup,false);
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
        if(medicoList == null){
            return 0;
        }
        return medicoList.size();
    }

    //endregion

    //region ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView listaMedicoNome;
        public TextView listaMedicoCRM;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            listaMedicoNome = itemView.findViewById(R.id.tvListarMedicoNome);
            listaMedicoCRM = itemView.findViewById(R.id.tvListarMedicoCRM);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(recyclerOnClickListerner != null){
                recyclerOnClickListerner.onClickListener(v,medicoList.get(getAdapterPosition()).id);
            }
        }
    }

    //endregion

    public void setRecyclerOnClickListerner (RecyclerOnClickListerner r){
        recyclerOnClickListerner = r;
    }
}
