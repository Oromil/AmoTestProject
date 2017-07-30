package com.oromil.amotestproject.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.oromil.amotestproject.AmoTestPreferences;
import com.oromil.amotestproject.adapters.DealsListAdapter;
import com.oromil.amotestproject.data.model.Lead;
import com.oromil.amotestproject.data.model.LeadsStatus;
import com.oromil.amotestproject.presenters.DealsActivityPresenter;
import com.oromil.amotestproject.R;
import com.oromil.amotestproject.presenters.DealsMvpView;

import java.util.List;

public class DealsActivity extends AppCompatActivity implements DealsMvpView{

    private DealsActivityPresenter mPresenter;
    private DealsListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);

        init();

        mPresenter = new DealsActivityPresenter();
        mPresenter.attachView(this);
        mPresenter.getData();

    }

    private void init(){
        mAdapter = new DealsListAdapter();

        mRecyclerView = (RecyclerView) findViewById(R.id.rvDealsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeLayout.setOnRefreshListener(() -> mPresenter.getData());
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
    public void setLeadsToList(List<Lead> dealsList) {
        mAdapter.setDealsList(dealsList);
    }

    @Override
    public void setLeadsStatusesToList(List<LeadsStatus> dealsStatusList) {
        mAdapter.setDealsStatusList(dealsStatusList);
    }

    @Override
    public void updateList() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress(boolean show) {
        mSwipeLayout.setRefreshing(show);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, DealsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AmoTestPreferences.getInstance().resetPreferences();
        mPresenter.clearDataBase();
        MainActivity.start(this);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
