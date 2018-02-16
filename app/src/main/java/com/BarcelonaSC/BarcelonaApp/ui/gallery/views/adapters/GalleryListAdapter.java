package com.BarcelonaSC.BarcelonaApp.ui.gallery.views.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.models.GalleryItem;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.views.holders.GalleryViewHolder;

import java.util.List;

/**
 * Created by Leonardojpr on 10/12/17.
 */

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryViewHolder> {

    List<GalleryItem> galleryModelList;
    ImgGalleryClickListener imgGalleryClickListener;

    public GalleryListAdapter(List<GalleryItem> galleryModelList) {
        this.galleryModelList = galleryModelList;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return GalleryViewHolder.getInstance(viewGroup);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder viewHolder, final int i) {
        viewHolder.setImgGallery(galleryModelList.get(i).getFoto(), i);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgGalleryClickListener.onImgClickListener(galleryModelList.get(i).getFoto(), i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryModelList.size();
    }

    public void setImgGalleryClickListener(ImgGalleryClickListener imgGalleryClickListener) {
        this.imgGalleryClickListener = imgGalleryClickListener;
    }

    public interface ImgGalleryClickListener {
        void onImgClickListener(String url, int position);
    }

    public void updateAll(List<GalleryItem> itemList) {
        galleryModelList.clear();
        galleryModelList = itemList;
        notifyDataSetChanged();
    }
}
