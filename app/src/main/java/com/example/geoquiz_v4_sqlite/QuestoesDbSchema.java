package com.example.geoquiz_v4_sqlite;

public class QuestoesDbSchema {

    public static final class QuestoesTbl {
        public static final String NOME = "Questoes";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TEXTO_QUESTAO = "txt_questao";
            public static final String QUESTAO_CORRETA = "questao_correta";
        }
    }

    public static final class RespostasTbl {
        public static final String NOME = "Respostas";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String RESPOSTA_CORRETA = "resposta_correta";
            public static final String RESPOSTA_OFERECIDA = "resposta_oferecida";
            public static final String COLOU = "colou";
        }
    }
}
