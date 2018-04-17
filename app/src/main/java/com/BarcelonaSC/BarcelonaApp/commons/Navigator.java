package com.BarcelonaSC.BarcelonaApp.commons;

import android.content.Intent;

import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaDataResponse;
import com.BarcelonaSC.BarcelonaApp.models.VideoReality;

/**
 * Created by Carlos on 01/10/2017.
 */

public interface Navigator {


    void navigateToNewsDetailsActivity(News news);

    void navigateToVideoNewsActivity(News news);

    void navigateToInfografiaActivity(News news);

    void navigateToHomeActivity();

    void navigateToGalleryActivity(int id);

    void navigateToVideoMultimediaActivity(MultimediaDataResponse multimediaDataResponse, int currentPosition);

    void navigateToPlayerActivity(int playerId, String type);

    void navigateToMonumentalProfile(String monumentalId, String pollId);

    void navigateToLiveActivity();

    void navigateToGameActivity();

    void navigateVirtualActivity(VideoReality videoReality);

    void navigateToActivity(Intent intent);


}
