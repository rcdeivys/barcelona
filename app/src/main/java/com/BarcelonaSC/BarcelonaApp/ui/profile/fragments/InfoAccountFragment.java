package com.BarcelonaSC.BarcelonaApp.ui.profile.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.profile.ProfileFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leonardojpr on 11/10/17.
 */

public class InfoAccountFragment extends Fragment {

    @BindView(R.id.register_name)
    EditText regName;
    @BindView(R.id.cedula)
    EditText regCi;
    @BindView(R.id.register_last)
    EditText regLastName;
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
    // @BindView(R.id.register_country)
    //  Spinner regSpCountry;
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
    @BindView(R.id.register_btn_register)
    Button btnRegister;
    @BindView(R.id.register_btn_exit)
    Button btnExit;
    @BindView(R.id.register_refer)
    EditText regRef;
    @BindView(R.id.linear_register_pass)
    LinearLayout linear_register_pass;

    @BindView(R.id.linear_register_cpass)
    LinearLayout linear_register_cpass;

    SessionManager sessionManager;
    private PreferenceManager preferenceManager;
    User user;
    boolean phone = false;
    boolean isSocial;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infoacc, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        sessionManager = new SessionManager(getActivity());
        preferenceManager = new PreferenceManager(getContext());
        isSocial = preferenceManager.getBoolean(Constant.Key.IS_SOCIAL, false);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataView(sessionManager.getUser());
                //((ProfileFragment) getParentFragment()).moveBack(InfoAccountFragment.this);
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
        regEmail.setEnabled(false);
        if (sessionManager.getUser().getFechaRegistro() != null) {
            setDataView(sessionManager.getUser());
        }

        isSocialConfig();
    }

    private void validateForm() {
        if (!regName.getText().toString().isEmpty()) {
            if (!regLastName.getText().toString().isEmpty()) {
                if (!regEmail.getText().toString().isEmpty()) {
                    if (regEmail.getText().toString().contains("@")) {
                        if (!regCi.getText().toString().isEmpty()) {
                            //if (!regPass.getText().toString().isEmpty()) {
                            //if (!regCpass.getText().toString().isEmpty()) {
                            //if (regPass.getText().toString().equals(regCpass.getText().toString())) {
//                            if (!regPhoneNum.getText().toString().isEmpty()) {
//                                if (!regPhoneCode.getText().toString().isEmpty()
//                                        && regPhoneCode.getText().toString().contains("+")) {
                                    /*if (!regDay.getText().toString().isEmpty()) {
                                        if (regRbMale.isChecked() || regRbFemale.isChecked()) {*/
                            updateUser();

                                        /*} else {
                                            Toast.makeText(getActivity(), "Debe indicar su género", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Debe seleccionar su fecha de cumpleaños", Toast.LENGTH_SHORT).show();
                                    }*/
//                                } else {
//                                    Toast.makeText(getActivity(), "El código del teléfono debe contener el símbolo + al comienzo", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                Toast.makeText(getActivity(), "El número telefónico no puede estar vacío", Toast.LENGTH_SHORT).show();
//                            }
                            //} else {
                            //    Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            //}
                            //} else {
                            //    Toast.makeText(getActivity(), "Debe confirmar la contraseña", Toast.LENGTH_SHORT).show();
                            //}
                            //} else {
                            //    Toast.makeText(getActivity(), "La contraseña no puede estar vacía", Toast.LENGTH_SHORT).show();
                            //}
                        } else {
                            Toast.makeText(getActivity(), "La cédula o pasaporte no puede estar vacía", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "El e-mail no es válido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "El e-mail no puede estar vacío", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "El apellido no puede estar vacío", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser() {
        user = new User();
        user.setNombre(regName.getText().toString());
        user.setApellido(regLastName.getText().toString());
        user.setEmail(regEmail.getText().toString());
        user.setCi(regCi.getText().toString());
        //if (!regPass.getText().toString().equals(PASSWORD))
        user.setClave(regPass.getText().toString());

        user.setCelular(regPhoneCode.getText().toString() + " " + regPhoneNum.getText().toString());
        //  user.setPais(regSpCountry.getSelectedItem().toString());
        if (regYear.getText().length() > 0)
            user.setFecha_nacimiento(regYear.getText().toString() + "/" + regMonth.getText().toString() + "/" + regDay.getText().toString());
        if (regRbMale.isChecked()) {
            user.setGenero(regRbMale.getText().toString());
        } else if (regRbFemale.isChecked()) {
            user.setGenero(regRbFemale.getText().toString());
        }

        ((ProfileFragment) getParentFragment()).presenter.updateUser(user, new SessionManager(getActivity()).getSession().getToken());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_day, R.id.btn_month, R.id.btn_year})
    public void setBirthday(View view) {
        String date;
        DateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy");
        date = dfDate.format(Calendar.getInstance().getTime());
        String parts[] = date.split("/");
        int year, month, day;

        if (!regYear.getText().toString().isEmpty() && !regMonth.getText().toString().isEmpty()
                && !regDay.getText().toString().isEmpty()) {
            year = Integer.parseInt(regYear.getText().toString());
            month = Integer.parseInt(regMonth.getText().toString()) - 1;
            day = Integer.parseInt(regDay.getText().toString());
        } else {
            year = 2017;
            month = 0;
            day = 1;
        }
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                Date date = calendar.getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat dfTwo = new SimpleDateFormat("dd/MM/yyyy");
                String newDate = df.format(date);
//                String newDateTwo = dfTwo.format(date);
                //user.setFecha_nacimiento(newDate);

                regDay.setText(String.valueOf(day));
                regMonth.setText(String.valueOf(month + 1));
                regYear.setText(String.valueOf(year));

            }
        }, year, month, day);
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    public void setDataView(UserItem user) {
        regName.setText(getString(user.getNombre()));
        regCi.setText(getString(user.getCedula()));
        regLastName.setText(getString(user.getApellido()));
        regEmail.setText(getString(user.getEmail()));
        regNick.setText(getString(user.getApodo()));
        if (user.getCelular() != null) {
            try {
                regPhoneCode.setText(codePhone(user.getCelular()));
                regPhoneNum.setText(numberPhone(user.getCelular()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //regPass.setText(PASSWORD);
        //regCpass.setText(PASSWORD);
        selectGender(getString(user.getGenero()));
        getBirthday(getString(user.getFechaNacimiento()));
        regRef.setText(getString(user.getReferido()));

    }

    private String getString(String object) {
        if (object != null)
            if (!object.equals(""))
                if (!object.equals("0000-00-00 00:00:00"))
                    return object;

        return "";
    }

    private String codePhone(String phone) {
        String[] code;
        code = phone.split(" ");
        return code[0];
    }

    private void selectGender(String gender) {
        if (gender.equals("Masculino")) {
            regRbMale.setChecked(true);
        } else if (gender.equals("Femenino")) {
            regRbFemale.setChecked(true);
        }
    }

    private String numberPhone(String phone) {
        String[] code;
        code = phone.split(" ");
        return code[1];
    }

    private void getBirthday(String date) {
        if (date.length() > 0) {
            regDay.setText(date.substring(8, 10));
            regMonth.setText(date.substring(5, 7));
            regYear.setText(date.substring(0, 4));
        }
    }

    public void isSocialConfig() {
        if (isSocial) {
            regEmail.setFocusable(false);
            linear_register_pass.setVisibility(View.GONE);
            linear_register_cpass.setVisibility(View.GONE);
        } else {
            regEmail.setFocusable(true);
            linear_register_pass.setVisibility(View.VISIBLE);
            linear_register_cpass.setVisibility(View.VISIBLE);
        }
    }

}