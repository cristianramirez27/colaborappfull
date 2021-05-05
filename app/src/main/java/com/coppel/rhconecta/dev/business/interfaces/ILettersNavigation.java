package com.coppel.rhconecta.dev.business.interfaces;

/**
 * Created by faustolima on 28/11/18.
 */

public interface ILettersNavigation<T> {

    void showFragmentAtPosition(int position, T data);
    void showFragmentAtPosition(int position);
    void setKinderGardenData(T data);
}
