package com.millonarios.MillonariosFC.app.di;

import com.millonarios.MillonariosFC.app.api.ApiModule;
import com.millonarios.MillonariosFC.app.api.AuthApi;
import com.millonarios.MillonariosFC.app.api.BannerApi;
import com.millonarios.MillonariosFC.app.api.ConfigurationApi;
import com.millonarios.MillonariosFC.app.api.FriendsApi;
import com.millonarios.MillonariosFC.app.api.GalleryApi;
import com.millonarios.MillonariosFC.app.api.GroupsApi;
import com.millonarios.MillonariosFC.app.api.LineUpApi;
import com.millonarios.MillonariosFC.app.api.NewsApi;
import com.millonarios.MillonariosFC.app.api.ProfileApi;
import com.millonarios.MillonariosFC.app.api.RecoveryPasswordApi;
import com.millonarios.MillonariosFC.app.api.ReferredApi;
import com.millonarios.MillonariosFC.app.api.RequestApi;
import com.millonarios.MillonariosFC.app.api.SCalendarApi;
import com.millonarios.MillonariosFC.app.api.TeamApi;
import com.millonarios.MillonariosFC.app.api.TournamentApi;
import com.millonarios.MillonariosFC.app.api.UserPhotoApi;
import com.millonarios.MillonariosFC.app.api.VRApi;
import com.millonarios.MillonariosFC.app.api.WallApi;
import com.millonarios.MillonariosFC.app.api.WallCommentApi;
import com.millonarios.MillonariosFC.app.api.YouChooseApi;
import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.app.manager.SessionManager;

import dagger.Component;

/**
 * Created by Carlos on 01/10/2017.
 */

@AppScope
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    ConfigurationApi configurationApi();

    TeamApi teamApi();

    NewsApi newsApi();

    BannerApi bannerApi();

    GalleryApi galleryApi();

    TournamentApi tournamentApi();

    SCalendarApi scalendarApi();

    LineUpApi lineUpApi();

    YouChooseApi youChooseApi();

    UserPhotoApi userPhotoApi();

    VRApi virtualRealityApi();

    AuthApi loginApi();

    ProfileApi profileApi();

    RecoveryPasswordApi recoveryPasswordApi();

    ReferredApi referredApi();

    SessionManager sessionManager();

    GroupsApi groupsApi();

    FriendsApi friendsApi();

    RequestApi requestApi();

    FirebaseManager fireBaseManager();

    WallApi wallApi();

    WallCommentApi wallCommentApi();

}
