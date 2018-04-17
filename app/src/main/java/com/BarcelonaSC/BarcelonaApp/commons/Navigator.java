package com.BarcelonaSC.BarcelonaApp.commons;

import android.content.Intent;

import com.BarcelonaSC.BarcelonaApp.models.BeneficioData;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaDataResponse;
import com.BarcelonaSC.BarcelonaApp.models.VideoReality;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaDataResponse;

/**
 * Created by Carlos on 01/10/2017.
 */

public interface Navigator {

    void navigateToNewsDetailsActivity(News news);

    void navigateToVideoNewsActivity(News news, int currentPosition);

    void navigateToVideoMultimediaActivity(MultimediaDataResponse multimediaDataResponse, int currentPosition);

    void navigateToVideoBeneficiosActivity(BeneficioData beneficioData, int currentVideo);

    void navigateToInfografiaActivity(News news);

    void navigateToHomeActivity();

    void navigateToGalleryActivity(News news);

    void navigateVirtualActivity(VideoReality videoReality);

    void navigateToVideoMultimediaActivity(MultimediaDataResponse multimediaDataResponse, int currentPosition);

    void navigateToPlayerActivity(int playerId, String type);

    void navigateToMonumentalProfile(String monumentalId, String pollId);

    void navigateToLiveActivity();

    void navigateToGameActivity();

    void navigateToActivity(Intent intent);


}
