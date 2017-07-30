package com.oromil.amotestproject.presenters;

import android.widget.Toast;

/**
 * Created by Oromil on 27.07.2017.
 */

public class BasePresenter<V extends MvpView> implements Presenter{

    V mMvpView;

    @Override
    public void attachView(MvpView mvpView) {
        this.mMvpView = (V) mvpView;
    }

    @Override
    public void detachView() {
        this.mMvpView = null;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(mMvpView.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public MvpView getmMvpView(){
        return mMvpView;
    }

    public boolean checkViewAttached(){
        if (mMvpView == null)
            return false;
        else
            return true;
    }

}
