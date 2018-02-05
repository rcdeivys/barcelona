package com.millonarios.MillonariosFC.ui.chat.requests.di;

import com.millonarios.MillonariosFC.app.api.RequestApi;
import com.millonarios.MillonariosFC.ui.chat.friends.di.FriendScope;
import com.millonarios.MillonariosFC.ui.chat.requests.RequestsFragment;
import com.millonarios.MillonariosFC.ui.chat.requests.mvp.RequestModel;
import com.millonarios.MillonariosFC.ui.chat.requests.mvp.RequestPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Cesar on 31/01/2018.
 */

@Module
public class RequestModule {

    private RequestsFragment requestsFragment = null;

    public RequestModule(RequestsFragment fragment) {
        this.requestsFragment = fragment;
    }

    @Provides
    @RequestScope
    public RequestModel provideModel(RequestApi api) {
        return new RequestModel(api);
    }

    @Provides
    @RequestScope
    public RequestPresenter providePresenter(RequestModel model) {
        return new RequestPresenter(requestsFragment, model);
    }

}
