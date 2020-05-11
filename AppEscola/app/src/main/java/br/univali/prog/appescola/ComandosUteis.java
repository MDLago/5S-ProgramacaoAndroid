package br.univali.prog.appescola;

import android.content.Context;
import android.widget.Toast;

public class ComandosUteis {
    private Context context;

    //////////////////////////////////////////////////////////////////
    public ComandosUteis(){
    }

    public ComandosUteis(Context context){
        this.context = context;
    }
    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    public static void exibirMensagem(Context context, String msg){
        Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
    }

    public void exibirMensagem(String msg){
        exibirMensagem(context,msg);
    }
    //////////////////////////////////////////////////////////////////
}
