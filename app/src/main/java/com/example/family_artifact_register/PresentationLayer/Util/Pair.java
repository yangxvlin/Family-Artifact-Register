package com.example.family_artifact_register.PresentationLayer.Util;

public class Pair<U, V> {
    private U fst;
    private V snd;

    public Pair(U u, V v) {
        fst = u;
        snd = v;
    }

    public U getFst() {
        return fst;
    }

    public V getSnd() {
        return snd;
    }
}
