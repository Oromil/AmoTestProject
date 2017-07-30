package com.oromil.amotestproject.presenters;

import com.oromil.amotestproject.AmoTestPreferences;
import com.oromil.amotestproject.R;
import com.oromil.amotestproject.data.ServerManager;
import com.oromil.amotestproject.data.model.LeadsListResponse;
import com.oromil.amotestproject.data.model.Lead;
import com.oromil.amotestproject.data.model.LeadsStatus;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import retrofit2.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Oromil on 27.07.2017.
 */

public class DealsActivityPresenter<T extends MvpView> extends BasePresenter{

    Realm mRealm;

    public void getData(){

        final ServerManager mServerManager = ServerManager.getInstance();

        mServerManager.requestDealsList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LeadsListResponse>() {
                    @Override
                    public void onCompleted() {
                        getmMvpView().showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            if (((HttpException) e).code()==401)
                                ServerManager.getInstance().requestAuthorization(AmoTestPreferences.getInstance().getLogin(), AmoTestPreferences.getInstance().getPassword())
                                    .subscribeOn(Schedulers.newThread())
                                    .subscribe(aVoid -> getData());
                            else if (((HttpException) e).code()==401)
                                showToast(mMvpView.getContext().getString(R.string.error_403));

                        }
                        else {
                            showToast(getmMvpView().getContext().getString(R.string.network_error));

                            loadDataFromDataBase();
                            getmMvpView().showProgress(false);
                        }
                    }
                    @Override
                    public void onNext(final LeadsListResponse deals) {
                        mServerManager.requestDealsStatuses()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(leadsListResponse -> {
                                    List<Lead>leadList = deals.getResponse().getLeads();
                                    List<LeadsStatus>leadsStatusList = leadsListResponse.getResponse().getAccount().getLeadsStatuses();

                                    saveInDataBase(leadList, leadsStatusList).observeOn(AndroidSchedulers.mainThread())
                                            .subscribeOn(Schedulers.newThread())
                                            .subscribe();

                                    getmMvpView().setLeadsToList(leadList);
                                    getmMvpView().setLeadsStatusesToList(leadsStatusList);
                                    getmMvpView().updateList();
                                });
                    }
                });
    }

    private Observable saveInDataBase(List<Lead>leads, List<LeadsStatus>leadsStatuses){
        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> {
            mRealm.deleteAll();
            mRealm.insertOrUpdate(leads);
            mRealm.insertOrUpdate(leadsStatuses);
        });
        mRealm.close();
        return Observable.just("");
    }

    private void loadDataFromDataBase(){

        loadLeadsStatusesFromDataBase()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(leadsStatuses -> {

                    getmMvpView().setLeadsStatusesToList(leadsStatuses);

                    loadLeadsFromDataBase()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(leads -> {
                                getmMvpView().setLeadsToList(leads);
                                getmMvpView().updateList();
                            });
                });
    }

    private Observable<ArrayList<Lead>> loadLeadsFromDataBase(){
        return Observable.create(subscriber -> {

            ArrayList<Lead>leads = new ArrayList<>();

            mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(realm -> {
                RealmQuery<Lead>realmQuery = mRealm.where(Lead.class);
                leads.addAll(mRealm.copyFromRealm(realmQuery.findAll()));
            });
            mRealm.close();
            subscriber.onNext(leads);
        });
    }
    private Observable<ArrayList<LeadsStatus>> loadLeadsStatusesFromDataBase(){
        return Observable.create(subscriber -> {

            ArrayList<LeadsStatus>leadsStatuses = new ArrayList<>();

            mRealm = Realm.getDefaultInstance();
            mRealm.executeTransaction(realm -> {
                RealmQuery<LeadsStatus>realmQuery = mRealm.where(LeadsStatus.class);
                leadsStatuses.addAll(mRealm.copyFromRealm(realmQuery.findAll()));
            });
            mRealm.close();
            subscriber.onNext(leadsStatuses);
        });
    }

    public void clearDataBase(){
        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> mRealm.deleteAll());
        mRealm.close();
    }

    @Override
    public DealsMvpView getmMvpView() {
        return (DealsMvpView)super.getmMvpView();
    }
}
