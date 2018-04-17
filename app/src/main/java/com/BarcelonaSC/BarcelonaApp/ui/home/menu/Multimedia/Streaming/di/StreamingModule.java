package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.di;

import com.BarcelonaSC.BarcelonaApp.app.api.MultimediaApi;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.StreamingFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.mvp.StreamingModel;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.mvp.StreamingPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Deivys on 4/16/2018.
 */

@Module
public class StreamingModule {

    private StreamingFragment streamingFragment;
    private String type;

    public StreamingModule(StreamingFragment streamingFragment, String type) {
        this.streamingFragment = streamingFragment;
        this.type = type;
    }

    @Provides
    @StreamingScope
    public StreamingFragment provideVideoFragment() {
        return streamingFragment;
    }

    @Provides
    @StreamingScope
    public StreamingModel provideModel(MultimediaApi multimediaApi) {
        return new StreamingModel(multimediaApi);
    }

    @Provides
    @StreamingScope
    public StreamingPresenter provideStreamingPresenter(StreamingModel model) {
        return new StreamingPresenter(streamingFragment, model, type);
    }

}
