package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.models.SuscripcionData;
import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.di.DaggerHinchaDoradoComponent;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.di.HinchaDoradoModule;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.mvp.HinchaDoradoContract;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.mvp.HinchaDoradoPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.paymentwebviews.GooglePlayPaymentActivity;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.paymentwebviews.PayUPaymentActivity;
import com.BarcelonaSC.BarcelonaApp.ui.profile.fragments.AcercaDeDialog;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Cesar on 19/02/2018.
 */

public class HinchaDoradoRegister extends BaseActivity implements HinchaDoradoContract.View, PayUPaymentActivity.OnItemClickListener {

    @BindView(R.id.back_button)
    FrameLayout back_button;

    @BindView(R.id.btn_pay_with_google)
    Button payGoogle;

    @BindView(R.id.btn_pay_with_payu)
    Button payPayU;

    @BindView(R.id.register_name)
    EditText register_name;

    @BindView(R.id.register_last)
    EditText register_last;

    @BindView(R.id.cedula)
    EditText cedula;

    @BindView(R.id.register_email)
    EditText register_email;

    @BindView(R.id.register_phone_code)
    EditText register_phone_code;

    @BindView(R.id.register_phone_num)
    EditText register_phone_num;

    @BindView(R.id.register_rb_male)
    RadioButton register_rb_male;

    @BindView(R.id.register_rb_female)
    RadioButton register_rb_female;

    @BindView(R.id.btn_day)
    EditText btn_day;

    @BindView(R.id.btn_month)
    EditText btn_month;

    @BindView(R.id.btn_year)
    EditText btn_year;

    @BindView(R.id.register_ciudad)
    EditText register_ciudad;

    @BindView(R.id.register_direccion)
    EditText register_direccion;

    @BindView(R.id.socio_rb_si)
    RadioButton socio_rb_si;

    @BindView(R.id.socio_rb_no)
    RadioButton socio_rb_no;

    @BindView(R.id.abonado_rb_si)
    RadioButton abonado_rb_si;

    @BindView(R.id.abonado_rb_no)
    RadioButton abonado_rb_no;

    @BindView(R.id.tv_footer)
    FCMillonariosTextView tv_footer;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.cb_terminos_condiciones)
    CheckBox cbTerminosCondiciones;

    EditText[] editTextArray;
    private boolean usarButton = true;
    String valueSpinner = "";

    @Inject
    public HinchaDoradoPresenter presenter;
    Unbinder unbinder;
    List<SuscripcionData> suscripcionDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscribir_hincha_dorado);
        unbinder = ButterKnife.bind(this);
        addEditTextToArray();
        settingValues();
        initComponent();
        presenter.onAttach(this);
        Log.i(TAG, "--->" + SessionManager.getInstance().getUser().getId_usuario());
        refresh();

    }

    public void initComponent() {
        DaggerHinchaDoradoComponent.builder()
                .appComponent(App.get().component())
                .hinchaDoradoModule(new HinchaDoradoModule(this))
                .build().inject(HinchaDoradoRegister.this);
    }

    private void addEditTextToArray() {
        editTextArray = new EditText[]{
                register_name,
                register_last,
                cedula,
                register_email,
                register_phone_code,
                register_phone_num,
                btn_day,
                btn_month,
                btn_year,
                register_ciudad,
                register_direccion
        };
    }

    private void settingValues() {
        register_name.setText(SessionManager.getInstance().getUser().getNombre());
        register_last.setText(SessionManager.getInstance().getUser().getApellido());
        cedula.setText(SessionManager.getInstance().getUser().getCi());
        register_email.setText(SessionManager.getInstance().getUser().getEmail() != null ? SessionManager.getInstance().getUser().getEmail() : "");

        tv_footer.setText(ConfigurationManager.getInstance().getConfiguration().getFooterFormularioDorados() != null ? ConfigurationManager.getInstance().getConfiguration().getFooterFormularioDorados() : "");

        SettingPhoneNumber();
        SettingGender();
        SettingBirthday();

        socio_rb_no.setChecked(true);
        abonado_rb_no.setChecked(true);
    }

    private void SettingBirthday() {
        if (SessionManager.getInstance().getUser().getFechaNacimiento() != null && !SessionManager.getInstance().getUser().getFechaNacimiento().isEmpty()) {
            String date = SessionManager.getInstance().getUser().getFechaNacimiento();
            if (date.length() > 0) {
                btn_day.setText(date.substring(8, 10));
                btn_month.setText(date.substring(5, 7));
                btn_year.setText(date.substring(0, 4));
            }
        }
    }

    private void SettingGender() {
        if (SessionManager.getInstance().getUser().getGenero() == null || SessionManager.getInstance().getUser().getGenero().isEmpty()) {
            register_rb_male.setChecked(true);
        } else {
            selectGender(SessionManager.getInstance().getUser().getGenero());
        }
    }

    private void SettingPhoneNumber() {
        if (SessionManager.getInstance().getUser().getCelular() == null || SessionManager.getInstance().getUser().getCelular().isEmpty()) {
            register_phone_code.setText("");
            register_phone_num.setText("");
        } else {
            String celular = SessionManager.getInstance().getUser().getCelular();
            try {
                register_phone_code.setText(codePhone(celular));
                register_phone_num.setText(numberPhone(celular));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String codePhone(String phone) {
        String[] code;
        code = phone.split(" ");
        return code[0];
    }

    private void selectGender(String gender) {
        if (gender.equals("Masculino")) {
            register_rb_male.setChecked(true);
        } else if (gender.equals("Femenino")) {
            register_rb_female.setChecked(true);
        }
    }

    private String numberPhone(String phone) {
        String[] code;
        code = phone.split(" ");
        return code[1];
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
        unbinder.unbind();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick(R.id.btn_pay_with_google)
    void goToPayWithGoogle() {
        Intent intent = new Intent(this, GooglePlayPaymentActivity.class);
        intent.putExtra(Constant.Key.TITLE, getString(R.string.with_google_play));
        startActivity(intent);
    }

    @OnClick(R.id.tv_terminos_condiciones)
    void showTerminos() {
        AcercaDeDialog acercaDeDialog = new AcercaDeDialog();
        acercaDeDialog.show(getActivity().getSupportFragmentManager(), "acercade");
    }

    @OnClick(R.id.btn_pay_with_payu)
    void goToPayWithPayu() {
        presenter.updateUser(updateUser(), new SessionManager(getActivity()).getSession().getToken());
        validatedFieldsToBuy();
    }

    public User updateUser() {

        UserItem userItem = SessionManager.getInstance().getUser();

        User user = new User();

        user.setNombre(register_name.getText().toString());
        user.setApellido(register_last.getText().toString());
        user.setEmail(register_email.getText().toString());

        user.setCelular(register_phone_code.getText().toString() + " " + register_phone_num.getText().toString());
        user.setCi(cedula.getText().toString().trim());
        //  user.setPais(regSpCountry.getSelectedItem().toString());
        if (btn_year.getText().length() > 0)
            user.setFecha_nacimiento(btn_year.getText().toString() + "/" + btn_month.getText().toString() + "/" + btn_day.getText().toString());
        if (register_rb_male.isChecked()) {
            user.setGenero(register_rb_male.getText().toString());
        } else if (register_rb_female.isChecked()) {
            user.setGenero(register_rb_female.getText().toString());
        }

        user.setDireccion(register_direccion.getText().toString());
        user.setCiudad(register_ciudad.getText().toString());


        if (abonado_rb_si.isChecked()) {
            user.setAbonado(abonado_rb_si.getText().toString());
        } else if (abonado_rb_no.isChecked()) {
            user.setAbonado(abonado_rb_no.getText().toString());
        }
        if (socio_rb_si.isChecked()) {
            user.setSocio(socio_rb_si.getText().toString());
        } else if (socio_rb_no.isChecked()) {
            user.setSocio(socio_rb_no.getText().toString());
        }
        return user;
    }

    @OnClick(R.id.back_button)
    void onClick_back_button() {
        this.finish();
    }

    private void validatedFieldsToBuy() {
        if (usarButton) {
            usarButton = false;
            Log.i(TAG, "--->validatedFieldsToBuy()");

            // Reset errors.
            for (int i = 0; i < editTextArray.length; i++) {
                editTextArray[i].setError(null);
            }
            //spinner.setError(null);

            boolean cancel = false;
            View focusView = null;

            //validates edittexts
            for (int i = 0; i < editTextArray.length; i++) {
                if (TextUtils.isEmpty(editTextArray[i].getText().toString())) {
                    editTextArray[i].setError(getString(R.string.error_field_required));
                    focusView = editTextArray[i];
                    cancel = true;
                    break;
                }
            }

            //validates phone code
            if (!(register_phone_code.getText().length() > 1)) {
                register_phone_code.setError(getString(R.string.error_field_required));
                focusView = editTextArray[4];
                cancel = true;
            }

            //validates birthday
            if (!(btn_year.getText().length() > 0) && !(btn_month.getText().length() > 0) && !(btn_day.getText().length() > 0)
                    || (btn_day.getText().toString().equals("00")) && (btn_month.getText().toString().equals("00"))
                    && (btn_year.getText().toString().equals("0000"))) {
                btn_day.setError(getString(R.string.error_field_required));
                focusView = btn_day;
                cancel = true;
            }

            //validate spinner
            if (valueSpinner.isEmpty() || valueSpinner.equals(Commons.getString(R.string.spinner_default_option))) {
                //spinner.setError(getString(R.string.error_spinner_required));
                Toast.makeText(this, getString(R.string.error_spinner_required), Toast.LENGTH_SHORT).show();
                focusView = spinner;
                cancel = true;
            }

            if (!cbTerminosCondiciones.isChecked()) {
                Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
                cbTerminosCondiciones.setError("");
                focusView = cbTerminosCondiciones;
                cancel = true;
            }

            if (cancel) {
                focusView.requestFocus();
            } else {
                Commons.hideKeyboard(this);

                showDialogConfirmPayu("Nombre: ".concat(register_name.getText().toString()).concat("\n")
                        .concat("Apellido: ").concat(register_last.getText().toString()).concat("\n")
                        .concat(App.get().getString(R.string.email) + ": ").concat(register_email.getText().toString()).concat("\n")
                        .concat("Cédula / Pasaporte: ").concat(cedula.getText().toString()).concat("\n")
                        .concat("Celular: ").concat(register_phone_code.getText().toString()).concat("-").concat(register_phone_num.getText().toString()).concat("\n")
                        .concat("Género: ").concat(getGenderSelected()).concat("\n")
                        .concat("Fecha de nacimiento: ").concat(btn_day.getText().toString().concat("-" + btn_month.getText().toString().concat("-" + btn_year.getText().toString()))).concat("\n")
                        .concat("Ciudad: ").concat(register_ciudad.getText().toString()).concat("\n")
                        .concat("Dirección: ").concat(register_direccion.getText().toString())
                );
            }
            usarButton = true;
        }
    }

    private void goPayu() {
        PayUPaymentActivity payUPaymentActivity = new PayUPaymentActivity();
        payUPaymentActivity.setParams(this, getSuscripcionDataFromSpinner(valueSpinner));
        showDialogFragment(payUPaymentActivity);
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onClickWebView(String TAG) {
        switch (TAG) {
            case HinchaDoradoConsts.TAG_Close_WebView: {
                presenter.getStatusSuscription();
                break;
            }
        }
    }

    public SuscripcionData getSuscripcionDataFromSpinner(String plan) {
        for (int i = 0; i < suscripcionDataList.size(); i++) {
            if (plan.equals(suscripcionDataList.get(i).getDescripcion())) {
                return suscripcionDataList.get(i);
            }
        }
        return null;
    }

    @Override
    public void updateSuscripcion(List<SuscripcionData> suscripcionData) {
        List<String> list = new ArrayList<>();
        this.suscripcionDataList = suscripcionData;
        list.add(Commons.getString(R.string.spinner_default_option));
        for (SuscripcionData suscripcionData1 : suscripcionData) {
            Log.i(TAG, " ---> Lista suscripcion data " + suscripcionData1.getDescripcion());
            list.add(suscripcionData1.getDescripcion());
        }

        ArrayAdapter dataAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valueSpinner = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                valueSpinner = item;
//            }
//        });
    }

    @Override
    public void updateStatusSuscripcion(String status) {
        Log.i(TAG, "--->updateStatusSuscripcion " + status);
        switch (status) {
            case HinchaDoradoConsts.TAG_STATUS_exito: {
                initActivityStatus(HinchaDoradoConsts.TAG_STATUS_exito);
                break;
            }
            case HinchaDoradoConsts.TAG_STATUS_fallo: {
                initActivityStatus(HinchaDoradoConsts.TAG_STATUS_fallo);
                break;
            }
            case HinchaDoradoConsts.TAG_STATUS_pendiente: {
                initActivityStatus(HinchaDoradoConsts.TAG_STATUS_pendiente);
                break;
            }
            case HinchaDoradoConsts.TAG_STATUS_no_existe: {

                break;
            }
        }
    }

    public void initActivityStatus(String TAG) {
        Intent intent = new Intent(this, HinchaDoradoStatusActivity.class);
        intent.putExtra(Constant.Key.DORADO_STATUS_VIEW, TAG);
        intent.putExtra(Constant.Key.DORADO_STATUS_SUSCRIPCION, getSuscripcionDataFromSpinner(valueSpinner));
        startActivity(intent);
    }

    @Override
    public void onFailedStatusSuscripcion() {
        Toast.makeText(this, "Error status suscripción", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedUpdate(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refresh() {
        presenter.loadSuscripcion();
    }

    @OnClick({R.id.btn_day, R.id.btn_month, R.id.btn_year})
    public void setBirthday(View view) {
        String date;
        DateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy");
        date = dfDate.format(Calendar.getInstance().getTime());
        String parts[] = date.split("/");
        int year, month, day;

        if (!btn_year.getText().toString().isEmpty() && !btn_month.getText().toString().isEmpty()
                && !btn_day.getText().toString().isEmpty()) {
            year = Integer.parseInt(btn_year.getText().toString());
            month = Integer.parseInt(btn_month.getText().toString()) - 1;
            day = Integer.parseInt(btn_day.getText().toString());
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

                btn_day.setText(String.valueOf(day));
                btn_month.setText(String.valueOf(month + 1));
                btn_year.setText(String.valueOf(year));

            }
        }, year, month, day);
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    public void showDialogConfirmPayu(String message) {

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_to_suscription, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        FCMillonariosTextView fcMillonariosTextView = (FCMillonariosTextView) dialoglayout.findViewById(R.id.fcm_tv_data_user);
        fcMillonariosTextView.setText(message);
        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_not);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        Button btnYes = (Button) dialoglayout.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPayu();
                alertDialog.dismiss();
            }
        });

    }

    public String getGenderSelected() {
        if (register_rb_male.isChecked())
            return "Masculino";
        else
            return "Femenino";
    }
}
