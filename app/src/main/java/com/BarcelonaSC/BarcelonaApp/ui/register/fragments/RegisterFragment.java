package com.BarcelonaSC.BarcelonaApp.ui.register.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.response.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.login.AuthActivity;
import com.BarcelonaSC.BarcelonaApp.ui.register.di.DaggerRegisterComponent;
import com.BarcelonaSC.BarcelonaApp.ui.register.di.RegisterModule;
import com.BarcelonaSC.BarcelonaApp.ui.register.mvp.RegisterContract;
import com.BarcelonaSC.BarcelonaApp.ui.register.mvp.RegisterPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Leonardojpr on 11/9/17.
 */

public class RegisterFragment extends Fragment implements RegisterContract.View {

    @BindView(R.id.register_picture)
    CircleImageView regPicture;
    @BindView(R.id.register_name)
    EditText regName;
    @BindView(R.id.register_last)
    EditText regLastName;
    @BindView(R.id.register_ci)
    EditText regCI;
    @BindView(R.id.register_nick)
    EditText regNick;
    @BindView(R.id.register_email)
    EditText regEmail;
    @BindView(R.id.register_pass)
    EditText regPass;
    @BindView(R.id.register_cpass)
    EditText regCpass;
    @BindView(R.id.register_phone_code)
    EditText regPhoneCode;
    @BindView(R.id.register_phone_num)
    EditText regPhoneNum;
    @BindView(R.id.btn_day)
    EditText regDay;
    @BindView(R.id.btn_month)
    EditText regMonth;
    @BindView(R.id.btn_year)
    EditText regYear;
    @BindView(R.id.register_rb_male)
    RadioButton regRbMale;
    @BindView(R.id.register_rb_female)
    RadioButton regRbFemale;
    @BindView(R.id.register_cb_terms)
    AppCompatCheckBox regCbTerms;
    @BindView(R.id.register_btn_register)
    Button btnRegister;
    @BindView(R.id.register_btn_exit)
    Button btnExit;
    @BindView(R.id.progressBar)
    RelativeLayout progressBar;
    @BindView(R.id.politicas)
    FCMillonariosTextView politicias;

    public Uri mCropImageUri;

    @Inject
    RegisterPresenter presenter;
    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());

        initComponent();
        onBackPressed();

        regPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectImageClick();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity) getActivity()).authFragment();
            }
        });
        politicias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TermsAndConditionsDialog newFragment = new TermsAndConditionsDialog();
                newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });


        regPhoneCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (regPhoneCode.getText().toString().isEmpty()) {
                    regPhoneCode.setText("+");
                    regPhoneCode.setSelection(1);
                }
                return false;
            }
        });

        regPhoneCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (regPhoneCode.getText().length() == 0) {
                    regPhoneCode.setText("+");
                    regPhoneCode.setSelection(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void initComponent() {
        DaggerRegisterComponent.builder()
                .appComponent(App.get().component())
                .registerModule(new RegisterModule(this))
                .build().inject(RegisterFragment.this);
    }

    private void validateForm() {
        if (!regName.getText().toString().isEmpty()) {
            if (!regLastName.getText().toString().isEmpty()) {
                if (!regCI.getText().toString().isEmpty()) {
                    if (!regEmail.getText().toString().isEmpty()) {
                        if (regEmail.getText().toString().contains("@")) {
                            if (!regPhoneNum.getText().toString().isEmpty()) {
                                if (!regPhoneCode.getText().toString().isEmpty()
                                        && regPhoneCode.getText().toString().contains("+")) {
                                    if (!regPass.getText().toString().isEmpty()) {
                                        if (!regCpass.getText().toString().isEmpty()) {
                                            if (regCpass.getText().toString().equals(regPass.getText().toString())) {
                                                if (regCbTerms.isChecked()) {
                                                    registerUser();
                                                } else {
                                                    Toast.makeText(getActivity(), "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(), "Debes confirmar la contraseña", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "La contraseña no puede estar vacía", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "El código del teléfono debe contener el símbolo + al comienzo", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "El número telefónico no puede estar vacío", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "El e-mail no es válido", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "El correo no puede estar vacío", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "La cédula no puede estar vacía", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "El apellido no puede estar vacío", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser() {
        user = new User();
        user.setNombre(regName.getText().toString());
        user.setApellido(regLastName.getText().toString());
        user.setCi(regCI.getText().toString());
        user.setEmail(regEmail.getText().toString());
        user.setClave(regPass.getText().toString());
        user.setCelular(regPhoneCode.getText().toString() + " " + regPhoneNum.getText().toString());
        user.setFecha_nacimiento(regYear.getText().toString() + "/" + regMonth.getText().toString() + "/" + regDay.getText().toString());
        if (regRbMale.isChecked()) {
            user.setGenero(regRbMale.getText().toString());
        } else if (regRbFemale.isChecked()) {
            user.setGenero(regRbFemale.getText().toString());
        }
        if (mCropImageUri != null) {
            InputStream imageStream = null;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(mCropImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            String encodedImage = Commons.encodeImage(selectedImage);
            user.setFoto(Constant.BASE64 + encodedImage);
        }

        showProgress();
        presenter.loadRegister(user);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setRegister(GenericResponse data) {
        if (getActivity() != null) {
            ((AuthActivity) getActivity()).validateFragment();
            ((AuthActivity) getActivity()).validateAccItem.setEmail(regEmail.getText().toString().trim());
            Toast.makeText(getActivity(), data.getData().getMensaje_pin(), Toast.LENGTH_LONG);
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToastError(String error) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri;
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
                regPicture.setImageURI(resultUri);
                mCropImageUri = resultUri;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(RegisterFragment.class.getSimpleName(), error.toString());
            }
        }
    }

    @SuppressLint("NewApi")
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

    @OnClick({R.id.btn_day, R.id.btn_month, R.id.btn_year})
    public void setBirthday() {

        int year = 2017;
        int month = 0;
        int day = 1;

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                regDay.setText(String.valueOf(day));
                regMonth.setText(String.valueOf(month + 1));
                regYear.setText(String.valueOf(year));

            }
        }, year, month, day);
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    private void onBackPressed() {
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    ((AuthActivity) getActivity()).authFragment();
                    return true;
                }
                return false;
            }
        });
    }

}