package com.oromil.amotestproject.presenters;

import android.content.Context;

import com.oromil.amotestproject.data.model.Lead;
import com.oromil.amotestproject.data.model.LeadsStatus;

import java.util.List;

/**
 * Created by Oromil on 27.07.2017.
 */

public interface DealsMvpView extends MvpView {

    void setLeadsToList(List<Lead> dealsList);
    void setLeadsStatusesToList(List<LeadsStatus> dealsStatusList);
    void updateList();
    void showProgress(boolean show);
}
