package uk.co.surveyorforyou.surveyorforyou;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruwan on 17/02/2017.
 */

public class PropertiesAdapter extends ArrayAdapter{

    List list = new ArrayList();


    public PropertiesAdapter(PropertiesFragment context, int resource) {
        super(context.getContext(), resource);
    }

    @Override
    public void add(Object object) {
       super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        PropertiesHolder propertiesHolder;

        row = convertView;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.properties_list_layout,parent,false);
            propertiesHolder = new PropertiesHolder();
            propertiesHolder.refNo = (TextView) row.findViewById(R.id.refNo);
            propertiesHolder.name = (TextView) row.findViewById(R.id.clientName);
            propertiesHolder.phone = (TextView) row.findViewById(R.id.clientPhone);
            propertiesHolder.postcode = (TextView) row.findViewById(R.id.surveyPostcode);
            propertiesHolder.dateOrdered = (TextView) row.findViewById(R.id.dateOrdered);
            propertiesHolder.dueDate = (TextView) row.findViewById(R.id.dateDue);
            row.setTag(propertiesHolder);
        }else{

            propertiesHolder = (PropertiesHolder) row.getTag();
        }

        Properties properties = (Properties) this.getItem(position);
        propertiesHolder.refNo.setText(properties.getRefNo());
        propertiesHolder.name.setText(properties.getName());
        propertiesHolder.phone.setText(properties.getPhone());
        propertiesHolder.postcode.setText(properties.getPostcode());
        propertiesHolder.dateOrdered.setText(properties.getDateOrdered());
        propertiesHolder.dueDate.setText(properties.getDueDate());

        return row;
    }

    static class PropertiesHolder{

        TextView refNo,name,phone,postcode,dateOrdered,dueDate;
    }
}
