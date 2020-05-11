package br.univali.prog.appescola.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.univali.prog.appescola.ComandosUteis;

public class sqliteDB {
    private SQLiteDatabase db;
    private String nomeDB;
    private ComandosUteis comandosUteis;
    private Context context;

    //////////////////////////////////////////////////////////////////
    public sqliteDB(){
        nomeDB = "escola";
    }

    public void setContext(Context context){
        this.context = context;
        comandosUteis = new ComandosUteis(context);
    }

    //////////////////////////////////////////////////////////////////

    public void criarBanco(){
        db = SQLiteDatabase.openOrCreateDatabase(nomeDB,null);

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXIST aluno");
        sql.append("(");
        sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome VARCHAR(50), ");
        sql.append("idade INTEGER, ");
        sql.append("curso VARCHAR(50)");
        sql.append(")");

        executaSQL(sql);
    }

    private void executaSQL(StringBuilder comandoSQL){
        try {
            db.execSQL(comandoSQL.toString());
        }catch (Exception ex){
            comandosUteis.exibirMensagem(ex.getMessage());
        }
    }

}
