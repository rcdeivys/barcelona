package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 9/15/17.
 */

public class LoadingFooter {

    public static final String TAG = LoadingFooter.class.getSimpleName();

    @BindView(R.id.loading_container)
    View loadingContainer;
    @BindView(R.id.empty_list_container)
    View emptyListContainer;
    @BindView(R.id.error_container)
    View errorContainer;
    @BindView(R.id.txt_empty_list)
    public TextView txtEmptyList;
    @BindView(R.id.txt_error)
    Button btnError;

    private View loading;
    private boolean isLoading = true;
    private boolean isVisible = true;
    private boolean isError = false;
    private String noMoreData;
    private String noMoreDataToDisplay;

    public LoadingFooter(Context context) {
        if (context != null) {
            loading = LayoutInflater.from(context).inflate(R.layout.view_loading_footer, null);
            ButterKnife.bind(this, loading);

            noMoreData = Commons.getString(R.string.no_more_data);
            noMoreDataToDisplay = Commons.getString(R.string.no_more_data_to_display);
        }
    }

    public void showLoading() {
        if (!isLoading || !isVisible) {
            show();
            isLoading = true;
            isError = false;
            loadingContainer.setVisibility(View.VISIBLE);
            emptyListContainer.setVisibility(View.INVISIBLE);
            errorContainer.setVisibility(View.INVISIBLE);
        }
    }

    public void showEmpty() {
        if (isLoading || isError || !isVisible) {
            show();
            isLoading = false;
            isError = false;
            loadingContainer.setVisibility(View.INVISIBLE);
            emptyListContainer.setVisibility(View.VISIBLE);
            errorContainer.setVisibility(View.INVISIBLE);
        }
    }

    public void showError(View.OnClickListener clickListener) {
        if (!isError || !isVisible) {
            show();
            btnError.setOnClickListener(clickListener);
            isLoading = false;
            isError = true;
            loadingContainer.setVisibility(View.INVISIBLE);
            emptyListContainer.setVisibility(View.INVISIBLE);
            errorContainer.setVisibility(View.VISIBLE);
        }
    }

    public void showNoMoreData() {
        txtEmptyList.setText(noMoreData);
        showEmpty();
    }

    public void showNoMoreDataToDisplay() {
        txtEmptyList.setText(noMoreDataToDisplay);
        showEmpty();
    }

    public void hide() {
        if (isVisible) {
            isVisible = false;
            loading.setVisibility(View.INVISIBLE);

        }
    }

    private void show() {
        if (!isVisible) {
            isVisible = true;
            loading.setVisibility(View.VISIBLE);
        }
    }

    public View getLoading() {
        return loading;
    }


    public TextView getTxtEmptyList() {
        return txtEmptyList;
    }
}
