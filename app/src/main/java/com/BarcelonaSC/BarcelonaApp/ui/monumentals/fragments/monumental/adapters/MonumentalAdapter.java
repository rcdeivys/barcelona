package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.MonumentalPoll;
import com.BarcelonaSC.BarcelonaApp.ui.news.views.adapters.NewsAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RYA-Laptop on 17/02/2018.
 */

public class MonumentalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = MonumentalAdapter.class.getSimpleName();
    private Context context;
    private OnItemClickListener onItemClickListener;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private MonumentalPoll Poll;
    SimpleDateFormat formatOut;
    SimpleDateFormat formatIn;

    public MonumentalAdapter(Context context, MonumentalPoll poll) {
        this.context = context;
        this.Poll = poll;
        //onItemClickListener = context;
        formatOut = new SimpleDateFormat("d/MM/yyyy");
        formatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new MonumentalAdapter.ViewHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.header_survey_monumentals, parent, false));
        } else {
            return new MonumentalAdapter.ViewItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_monumental_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position - 1;
        if (holder instanceof ViewHeader) {
            ViewHeader header = (ViewHeader) holder;
            try {
                Date date1 = formatIn.parse(Poll.getFecha_fin());
                String formatOutStr = formatOut.format(date1);
                //header.datevoting.setText(String.format("Hasta el %1$s", String.valueOf(formatOutStr)));
                header.datevoting.setText("Desde el " + Commons.simpleDateFormat(Poll.getFecha_fin()).substring(0, 2) + " de " + getMonthForInt(Integer.parseInt(Commons.simpleDateFormat(Poll.getFecha_fin()).substring(3, 5))) + " " + Poll.getFecha_fin().substring(0, 4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            ViewItem item = (ViewItem) holder;
            Glide.with(context)
                    .load(Poll.getMonumentales().get(pos).getBanner())
                    .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                    .into(item.girl);

            item.girl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickItem(pos);
                }
            });
            item.monumental_name.setText(Poll.getMonumentales().get(pos).getNombre());
            item.monumental_name_container.setVisibility(View.GONE);
        }
    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("es", "ES"));
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 12) {
            month = months[num - 1];
        }
        return month;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return Poll.getMonumentales().size() + 1;
    }

    class ViewItem extends RecyclerView.ViewHolder {
        @BindView(R.id.player_img)
        ImageView girl;
        @BindView(R.id.monumental_name)
        FCMillonariosTextView monumental_name;
        @BindView(R.id.monumental_name_container)
        RelativeLayout monumental_name_container;

        ViewItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickItem(int position);
    }

    class ViewHeader extends RecyclerView.ViewHolder {
        @BindView(R.id.date_voting)
        TextView datevoting;

        ViewHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setData(MonumentalPoll Poll) {
        this.Poll = Poll;
        notifyDataSetChanged();
    }
}