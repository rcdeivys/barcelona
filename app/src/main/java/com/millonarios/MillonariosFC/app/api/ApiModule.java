package com.millonarios.MillonariosFC.app.api;



import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.app.network.RetrofitModule;
import com.millonarios.MillonariosFC.app.di.AppScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Carlos-pc on 28/09/2017.
 */
@Module(includes = RetrofitModule.class)
public class ApiModule {

    private static final String TAG = ApiModule.class.getSimpleName();

    @Provides
    @AppScope
    public static ConfigurationApi provideConfigurationApi(Retrofit retrofit) {
        return retrofit.create(ConfigurationApi.class);
    }

    @Provides
    @AppScope
    public static TeamApi provideTeamApi(Retrofit retrofit) {
        return retrofit.create(TeamApi.class);
    }

    @Provides
    @AppScope
    public static NewsApi provideNewsApi(Retrofit retrofit) {
        return retrofit.create(NewsApi.class);
    }

    @Provides
    @AppScope
    public static GalleryApi provideGalleryApi(Retrofit retrofit) {
        return retrofit.create(GalleryApi.class);
    }

    @Provides
    @AppScope
    public static TournamentApi provideTournamentApi(Retrofit retrofit) {
        return retrofit.create(TournamentApi.class);
    }

    @Provides
    @AppScope
    public static SCalendarApi provideCalendarApi(Retrofit retrofit) {
        return retrofit.create(SCalendarApi.class);
    }

    @Provides
    @AppScope
    public static VRApi provideVirtualRealityApi(Retrofit retrofit) {
        return retrofit.create(VRApi.class);
    }

    @Provides
    @AppScope
    public static UserPhotoApi provideUserPhotoApi(Retrofit retrofit) {
        return retrofit.create(UserPhotoApi.class);
    }

    @Provides
    @AppScope
    public static LineUpApi provideLineUpApi(Retrofit retrofit) {
        return retrofit.create(LineUpApi.class);
    }

    @Provides
    @AppScope
    public static AuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }

    @Provides
    @AppScope
    public static ProfileApi provideProfileApi(Retrofit retrofit) {
        return retrofit.create(ProfileApi.class);
    }

    @Provides
    @AppScope
    public static BannerApi provideBannerApi(Retrofit retrofit) {
        return retrofit.create(BannerApi.class);
    }

    @Provides
    @AppScope
    public static YouChooseApi provideYouChooseApi(Retrofit retrofit) {
        return retrofit.create(YouChooseApi.class);
    }

    @Provides
    @AppScope
    public static RecoveryPasswordApi provideRecoveryApi(Retrofit retrofit) {
        return retrofit.create(RecoveryPasswordApi.class);
    }

    @Provides
    @AppScope
    public static ReferredApi provideReferredApi(Retrofit retrofit) {
        return retrofit.create(ReferredApi.class);
    }

    @Provides
    @AppScope
    public static GroupsApi provideGroupsApi(Retrofit retrofit) {
        return retrofit.create(GroupsApi.class);
    }

    @Provides
    @AppScope
    public static FriendsApi provideFriendsApi(Retrofit retrofit) {
        return retrofit.create(FriendsApi.class);
    }

    @Provides
    @AppScope
    public static RequestApi provideRequestApi(Retrofit retrofit) {
        return retrofit.create(RequestApi.class);
    }

    @Provides
    @AppScope
    public static FirebaseManager provideFirebaseManager() {
        FirebaseManager manager = FirebaseManager.getInstance();
        return manager;
    }

    @Provides
    @AppScope
    public static WallApi provideWallApi(Retrofit retrofit) {
        return retrofit.create(WallApi.class);
    }

    @Provides
    @AppScope
    public static WallCommentApi provideWallCommentApi(Retrofit retrofit) {
        return retrofit.create(WallCommentApi.class);
    }

}