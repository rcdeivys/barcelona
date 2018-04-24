package com.BarcelonaSC.BarcelonaApp.app.di;

import com.BarcelonaSC.BarcelonaApp.app.api.ApiModule;
import com.BarcelonaSC.BarcelonaApp.app.api.AuthApi;
import com.BarcelonaSC.BarcelonaApp.app.api.BannerApi;
import com.BarcelonaSC.BarcelonaApp.app.api.ChatApi;
import com.BarcelonaSC.BarcelonaApp.app.api.ConfigurationApi;
import com.BarcelonaSC.BarcelonaApp.app.api.FriendsApi;
import com.BarcelonaSC.BarcelonaApp.app.api.GalleryApi;
import com.BarcelonaSC.BarcelonaApp.app.api.GroupsApi;
import com.BarcelonaSC.BarcelonaApp.app.api.LineUpApi;
import com.BarcelonaSC.BarcelonaApp.app.api.MapApi;
import com.BarcelonaSC.BarcelonaApp.app.api.MonumentalApi;
import com.BarcelonaSC.BarcelonaApp.app.api.MultimediaApi;
import com.BarcelonaSC.BarcelonaApp.app.api.NewsApi;
import com.BarcelonaSC.BarcelonaApp.app.api.ProfileApi;
import com.BarcelonaSC.BarcelonaApp.app.api.RecoveryPasswordApi;
import com.BarcelonaSC.BarcelonaApp.app.api.ReferredApi;
import com.BarcelonaSC.BarcelonaApp.app.api.RequestApi;
import com.BarcelonaSC.BarcelonaApp.app.api.SCalendarApi;
import com.BarcelonaSC.BarcelonaApp.app.api.TeamApi;
import com.BarcelonaSC.BarcelonaApp.app.api.TournamentApi;
import com.BarcelonaSC.BarcelonaApp.app.api.UserPhotoApi;
import com.BarcelonaSC.BarcelonaApp.app.api.VRApi;
import com.BarcelonaSC.BarcelonaApp.app.api.WallApi;
import com.BarcelonaSC.BarcelonaApp.app.api.WallCommentApi;
import com.BarcelonaSC.BarcelonaApp.app.api.WallSearchApi;
import com.BarcelonaSC.BarcelonaApp.app.api.YouChooseApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;

import dagger.Component;

/**
 * Created by Carlos on 01/10/2017.
 */

@AppScope
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    ConfigurationApi configurationApi();

    TeamApi teamApi();

    MultimediaApi multimediaApi();

    NewsApi newsApi();

    BannerApi bannerApi();

    GalleryApi galleryApi();

    TournamentApi tournamentApi();

    SCalendarApi scalendarApi();

    LineUpApi lineUpApi();

    YouChooseApi youChooseApi();

    UserPhotoApi userPhotoApi();

    ChatApi chatApi();

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

    MonumentalApi monumentalApi();

    MapApi mapApi();

    WallSearchApi wallSearchApi();

}
