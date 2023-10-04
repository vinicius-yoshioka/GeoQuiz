package com.example.geoquiz_v4_sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Modelo de projeto para a Atividade 1.
 * Será preciso adicionar o cadastro das respostas do usuário ao Quiz, conforme
 * definido no Canvas.
 * <p>
 * GitHub: https://github.com/udofritzke/GeoQuiz
 * */
public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private static final String CHAVE_INDICE = "INDICE";
    private static final int CODIGO_REQUISICAO_COLA = 0;


    private TextView mTextViewQuestao;
    private Button mBotaoVerdadeiro;
    private Button mBotaoFalso;
    private Button mBotaoCola;
    private Button mBotaoProximo;
    private Button mBotaoMostra;
    private Button mBotaoDeleta;
    private TextView mTextViewQuestoesArmazenadas;

    private QuestaoDB mQuestoesDb;
    private Questao[] mBancoDeQuestoes = new Questao[] {
            new Questao(R.string.questao_suez, true),
            new Questao(R.string.questao_alemanha, false)
    };
    private boolean mEhColador;
    private int mIndiceAtual = 0;


    @Override
    protected void onCreate(Bundle instanciaSalva) {
        super.onCreate(instanciaSalva);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");

        if (instanciaSalva != null) {
            mIndiceAtual = instanciaSalva.getInt(CHAVE_INDICE, 0);
        }

        mTextViewQuestao = (TextView) findViewById(R.id.view_texto_da_questao);
        atualizaQuestao();

        mBotaoVerdadeiro = (Button) findViewById(R.id.botao_verdadeiro);
        mBotaoVerdadeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaResposta(true);

                if (mQuestoesDb == null) {
                    mQuestoesDb = new QuestaoDB(getBaseContext());
                }

                Questao questaoRespondida = mBancoDeQuestoes[mIndiceAtual];

                Resposta resposta = new Resposta();
                resposta.setUuid(questaoRespondida.getId());
                resposta.setRespostaOferecida(1);
                resposta.setRespostaCorreta(questaoRespondida.isRespostaCorreta() ? 1 : 0);
                resposta.setColou(mEhColador ? 1 : 0);

                mQuestoesDb.addResposta(resposta);
            }
        });

        mBotaoFalso = (Button) findViewById(R.id.botao_falso);
        mBotaoFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaResposta(false);

                if (mQuestoesDb == null) {
                    mQuestoesDb = new QuestaoDB(getBaseContext());
                }

                Questao questaoRespondida = mBancoDeQuestoes[mIndiceAtual];

                Resposta resposta = new Resposta();
                resposta.setUuid(questaoRespondida.getId());
                resposta.setRespostaOferecida(0);
                resposta.setRespostaCorreta(questaoRespondida.isRespostaCorreta() ? 1 : 0);
                resposta.setColou(mEhColador ? 1 : 0);

                mQuestoesDb.addResposta(resposta);
            }
        });

        mBotaoProximo = (Button) findViewById(R.id.botao_proximo);
        mBotaoProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIndiceAtual = (mIndiceAtual + 1) % mBancoDeQuestoes.length;
                mEhColador = false;
                atualizaQuestao();
            }
        });

        mBotaoCola = (Button) findViewById(R.id.botao_cola);
        mBotaoCola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean respostaEVerdadeira = mBancoDeQuestoes[mIndiceAtual].isRespostaCorreta();
                Intent intent = ColaActivity.novoIntent(MainActivity.this, respostaEVerdadeira);
                startActivityForResult(intent, CODIGO_REQUISICAO_COLA);
            }
        });

        mBotaoMostra = (Button) findViewById(R.id.botao_mostra_questoes);
        mBotaoMostra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mQuestoesDb == null) {
                    mQuestoesDb = new QuestaoDB(getBaseContext());
                }

                if (mTextViewQuestoesArmazenadas == null) {
                    mTextViewQuestoesArmazenadas = (TextView) findViewById(R.id.texto_questoes_a_apresentar);
                }
                mTextViewQuestoesArmazenadas.setText("");

                Cursor cursor = mQuestoesDb.getRespostas(null, null);
                if (cursor != null) {
                    if (cursor.getCount() == 0) {
                        mTextViewQuestoesArmazenadas.setText("Nada a apresentar");
                        Log.i("MSGS", "Nenhum resultado");
                    }

                    try {
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            String texto = cursor.getString(cursor.getColumnIndex(QuestoesDbSchema.RespostasTbl.Cols.RESPOSTA_OFERECIDA));
                            Log.i("MSGS", texto);

                            mTextViewQuestoesArmazenadas.append(texto + "\n\n");
                            cursor.moveToNext();
                        }
                    } finally {
                        cursor.close();
                    }
                } else {
                    Log.i("MSGS", "cursor nulo!");
                }
            }
        });

        mBotaoDeleta = (Button) findViewById(R.id.botao_deleta);
        mBotaoDeleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mQuestoesDb == null) {
                    mQuestoesDb = new QuestaoDB(getBaseContext());
                }

                mQuestoesDb.deleteRespostas();

                if (mTextViewQuestoesArmazenadas == null) {
                    mTextViewQuestoesArmazenadas = (TextView) findViewById(R.id.texto_questoes_a_apresentar);
                }
                mTextViewQuestoesArmazenadas.setText("");
            }
        });
    }


    private void atualizaQuestao() {
        int questao = mBancoDeQuestoes[mIndiceAtual].getTextoRespostaId();
        mTextViewQuestao.setText(questao);
    }

    private void verificaResposta(boolean respostaPressionada) {
        boolean respostaCorreta = mBancoDeQuestoes[mIndiceAtual].isRespostaCorreta();
        int idMensagemResposta = 0;

        if (mEhColador) {
            idMensagemResposta = R.string.toast_julgamento;
        } else {
            if (respostaPressionada == respostaCorreta) {
                idMensagemResposta = R.string.toast_correto;
            } else {
                idMensagemResposta = R.string.toast_incorreto;
            }
        }
        Toast.makeText(this, idMensagemResposta, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle instanciaSalva) {
        super.onSaveInstanceState(instanciaSalva);
        Log.i(TAG, "onSaveInstanceState()");
        instanciaSalva.putInt(CHAVE_INDICE, mIndiceAtual);
    }

    @Override
    protected void onActivityResult(int codigoRequisicao, int codigoResultado, Intent dados) {
        super.onActivityResult(codigoRequisicao, codigoResultado, dados);
        if (codigoResultado != Activity.RESULT_OK) {
            return;
        }
        if (codigoRequisicao == CODIGO_REQUISICAO_COLA) {
            if (dados == null) {
                return;
            }
            mEhColador = ColaActivity.foiMostradaResposta(dados);
        }
    }
}
