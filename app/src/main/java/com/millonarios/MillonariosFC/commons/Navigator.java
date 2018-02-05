package com.millonarios.MillonariosFC.commons;

import android.content.Intent;

import com.millonarios.MillonariosFC.models.News;

/**
 * Created by Carlos on 01/10/2017.
 */

public interface Navigator {


    void navigateToNewsDetailsActivity(News news);

    void navigateToVideoNewsActivity(News news);

    void navigateToInfografiaActivity(News news);

    void navigateToHomeActivity();

    void navigateToGalleryActivity(int id);


    void navigateToPlayerActivity(int playerId, String type);

    void navigateToMonumentalProfile(String monumentalId, String pollId);

    void navigateToLiveActivity();

    void navigateToGameActivity();

    void navigateToActivity(Intent intent);


}
