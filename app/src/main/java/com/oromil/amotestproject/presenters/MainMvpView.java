package com.oromil.amotestproject.presenters;

/**
 * Created by Oromil on 29.07.2017.
 */

public interface MainMvpView extends MvpView {
    void splashVisible(boolean visibility);
    void showMassageError();
    void showProgressBar(boolean show);
}
