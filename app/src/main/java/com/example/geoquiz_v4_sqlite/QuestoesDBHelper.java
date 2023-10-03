package com.example.geoquiz_v4_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuestoesDBHelper extends SQLiteOpenHelper {


    private static final String TAG = "QuestoesDbHelper";
    private static final int VERSAO = 1;
    private static final String NOME_DATABASE = "questoesDB";


    public QuestoesDBHelper(Context context) {
        super(context, NOME_DATABASE, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");

        db.execSQL("CREATE TABLE " + QuestoesDbSchema.QuestoesTbl.NOME + " (" +
                "_id integer PRIMARY KEY autoincrement," +
                QuestoesDbSchema.QuestoesTbl.Cols.UUID + " TEXT," +
                QuestoesDbSchema.QuestoesTbl.Cols.QUESTAO_CORRETA + " TEXT," +
                QuestoesDbSchema.QuestoesTbl.Cols.TEXTO_QUESTAO + " TEXT)"
        );
        Log.d(TAG, "Tabela " + QuestoesDbSchema.QuestoesTbl.NOME + " criada");

        db.execSQL("CREATE TABLE " + QuestoesDbSchema.RespostasTbl.NOME + " (" +
                "_id integer PRIMARY KEY autoincrement," +
                QuestoesDbSchema.RespostasTbl.Cols.UUID + " TEXT," +
                QuestoesDbSchema.RespostasTbl.Cols.RESPOSTA_CORRETA + " INTEGER," +
                QuestoesDbSchema.RespostasTbl.Cols.RESPOSTA_OFERECIDA + " INTEGER," +
                QuestoesDbSchema.RespostasTbl.Cols.COLOU + " INTEGER)"
        );
        Log.d(TAG, "Tabela " + QuestoesDbSchema.RespostasTbl.NOME + " criada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
        Log.d(TAG, "onUpgrade");

        db.execSQL("DROP TABLE IF EXISTS " + QuestoesDbSchema.QuestoesTbl.NOME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestoesDbSchema.RespostasTbl.NOME);
        onCreate(db);
    }
}
