package com.BarcelonaSC.BarcelonaApp.ui.profile.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.NotificationManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.login.AuthActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.profile.ProfileFragment;
import com.BarcelonaSC.BarcelonaApp.ui.profile.carnet.CarnetDigitalActivity;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.ReferredToActivity;
import com.BarcelonaSC.BarcelonaApp.ui.register.fragments.RegisterFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by leonardojpr on 11/10/17.
 */

public class HinchaFragment extends Fragment {

    @BindView(R.id.text_name)
    public FCMillonariosTextView textName;
    @BindView(R.id.input_nickname)
    EditText inputNickname;
    @BindView(R.id.btn_logout)
    LinearLayout btnLogout;
    @BindView(R.id.img_profile)
    public CircleImageView imgProfile;
    @BindView(R.id.icon_edit_img_profile)
    ImageView iconEditImgProfile;
    @BindView(R.id.text_registrado)
    TextView registrado;
    @BindView(R.id.id_hincha)
    FCMillonariosTextView id_hincha;
    SessionManager sessionManager;
    @BindView(R.id.view_btn_confirmit)
    LinearLayout viewBtnDiscardSave;
    @BindView(R.id.btn_save_change)
    Button btnSaveChange;
    @BindView(R.id.btn_discard_change)
    Button btnDiscardChange;
    @BindView(R.id.btn_carnet)
    LinearLayout btnCarnet;
    @BindView(R.id.btn_share)
    LinearLayout btnShare;
    @BindView(R.id.acerca_de)
    FCMillonariosTextView acercaDe;

    public Uri mCropImageUri;

    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hincha_oficial, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        sessionManager = new SessionManager(getActivity());

        btnDiscardChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataView(sessionManager.getUser());
                showActionButtonDiscarSave(false);
            }
        });

        acercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Send Google Analytics Screen Tag
                App.get().registerTrackScreen(Constant.Analytics.PROFILE + "." + Constant.ProfileTags.Terms);

                AcercaDeDialog acercaDeDialog = new AcercaDeDialog();
                acercaDeDialog.show(getActivity().getSupportFragmentManager(), "acercade");
            }
        });

        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputNickname.getText().toString().trim().isEmpty()) {
                    updateUser();
                } else {
                    Toast.makeText(getActivity(), "¡Tu Apodo no puede estar vacío!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        inputNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (inputNickname.getText().toString().length() == 0 || inputNickname.getText().toString().trim().equals(sessionManager.getUser().getApodo())) {
                    showActionButtonDiscarSave(false);
                } else {
                    showActionButtonDiscarSave(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnCarnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.getUser().getCedula() != null && !sessionManager.getUser().getCedula().equals("")) {
                    // Send Google Analytics card Tag
                    App.get().registerTrackScreen(Constant.Analytics.PROFILE + "." + Constant.ProfileTags.Card);

                    Intent intent = new Intent(getActivity(), CarnetDigitalActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    // Send Google Analytics event card fail
                    App.get().registerTrackEvent(Constant.Analytics.PROFILE, Constant.ActionTags.Clicked, Constant.ProfileTags.CardFail, 0);
                    Toast.makeText(getActivity(), "Completa todos los campos de registro para ver el carné en la pestaña \"Info Cuenta\"", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //moveToReferredFragment();
                showDialog("Próximamente podrás invitar a más Toreros");

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send Google Analytics event log out
                App.get().registerTrackEvent(Constant.Analytics.PROFILE, Constant.ActionTags.Clicked, Constant.ProfileTags.Logout, 0);

                sessionManager.setSession(null);
                sessionManager.setUrlLineUpShare("");
                sessionManager.setUser(null);
                FirebaseManager.getInstance().clearFirebaseManager();
                PreferenceManager.getInstance().setBoolean(Constant.Key.MONUMETAL_ID, false);
                NotificationManager.getInstance().setConfiguration(null);
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getActivity(), AuthActivity.class));
                getActivity().finish();
            }
        });
        if (sessionManager.getUser().getFechaRegistro() != null) {
            setDataView(sessionManager.getUser());
        } else {
            ((ProfileFragment) getParentFragment()).presenter.loadUser(sessionManager.getSession().getToken());
        }
    }

    private void setDataView(UserItem user) {
        textName.setText(user.getNombre() + " " + user.getApellido());
        if (user.getApodo() != null) {
            inputNickname.setText(user.getApodo());
        }
        registrado.setText("DESDE "
                + Commons.simpleDateFormat(user.getFechaRegistro()).substring(0, 2)
                + "/" + getMonthForInt(Integer.parseInt(Commons.simpleDateFormat(user.getFechaRegistro()).substring(3, 5))).substring(0, 3)
                + "/" + user.getFechaRegistro().substring(0, 4));
        id_hincha.setText("N°" + sessionManager.getSession().getIdUser());
        Glide.with(getActivity()).load(user.getFoto()).apply(new RequestOptions().error(R.drawable.silueta)).into(imgProfile);
    }

    public void updateDataView(UserItem user) {
        textName.setText(user.getNombre() + " " + user.getApellido());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri;

        // Google analytics send profile tag
        App.get().registerTrackScreen(Constant.Analytics.PROFILE + "." + Constant.ProfileTags.Photo);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = CropImage.getPickImageResultUri(getActivity(), data);
            if (CropImage.isReadExternalStoragePermissionsRequired(getActivity(), imageUri)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                }
            } else {
                startCropImageActivity(imageUri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                Uri resultUri = result.getUri();
                Glide.with(getActivity()).load(result.getUri()).into(imgProfile);
                mCropImageUri = resultUri;
                Log.d("probando", resultUri.toString());
                updateUser();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(RegisterFragment.class.getSimpleName(), error.toString());
            }
        }
    }

    @OnClick({R.id.icon_edit_img_profile, R.id.img_profile})
    public void onSelectImageClick() {
        if (CropImage.isExplicitCameraPermissionRequired(getActivity())) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
        } else {
            CropImage.startPickImageActivity(getActivity());
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                .setFixAspectRatio(true)
                .start(getActivity());
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

    private void updateUser() {
        user = new User();
        user.setApodo(inputNickname.getText().toString().trim());
        user.setNombre(sessionManager.getUser().getNombre());

        if (mCropImageUri != null) {
            InputStream imageStream = null;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(mCropImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            String encodedImage = encodeImage(selectedImage);
            user.setFoto("data:image/png;base64," + encodedImage);
        }

        ((ProfileFragment) getParentFragment()).presenter.updateUser(user, new SessionManager(getActivity()).getSession().getToken());
        showActionButtonDiscarSave(false);
    }

    private void showActionButtonDiscarSave(boolean isShow) {
        if (isShow) {
            viewBtnDiscardSave.setVisibility(View.VISIBLE);
        } else {
            viewBtnDiscardSave.setVisibility(View.GONE);
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

    public void share() {

        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, SessionManager.getInstance().getUser().getApodo());

        PackageManager pm = getActivity().getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");

        Intent openInChooser = Intent.createChooser(emailIntent, "Compartir");
        openInChooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;

            Intent intent = new Intent();
            intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, Commons.getString(R.string.url_api) + "compartir/usr/" + SessionManager.getInstance().getSession().getCodigo());
            intent.setType("text/plain");
            intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
        }

        // convert intentList to array
        LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);

        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        getActivity().startActivity(openInChooser);
    }

    private void moveToReferredFragment() {
        Intent intent = new Intent(getActivity(), ReferredToActivity.class);
        getActivity().startActivity(intent);
        //Toast.makeText(getActivity(), "Esta opción estará activa para la próxima actualización", Toast.LENGTH_SHORT).show();
    }

    public void showDialog(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_ok, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        FCMillonariosTextView fcMillonariosTextView = dialoglayout.findViewById(R.id.fcm_tv_tittle);
        fcMillonariosTextView.setText(message);
        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_ok);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

}