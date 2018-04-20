package com.BarcelonaSC.BarcelonaApp.utils.giphy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;
import com.BarcelonaSC.BarcelonaApp.utils.ItemOffsetDecoration;
import com.BarcelonaSC.BarcelonaApp.utils.giphy.mvp.GiphyContract;
import com.BarcelonaSC.BarcelonaApp.utils.giphy.mvp.GiphyPresenter;
import com.giphy.sdk.core.models.Media;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Created by quint on 3/13/2018.
 */

public class GiphyFragment extends BaseFragment implements GiphyContract.View {

    public static final String TAG = GiphyFragment.class.getSimpleName();
    @BindView(R.id.rv_gif)
    RecyclerView rvGif;
    Unbinder unbinder;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private GiphyPresenter giphyPresenter;

    GiphyAdapter giphyAdapter;
    GridLayoutManager gridLayoutManager;
    @BindView(R.id.et_seach_gif)
    EditText etSeachGif;
    GiphyAdapter.OnGifClickListener onGifClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        giphyPresenter = new GiphyPresenter();
    }


    public void setGifListener(GiphyAdapter.OnGifClickListener onGifClickListener) {
        this.onGifClickListener = onGifClickListener;
    }

    public void initAdapter() {
        if (giphyAdapter == null) {
            giphyAdapter = new GiphyAdapter(getContext(), onGifClickListener);
            giphyAdapter.setListener(onGifClickListener);
            rvGif.setAdapter(giphyAdapter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giphy, container, false);
        unbinder = ButterKnife.bind(this, view);
        giphyPresenter.onAttach(this);
        rvGif.setOnScrollListener(initRecyclerViewScroll());
        rvGif.setLayoutManager(gridLayoutManager);
        if (getContext() != null) {
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
            rvGif.addItemDecoration(itemDecoration);
        }
        if (giphyAdapter == null)
            giphyPresenter.getTrendingGif();
        etSeachGif.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (etSeachGif != null) {
                    if (giphyAdapter != null) {
                        giphyAdapter.setData(new ArrayList<Media>());
                        giphyAdapter.notifyDataSetChanged();
                    }

                    if ("".equals(etSeachGif.getText().toString())) {
                        giphyPresenter.getTrendingGif();
                    } else if (etSeachGif.getText().toString().length() > 0) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {

                            giphyPresenter.seachGif(etSeachGif.getText().toString());
                        }

                    }
                }
                return handled;
            }
        });
        return view;

    }

    @OnTextChanged(value = R.id.et_seach_gif, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged() {
        Log.i(TAG, "*/*/*/--->" + etSeachGif.getText().toString());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        giphyPresenter.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private EndlessScrollListener initRecyclerViewScroll() {
        return new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.i(TAG, "/////--->curren: " + current_page);
                giphyPresenter.getPaginationGif(current_page);
            }
        };
    }


    @Override
    public void onGetTrendingGif(List<Media> gifs) {
        hideProgress();
        Log.i(TAG, "////--->1" + gifs.size());
        initAdapter();
        giphyAdapter.setData(gifs);
        giphyAdapter.notifyDataSetChanged();
    }


    @Override
    public void onGetSeachGif(List<Media> gifs) {
        hideProgress();
        Log.i(TAG, "////--->2" + gifs.size());
        initAdapter();
        giphyAdapter.setData(gifs);
        giphyAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}