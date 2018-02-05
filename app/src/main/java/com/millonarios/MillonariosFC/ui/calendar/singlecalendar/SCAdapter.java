package com.millonarios.MillonariosFC.ui.calendar.singlecalendar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.utils.Commons;
import com.millonarios.MillonariosFC.utils.FCMillonariosTextView;
import com.millonarios.MillonariosFC.models.SCalendarNoticia;
import com.millonarios.MillonariosFC.ui.gallery.GalleryListActivity;
import com.millonarios.MillonariosFC.ui.news.NewsDetailsActivity;
import com.millonarios.MillonariosFC.ui.news.NewsInfografyActivity;
import com.millonarios.MillonariosFC.ui.news.NewsVideoActivity;
import com.millonarios.MillonariosFC.utils.Constants.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Amplex on 8/11/2017.
 */

public class SCAdapter extends ArrayAdapter<SCalendarNoticia> {

    public SCAdapter(Context context, ArrayList<SCalendarNoticia> noticias) {
        super(context, R.layout.item_news, noticias);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final SCalendarNoticia sCalendarNoticia = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View box = inflater.inflate(R.layout.item_news, null);

        ImageView ivNews = box.findViewById(R.id.iv_news);
        FCMillonariosTextView date = box.findViewById(R.id.tv_date);
        FCMillonariosTextView title = box.findViewById(R.id.tv_title);

        assert sCalendarNoticia != null;
        Glide.with(getContext()).load(sCalendarNoticia.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.millos_news_wm).error(R.drawable.millos_news_wm))
                .into(ivNews);
        date.setText(Commons.getStringDate(sCalendarNoticia.getFecha()));
        title.setText(sCalendarNoticia.getTitulo());

        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sCalendarNoticia.getTipo().matches(Constant.NewsType.GALERY)) {
                    Intent intent = new Intent(getContext(), GalleryListActivity.class);
                    intent.putExtra(Constant.Key.ID, sCalendarNoticia.getId());
                    getContext().startActivity(intent);
                } else if (sCalendarNoticia.getTipo().matches(Constant.NewsType.VIDEO)) {
                    Intent intent = new Intent(getContext(), NewsVideoActivity.class);
                    intent.putExtra(Constant.Key.URL, sCalendarNoticia.getLink());
                    getContext().startActivity(intent);
                } else if (sCalendarNoticia.getTipo().matches(Constant.NewsType.INFOGRAFY) || sCalendarNoticia.getTipo().matches(Constant.NewsType.STAT)) {
                    Intent intent = new Intent(getContext(), NewsInfografyActivity.class);
                    intent.putExtra(Constant.Key.URL, sCalendarNoticia.getLink());
                    getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
                    intent.putExtra(Constant.Key.TITLE, sCalendarNoticia.getTitulo());
                    intent.putExtra(Constant.Key.DESC_NEW, sCalendarNoticia.getDescripcion());
                    intent.putExtra(Constant.Key.IMG, sCalendarNoticia.getFoto());
                    getContext().startActivity(intent);
                }

            }
        });

        return box;
    }

    private String formatDate(String date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        Date parse = null;
        try {
            parse = fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy", new Locale("es","ES"));

        return fmtOut.format(parse);
    }

}