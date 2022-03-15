package com.homerents.home_rent_billing;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Clintbilllist extends ArrayAdapter<Database> {
    private Activity context;
    private List<Database> clintbilllist;
    public Clintbilllist(Activity context, List<Database> dataListlist) {
        super(context, R.layout.bill_layout, dataListlist);
        this.context = context;
        this.clintbilllist = dataListlist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.bill_layout,null,true);


        TextView textDate= (TextView) listViewItem.findViewById(R.id.vdate);
        TextView textNew= (TextView) listViewItem.findViewById(R.id.vnewreading);
        TextView textOld= (TextView) listViewItem.findViewById(R.id.voldreading);
        TextView textDiff= (TextView) listViewItem.findViewById(R.id.vunitsdiff);
        TextView textRate= (TextView) listViewItem.findViewById(R.id.vrate);
        TextView textRent= (TextView) listViewItem.findViewById(R.id.vrent);
        TextView textPaid1= (TextView) listViewItem.findViewById(R.id.vpd1);
        TextView textPaid2= (TextView) listViewItem.findViewById(R.id.vpd2);
        TextView textUnpaid= (TextView) listViewItem.findViewById(R.id.vunpaid);
        TextView textGtot= (TextView) listViewItem.findViewById(R.id.vgrandtotal);
        TextView textec= (TextView) listViewItem.findViewById(R.id.vec);

        Database database=clintbilllist.get(position);
        String unit=database.getDiff();
        String rate=database.getRate();
        int unitint= Integer.parseInt(unit);
        Float rateint= Float.parseFloat(rate);
        Float ec=unitint*rateint;
        textec.setText(""+ec);
        textDate.setText("Date :- "+database.getDate());
        textNew.setText(database.getNewreading());
        textOld.setText("-  "+database.getOldreading());
        textDiff.setText(database.getDiff());
        textRate.setText("*  "+database.getRate());
        textRent.setText("+  "+database.getRent());
        textPaid1.setText("-  "+database.getPaid1());
        textPaid2.setText("-  "+database.getPaid2());
        textUnpaid.setText("+  "+database.getUnpaid());
        textGtot.setText("Rs. "+database.getGrandtotal());


        return listViewItem;
    }
}

