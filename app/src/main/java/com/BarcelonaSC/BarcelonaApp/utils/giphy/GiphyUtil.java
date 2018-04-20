package com.BarcelonaSC.BarcelonaApp.utils.giphy;

import android.util.Log;

import com.giphy.sdk.core.models.Media;
import com.giphy.sdk.core.models.enums.LangType;
import com.giphy.sdk.core.models.enums.MediaType;
import com.giphy.sdk.core.models.enums.RatingType;
import com.giphy.sdk.core.network.api.CompletionHandler;
import com.giphy.sdk.core.network.api.GPHApi;
import com.giphy.sdk.core.network.api.GPHApiClient;
import com.giphy.sdk.core.network.response.ListMediaResponse;

import java.util.List;

/**
 * Created by quint on 3/13/2018.
 */

public class GiphyUtil {

    private GPHApi client;
    private GiphyListener giphyListener;

    public GiphyUtil(GiphyListener giphyListener) {
        this.giphyListener = giphyListener;
        client = new GPHApiClient("CUfqAwj8bhSr2uWUttE3zRckHU1l4z4R");
    }


    public void getTrendingGif(int pagination) {
        getTrending(pagination);
    }

    public void getTrendingGif() {
        getTrending(0);
    }

    public void seachGif(String seach, int pagination) {
        seach(seach, pagination);
    }

    public void seachGif(String seach) {
        seach(seach, 1);
    }

    private void getTrending(int number) {
        /// Gif Search
        client.trending(MediaType.gif, 25 * number - 1, 25 * number, null, new CompletionHandler<ListMediaResponse>() {
            @Override
            public void onComplete(ListMediaResponse result, Throwable e) {
                if (result == null) {
                    // Do what you want to do with the error
                } else {
                    if (result.getData() != null) {
                        giphyListener.onGetTrendingGif(result.getData());
                        for (Media gif : result.getData()) {
                            Log.i("tag", "///giphy1" + gif.getBitlyGifUrl());
                            Log.i("tag", "///giphy2" + gif.getBitlyUrl());
                            Log.i("tag", "///giphy3" + gif.getContentUrl());
                            Log.i("tag", "///giphy4" + gif.getUrl());
                            Log.i("tag", "///giphy5" + gif.getSourcePostUrl());
                            Log.i("tag", "///giphy6" + gif.getEmbedUrl());
                        }
                    } else {
                        Log.e("giphy error", "No results found");
                    }
                }
            }
        });

    }

    private void seach(String seach, int pagination) {
        /// Gif Search
        Log.v("giphy", seach);
        client.search(seach, MediaType.gif, 25 * pagination - 1, 25 * pagination, RatingType.r, LangType.english, new CompletionHandler<ListMediaResponse>() {
            @Override
            public void onComplete(ListMediaResponse result, Throwable e) {
                if (result == null) {
                    // Do what you want to do with the error
                } else {
                    if (result.getData() != null) {
                        giphyListener.onGetSeachGif(result.getData());
                        for (Media gif : result.getData()) {
                            Log.v("giphy", gif.getId());
                        }
                    } else {
                        Log.e("giphy error", "No results found");
                    }
                }
            }
        });
    }

    public interface GiphyListener {

        void onGetTrendingGif(List<Media> gifs);

        void onGetSeachGif(List<Media> gifs);

        void onError();
    }
}
