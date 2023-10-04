package com.example.geoquiz_v4_sqlite;

import java.util.UUID;

public class Resposta {


    private UUID mId;
    private int mRespostaCorreta;
    private int mRespostaOferecida;
    private int mColou;


    public Resposta() {}


    public UUID getUuid() {
        return mId;
    }

    public void setUuid(UUID uuid) {
        this.mId = uuid;
    }

    public int getRespostaCorreta() {
        return mRespostaCorreta;
    }

    public void setRespostaCorreta(int respostaCorreta) {
        this.mRespostaCorreta = respostaCorreta;
    }

    public int getRespostaOferecida() {
        return mRespostaOferecida;
    }

    public void setRespostaOferecida(int respostaOferecida) {
        this.mRespostaOferecida = respostaOferecida;
    }

    public int getColou() {
        return mColou;
    }

    public void setColou(int colou) {
        this.mColou = colou;
    }
}
