package com.millonarios.MillonariosFC.ui.lineup.dragAndDropPlayer;


import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.utils.CircleDragDrop;
import com.millonarios.MillonariosFC.models.IdealElevenData;
import com.millonarios.MillonariosFC.models.NominaItem;
import com.millonarios.MillonariosFC.ui.lineup.UtilDragAnDrop;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 14/11/2017.
 */

public class DragDropPlayerDialogFragment extends DialogFragment {


    RecyclerView rvPlayers;
    FrameLayout soccesFieldView;
    ImageView ivTrash;
    Button btnOk;

    private PlayerDropListener playerDropListener;
    private DragDropAdapter dragDropAdapter;
    private ArrayList<NominaItem> playerPlayByPlays;

    ArrayList<IdealElevenData> idealElevenData = new ArrayList<>();

    public static final String TAG = DragDropPlayerDialogFragment.class.getSimpleName();

    public static DragDropPlayerDialogFragment newInstance() {

        Bundle args = new Bundle();

        DragDropPlayerDialogFragment fragment = new DragDropPlayerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment_drag_drop_player, container,
                false);
        rvPlayers = (RecyclerView) rootView.findViewById(R.id.rv_players);
        soccesFieldView = (FrameLayout) rootView.findViewById(R.id.socces_field_view);
        btnOk = (Button) rootView.findViewById(R.id.btn_ok);
        ivTrash = (ImageView) rootView.findViewById(R.id.iv_trash);
        if (idealElevenData.size() > 0) {

            for (IdealElevenData idealEleven : idealElevenData) {

                for (int i = 0; i < playerPlayByPlays.size(); i++) {
                    if (playerPlayByPlays.get(i).getIdJugador() == idealEleven.getPlayerPlayByPlay().getIdJugador()) {
                        playerPlayByPlays.remove(i);
                    }
                }
            }


        }

        soccesFieldView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                soccesFieldView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (idealElevenData.size() > 0)
                    UtilDragAnDrop.paintPlayerDropDialog(soccesFieldView, idealElevenData, getContext());

            }
        });

        dragDropAdapter = new DragDropAdapter(playerPlayByPlays);
        TextView tvCloset = (TextView) rootView.findViewById(R.id.tv_closet);

        tvCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerDropListener.onPlayerDropSave(idealElevenData, encodeImage(convertLayoutToImage()), true);
                dismiss();
            }
        });

        rvPlayers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        rvPlayers.getRecycledViewPool().setMaxRecycledViews(0, 0);
        rvPlayers.setAdapter(dragDropAdapter);
        ivTrash.setOnDragListener(new MyDragTranshListener());

        soccesFieldView.setOnDragListener(new MyDragListener());
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (idealElevenData.size() == 11) {
                    playerDropListener.onPlayerDropSave(idealElevenData, encodeImage(convertLayoutToImage()), false);
                    dismiss();
                } else {
                    Toast.makeText(App.getAppContext(), R.string.select_11_player, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }


    public void setPlayerList(List<NominaItem> playersList, PlayerDropListener playerDropListener) {
        playerPlayByPlays = new ArrayList<>(playersList);
        this.playerDropListener = playerDropListener;

    }

    public void setPlayerList(List<NominaItem> playersList, List<IdealElevenData> titularCache, PlayerDropListener playerDropListener) {
        playerPlayByPlays = new ArrayList<>(playersList);
        idealElevenData = new ArrayList<>(titularCache);
        this.playerDropListener = playerDropListener;

    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));

        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    class MyDragListener implements View.OnDragListener {
        /* Drawable enterShape = getResources().getDrawable(
                 R.drawable.shape_droptarget);
         Drawable normalShape = getResources().getDrawable(R.drawable.shape);*/
        Point touchPosition;
        private boolean isEnter = false;
        private boolean isMoving = false;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    isEnter = true;
                    //   v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    isEnter = false;
                    //     v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup


                    if (isEnter && !isMoving) {
                        isMoving = true;
                        CircleDragDrop view = (CircleDragDrop) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        FrameLayout container = (FrameLayout) v;
                        if (owner.getId() == container.getId() || idealElevenData.size() < 11) {
                            owner.removeView(view);


                            int sizeImage = UtilDragAnDrop.cpSizeImage(container.getWidth(), container.getHeight());
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(sizeImage
                                    , sizeImage);

                            int x = Math.round(event.getX());
                            int y = Math.round(event.getY());

                            if (x + view.getWidth() > container.getWidth())
                                x = x - (x + view.getWidth() - container.getWidth());
                            if (y + view.getHeight() > container.getHeight())
                                y = y - (y + view.getHeight() - container.getHeight());

                            view.setPositionX(UtilDragAnDrop.sizeXToCancha210(x, v.getWidth()));
                            view.setPositionY(UtilDragAnDrop.sizeYToCancha310(y, v.getHeight()));

                            params.leftMargin = x;
                            params.topMargin = y;
                            container.addView(view, params);
                            view.setVisibility(View.VISIBLE);
                            dragDropAdapter.removePlayerFromList(view.getPlayerPlayByPlay());

                            if (idealElevenData.size() == 0)
                                idealElevenData.add(view.getIdealElevenData());
                            else {
                                Boolean havePlayer = false;
                                for (IdealElevenData idealEleven : idealElevenData) {
                                    if (idealEleven.getPlayerPlayByPlay().getIdJugador() == view.getIdealElevenData().getPlayerPlayByPlay().getIdJugador()) {
                                        idealEleven.setPositionX(UtilDragAnDrop.sizeXToCancha210(x, v.getWidth()));
                                        idealEleven.setPositionY(UtilDragAnDrop.sizeYToCancha310(y, v.getHeight()));
                                        havePlayer = true;
                                        break;
                                    }
                                }
                                if (!havePlayer)
                                    idealElevenData.add(view.getIdealElevenData());

                            }

                        } else {
                            view = (CircleDragDrop) event.getLocalState();
                            view.setVisibility(View.VISIBLE);
                        }
                        //dqwdq
                    } else {
                        CircleDragDrop view = (CircleDragDrop) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                    }
                    isEnter = false;
                    isMoving = false;
                    dragDropAdapter.notifyDataSetChanged();
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    touchPosition = getTouchPositionFromDragEvent(v, event);
                case DragEvent.ACTION_DRAG_ENDED:

                    if (!isEnter) {
                        CircleDragDrop view = (CircleDragDrop) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        dragDropAdapter.notifyDataSetChanged();
                    }
                default:
                    break;
            }
            return true;
        }
    }

    class MyDragTranshListener implements View.OnDragListener {
        /* Drawable enterShape = getResources().getDrawable(
                 R.drawable.shape_droptarget);
         Drawable normalShape = getResources().getDrawable(R.drawable.shape);*/
        Point touchPosition;
        private boolean isEnter = false;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    isEnter = true;
                    //   v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    isEnter = false;
                    //     v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    if (isEnter) {

                        CircleDragDrop view = (CircleDragDrop) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);

                        if (!playerPlayByPlays.contains(view.getPlayerPlayByPlay()))
                            playerPlayByPlays.add(view.getPlayerPlayByPlay());

                        for (int i = 0; i < idealElevenData.size(); i++) {
                            if (idealElevenData.get(i).getPlayerPlayByPlay().getIdJugador() == view.getIdealElevenData().getPlayerPlayByPlay().getIdJugador()) {
                                idealElevenData.remove(i);
                                break;
                            }

                        }
                    } else {
                        CircleDragDrop view = (CircleDragDrop) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                    }
                    isEnter = false;
                    dragDropAdapter.notifyDataSetChanged();
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    touchPosition = getTouchPositionFromDragEvent(v, event);
                case DragEvent.ACTION_DRAG_ENDED:

                    if (!isEnter) {
                        CircleDragDrop view = (CircleDragDrop) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        dragDropAdapter.notifyDataSetChanged();
                    }
                default:
                    break;
            }
            return true;
        }
    }

    public static Point getTouchPositionFromDragEvent(View item, DragEvent event) {
        Rect rItem = new Rect();
        item.getGlobalVisibleRect(rItem);
        return new Point(rItem.left + Math.round(event.getX()), rItem.top + Math.round(event.getY()));
    }

    public interface PlayerDropListener {

        void onPlayerDropSave(List<IdealElevenData> idealElevenData, String imageEncode, Boolean saveForLate);

    }

    private Bitmap convertLayoutToImage() {

       /* soccesFieldView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        soccesFieldView.layout(0, 0, soccesFieldView.getMeasuredWidth(), soccesFieldView.getMeasuredHeight());
*/
        soccesFieldView.setDrawingCacheEnabled(true);
        soccesFieldView.buildDrawingCache();
        return soccesFieldView.getDrawingCache();// creates bitmap and returns the same
    }

    private String encodeImage(Bitmap bm) {
        Bitmap resize = scaleDown(bm, 748, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resize.compress(Bitmap.CompressFormat.PNG, 25, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min((float) maxImageSize / realImage.getWidth()
                , (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }

}