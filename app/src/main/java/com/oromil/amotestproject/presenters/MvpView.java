package com.oromil.amotestproject.presenters;

import android.content.Context;

/**
 * Created by Oromil on 27.07.2017.
 */

public interface MvpView {
    Context getContext();
    void destroyView();
}
