package com.millonarios.MillonariosFC.ui.chat.requests.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.ui.chat.requests.RequestConsts;
import com.millonarios.MillonariosFC.ui.chat.requests.RequestModelView;
import com.millonarios.MillonariosFC.utils.Commons;
import com.millonarios.MillonariosFC.utils.FCMillonariosTextView;
import com.millonarios.MillonariosFC.utils.PhotoUpload;

import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Cesar on 25/01/2018.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    private static final String TAG = RequestAdapter.class.getSimpleName();

    private Context context;
    private List<RequestModelView> requestList;
    private OnItemClickListener onItemClickListener;
    private boolean isSeach = false;

    public RequestAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<RequestModelView> requestList, boolean isSeach) {
        this.requestList = requestList;
        this.isSeach = isSeach;
    }

    @Override
    public int getItemViewType(int position) {
        if (isSeach) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_solicitudes, parent, false));
            case 1:
                return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_sugerencias, parent, false));
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_sugerencias, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(requestList.get(position), context, onItemClickListener);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_photo)
        CircleImageView civPhoto;
        @BindView(R.id.tv_apodo)
        FCMillonariosTextView tvApodo;
        @Nullable
        @BindView(R.id.tv_solicitud_enviar)
        FCMillonariosTextView tv_solicitud_enviar;
        @BindView(R.id.btn_accept)
        Button btn_accept;
        @BindView(R.id.btn_cancel)
        Button btn_cancel;
        Context context;

        RequestModelView requestModelView;
        private OnItemClickListener onItemClickListener;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(RequestModelView r, Context context, OnItemClickListener listener) {
            this.requestModelView = r;
            this.onItemClickListener = listener;
            this.context = context;
            PhotoUpload.uploadFoto(requestModelView.getId(), new PhotoUpload.PhotoListener() {
                @Override
                public void onPhotoSucces(String foto) {

                    Glide.with(App.get()).load(foto)
                            .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                            .into(civPhoto);
                }

                @Override
                public void onError() {

                }
            });
            if (isSeach) {
                if (requestModelView.isSend()) {
                    btn_cancel.setVisibility(View.VISIBLE);
                    btn_accept.setVisibility(View.GONE);
                    tv_solicitud_enviar.setVisibility(View.VISIBLE);
                } else {
                    btn_cancel.setVisibility(View.GONE);
                    btn_accept.setVisibility(View.VISIBLE);
                    tv_solicitud_enviar.setVisibility(View.GONE);
                }
            }


            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isSeach)
                        onItemClickListener.onClickItem(requestModelView, RequestConsts.TAG_ON_CLICK_INVITED);
                    else
                        onItemClickListener.onClickItem(requestModelView, RequestConsts.TAG_ON_CLICK_ACEPTAR);
                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isSeach)
                        onItemClickListener.onClickItem(requestModelView, RequestConsts.TAG_ON_OWNER_CLICK_CANCEL);
                    else
                        onItemClickListener.onClickItem(requestModelView, RequestConsts.TAG_ON_CLICK_CANCEL);


                }
            });


            tvApodo.setText(nameOrApodo(requestModelView));
        }

        public String nameOrApodo(RequestModelView requestModelView) {
            String apodo = requestModelView.getApodo();
            if (apodo != null && !apodo.isEmpty())
                return requestModelView.getApodo();
            else
                return requestModelView.getNombre();
        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickItem(RequestModelView friendsModelView, String TAG);

        void onLongClickItem(RequestModelView friendsModelView);
    }


}
