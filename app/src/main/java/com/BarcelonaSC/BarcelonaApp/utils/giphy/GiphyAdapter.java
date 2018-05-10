package com.BarcelonaSC.BarcelonaApp.utils.giphy;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.giphy.sdk.core.models.Media;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by quint on 3/13/2018.
 */

public class GiphyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = GiphyAdapter.class.getSimpleName();


    private Context context;
    private List<Media> gifs;
    private List<GifDrawable> gifDrawable;
    private OnGifClickListener onGifClickListener;

    public GiphyAdapter(Context context, OnGifClickListener onGifClickListener) {
        this.context = context;
        this.onGifClickListener = onGifClickListener;

    }

    public void setListener(OnGifClickListener onGifClickListener) {
        this.onGifClickListener = onGifClickListener;
    }


    public void setData(List<Media> data) {
        this.gifs = data;
        gifDrawable = new ArrayList<>();
        for (Media media : data) {
            gifDrawable.add(null);
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new GiphyHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_giphy, parent, false));

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final GiphyHolder giphyHolder = (GiphyHolder) holder;

        //  final String gifUrl = "https://giphy.com/gifs/" + gifs.get(position).getId() + "/html5";
        final String gifUrl = "http://media2.giphy.com/media/" + gifs.get(position).getId() + "/giphy.gif";
        Log.i(TAG, "/////--->curren gif url: " + gifUrl);

         /*   Glide.with(context)
                    .asGif()
                    .load(gifUrl)
                    .thumbnail(0.1f)
                    .apply(new RequestOptions()
                            .placeholder(new ColorDrawable(ContextCompat.getColor(context, R.color.gray_300)))
                            .error(new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent)))
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(giphyHolder.qivGif);*/

        giphyHolder.qivGif.getHierarchy()
                .setPlaceholderImage(new ColorDrawable(ContextCompat.getColor(context, R.color.gray_300)));
        giphyHolder.qivGif.getHierarchy().setFailureImage(new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent)));

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(gifUrl))
                .setLocalThumbnailPreviewsEnabled(true)
                .setCacheChoice(ImageRequest.CacheChoice.SMALL)
                .build();


        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        giphyHolder.qivGif.setController(controller);

        giphyHolder.qivGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onGifClickListener != null)
                    onGifClickListener.onClickGif(gifUrl);
            }
        });

    }


    @Override
    public int getItemCount() {
        return gifs.size();
    }

    public interface OnGifClickListener {
        void onClickGif(String link);
    }

    class GiphyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.qiv_gif)
        SimpleDraweeView qivGif;

        GiphyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}