package com.millonarios.MillonariosFC.ui.referredto.fragments.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.utils.Commons;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by RYA-Laptop on 05/01/2017.
 */

public class ReferredListAdapter extends ArrayAdapter<ReferredParser> {

    ArrayList<ReferredParser> referred;
    private Context context;

    public int inactive = 0;

    /*StatusInactiveListener statusInactiveListener;

    public void setStatusInactiveListener(StatusInactiveListener statusInactiveListener) {
        this.statusInactiveListener = statusInactiveListener;
    }*/

    public ReferredListAdapter(Context context, ArrayList<ReferredParser> referred) {
        super(context, R.layout.lv_referred_item, referred);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReferredParser referredParser = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View box = inflater.inflate(R.layout.lv_referred_item, null);

        assert referredParser != null;
        String pos = String.valueOf(position + 1);
        RelativeLayout contentReffered = box.findViewById(R.id.content_referred);
        TextView tvPosition = box.findViewById(R.id.tv_position);
        CircleImageView ivImage = box.findViewById(R.id.iv_image);
        TextView tvName = box.findViewById(R.id.tv_name);
        TextView tvApodo = box.findViewById(R.id.tv_points);

        tvPosition.setText(pos);
        if (!referredParser.getFoto().isEmpty()) {
            if (!referredParser.getFoto().equals("")) {
                Picasso.with(context)
                        .load(referredParser.getFoto())
                        .resize(45, 45)
                        .into(ivImage);
            } else {
                ivImage.setImageResource(R.drawable.silueta);
            }
        } else {
            ivImage.setImageResource(R.drawable.silueta);
        }

        if (referredParser.getStatus() != null) {
            if (!referredParser.getStatus().toLowerCase().equals("activo")) {
                tvName.setTextColor(Commons.getColor(R.color.textColorPrimary));
                tvPosition.setTextColor(Commons.getColor(R.color.textColorPrimary));
                contentReffered.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        inactiveDialog();
                    }
                });
                inactive++;
            }
        }

        String nombre = referredParser.getNombre() + " " + referredParser.getApellido();
        tvName.setText(nombre);
        tvApodo.setText(referredParser.getApodo());

        return box;
    }

    private void inactiveDialog() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialoglayout = inflater.inflate(R.layout.dialog_referred_inactive, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        TextView fcMillonariosTextView = dialoglayout.findViewById(R.id.text_description);
        fcMillonariosTextView.setText("Este amigo aún no está activo en la APP. Para que se pueda activar debe descargar la App e ingresar con la cuenta que se registró.");

        Button btnYes = dialoglayout.findViewById(R.id.aceptar);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

}