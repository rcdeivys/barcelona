package com.BarcelonaSC.BarcelonaApp.utils.giphy.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.giphy.sdk.core.models.Media;

import java.util.List;

/**
 * Created by Carlos on 15/03/2018.
 */

public class GiphyContract {

    public interface View {
        void onGetTrendingGif(List<Media> gifs);

        void onGetSeachGif(List<Media> gifs);

        void showProgress();

        void hideProgress();
    }

    public interface Presenter extends MVPContract.Presenter<GiphyContract.View> {

    }

}
