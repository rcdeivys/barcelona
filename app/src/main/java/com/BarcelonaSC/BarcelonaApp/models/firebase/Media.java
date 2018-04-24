package com.BarcelonaSC.BarcelonaApp.models.firebase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 11/04/2018.
 */

public class Media {

    @SerializedName("url_media")
    @Expose
    private String urlMedia;
    @SerializedName("tipo_media")
    @Expose
    private String tipoMedia;
    @SerializedName("video_thumbnail")
    @Expose
    private String videoThumbnail;

    public String getUrlMedia() {
        return urlMedia;
    }

    public void setUrlMedia(String urlMedia) {
        this.urlMedia = urlMedia;
    }

    public String getTipoMedia() {
        return tipoMedia;
    }

    public void setTipoMedia(String tipoMedia) {
        this.tipoMedia = tipoMedia;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }
}
