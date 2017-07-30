package com.oromil.amotestproject.ui;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActivityChooserView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.oromil.amotestproject.AmoTestPreferences;
import com.oromil.amotestproject.presenters.MainMvpView;
import com.oromil.amotestproject.presenters.MainPresenter;
import com.oromil.amotestproject.R;

public class MainActivity extends AppCompatActivity  implements MainMvpView {

    private MainPresenter mPresenter;
    private SplashFragment mSplash;
    private TextInputLayout mInputLayout;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSplash = new SplashFragment();
        splashVisible(true);

        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
        mPresenter.checkUserData();

        mInputLayout = (TextInputLayout) findViewById(R.id.inputLayout);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        final EditText etLogin = (EditText) findViewById(R.id.etLogin);
        final TextInputEditText etPassword = (TextInputEditText) findViewById(R.id.etPassword);

        findViewById(R.id.btnLogin).setOnClickListener(v ->{
            showProgressBar(true);
                mPresenter.getData(etLogin.getText().toString(),
                        etPassword.getText().toString());});
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void destroyView() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void splashVisible(boolean visibility) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (visibility)
            transaction.add(R.id.fragment_container, mSplash);
        else transaction.remove(mSplash);
        transaction.commit();
    }

    @Override
    public void showMassageError() {
        mInputLayout.setError(getString(R.string.input_error));
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show)
            mProgressBar.setVisibility(View.VISIBLE);
        else mProgressBar.setVisibility(View.INVISIBLE);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }
}
