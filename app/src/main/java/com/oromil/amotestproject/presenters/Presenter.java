package com.oromil.amotestproject.presenters;

/**
 * Created by Oromil on 27.07.2017.
 */

public interface Presenter <V extends MvpView> {
    void attachView(V mvpView);
    void detachView();
    void showToast(String message);
}
