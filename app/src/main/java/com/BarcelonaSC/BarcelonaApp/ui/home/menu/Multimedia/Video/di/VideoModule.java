package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.di;

import com.BarcelonaSC.BarcelonaApp.app.api.MultimediaApi;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.VideoFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.mvp.VideoModel;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.mvp.VideoPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Deivys on 3/29/2018.
 */
@Module
public class VideoModule {

    private VideoFragment videoFragment;
    private String type;

    public VideoModule(VideoFragment videoFragment, String type) {
        this.videoFragment = videoFragment;
        this.type = type;
    }

    @Provides
    @VideoScope
    public VideoFragment provideVideoFragment() {
        return videoFragment;
    }

    @Provides
    @VideoScope
    public VideoModel provideModel(MultimediaApi multimediaApi) {
        return new VideoModel(multimediaApi);
    }

    @Provides
    @VideoScope
    public VideoPresenter provideVideoPresenter(VideoModel model) {
        return new VideoPresenter(videoFragment, model, type);
    }

}
