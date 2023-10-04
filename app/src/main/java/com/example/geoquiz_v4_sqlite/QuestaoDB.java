package com.example.geoquiz_v4_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuestaoDB {


    private Context mContext;
    private static Context mStaticContext;
    private SQLiteDatabase mDatabase;


    public QuestaoDB(Context contexto) {
        mContext = contexto.getApplicationContext();
        mStaticContext = mContext;
        mDatabase = new QuestoesDBHelper(mContext).getWritableDatabase();
    }


    private static ContentValues getValoresConteudo(Questao q) {
        ContentValues valores = new ContentValues();

        // pares chave-valor: nomes das colunas - valores
        valores.put(QuestoesDbSchema.QuestoesTbl.Cols.UUID, q.getId().toString());
        // recupera valor do recurso string pelo id
        valores.put(
                QuestoesDbSchema.QuestoesTbl.Cols.TEXTO_QUESTAO,
                mStaticContext.getString(q.getTextoRespostaId())
        );
        valores.put(QuestoesDbSchema.QuestoesTbl.Cols.QUESTAO_CORRETA, q.isRespostaCorreta());
        return valores;
    }

    public void addQuestao(Questao q) {
        ContentValues valores = getValoresConteudo(q);
        mDatabase.insert(QuestoesDbSchema.QuestoesTbl.NOME, null, valores);
    }

    public void updateQuestao(Questao q) {
        String uuidString = q.getId().toString();
        ContentValues valores = getValoresConteudo(q);

        mDatabase.update(
                QuestoesDbSchema.QuestoesTbl.NOME,
                valores,
                QuestoesDbSchema.QuestoesTbl.Cols.UUID + " = ?",
                new String[] {uuidString}
        );
    }

    public Cursor queryQuestao(String clausulaWhere, String[] argsWhere) {
        Cursor cursor = mDatabase.query(
                QuestoesDbSchema.QuestoesTbl.NOME,
                null, // todas as colunas
                clausulaWhere,
                argsWhere,
                null, // sem group by
                null, // sem having
                null  // sem order by
        );
        return cursor;
    }


    private static ContentValues getRespostaContentValues(Resposta r) {
        ContentValues valores = new ContentValues();

        valores.put(QuestoesDbSchema.RespostasTbl.Cols.UUID, r.getUuid().toString());
        valores.put(QuestoesDbSchema.RespostasTbl.Cols.RESPOSTA_CORRETA, r.getRespostaCorreta());
        valores.put(QuestoesDbSchema.RespostasTbl.Cols.RESPOSTA_OFERECIDA, r.getRespostaOferecida());
        valores.put(QuestoesDbSchema.RespostasTbl.Cols.COLOU, r.getColou());

        return valores;
    }

    public void addResposta(Resposta r) {
        ContentValues valores = getRespostaContentValues(r);
        mDatabase.insert(QuestoesDbSchema.RespostasTbl.NOME, null, valores);
    }

    public Cursor getRespostas(String clausulaWhere, String[] argsWhere) {
        Cursor cursor = mDatabase.query(
                QuestoesDbSchema.RespostasTbl.NOME,
                null, // todas as colunas
                clausulaWhere,
                argsWhere,
                null, // sem group by
                null, // sem having
                null  // sem order by
        );
        return cursor;
    }

    public void deleteRespostas() {
        mDatabase.delete(QuestoesDbSchema.RespostasTbl.NOME, null, null);
    }
}
