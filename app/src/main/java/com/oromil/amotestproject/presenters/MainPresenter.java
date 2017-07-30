package com.oromil.amotestproject.presenters;

import android.util.Log;
import android.widget.Toast;

import com.oromil.amotestproject.AmoTestPreferences;
import com.oromil.amotestproject.R;
import com.oromil.amotestproject.data.ServerManager;
import com.oromil.amotestproject.ui.DealsActivity;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Oromil on 27.07.2017.
 */

public class MainPresenter extends BasePresenter{

    AmoTestPreferences mPreferences;

    public MainPresenter(){
        mPreferences = AmoTestPreferences.getInstance();
    }

    public void checkUserData(){
        if (check())
            getData(mPreferences.getLogin(), mPreferences.getPassword());
        else getmMvpView().splashVisible(false);
    }

    public void getData(final String login, final String password){
        ServerManager.getInstance().requestAuthorization(login, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()).subscribe(aVoid -> {

                    mPreferences.savePassword(password);
                    mPreferences.saveLogin(login);

                    DealsActivity.start(getmMvpView().getContext());
                    getmMvpView().destroyView();

                }, throwable -> {

            getmMvpView().showProgressBar(false);

            if (throwable instanceof HttpException) {
                if (((HttpException) throwable).code()==401)
                    getmMvpView().showMassageError();
                else if (((HttpException) throwable).code()==403)
                    showToast(getmMvpView().getContext().getString(R.string.error_403));
            }else {
                showToast(getmMvpView().getContext().getString(R.string.network_error));
                if (check())
                    DealsActivity.start(getmMvpView().getContext());
            }
        });
    }

    private boolean check(){
        String login = mPreferences.getLogin();
        String password = mPreferences.getPassword();

        if (login!=null&&password!=null)
            return true;
        else return false;
    }

    @Override
    public MainMvpView getmMvpView() {
        return (MainMvpView)mMvpView;
    }
}
