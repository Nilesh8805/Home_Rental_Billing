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

public class ClintList extends ArrayAdapter<Database> {
    private Activity context;
    private List<Database> clintList;
    public ClintList(Activity context, List<Database> clintList){
        super(context, R.layout.clint_layout, clintList);
        this.context=context;
        this.clintList=clintList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.clint_layout,null,true);

        TextView textViewnumber= (TextView) listViewItem.findViewById(R.id.textviewno);
        TextView textViewname= (TextView) listViewItem.findViewById(R.id.textviewno2);

        Database database=clintList.get(position);
        textViewnumber.setText(database.getClintno());
        textViewname.setText(database.getName());

        return listViewItem;
    }
}
