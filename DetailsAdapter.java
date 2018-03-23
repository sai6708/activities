package mrhot.in.mrhotforbusiness.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mrhot.in.mrhotforbusiness.R;

/**
 * Created by prabhat on 19-09-2017.
 */

public class DetailsAdapter extends ArrayAdapter {

    List list=new ArrayList();
    Context context,cx;
    static int pos;
    View view;
    EditDeliveryTimings ed=new EditDeliveryTimings();
    EditDeliveryTimings.Editdetails editdetails;
    static String p;
    String editslot;


    public DetailsAdapter( Context context,  int resource) {
        super(context, resource);
        this.context=context;//IMPPPPPPPPPPPPPPPPPPPP
        this.cx=context;
    }

    public void add(EditDeliveryTimings.Editdetails object){
        super.add(object);
        list.add(object);

    }

    public String period(){

        p=editdetails.getPeriod();
        return p;
    }



    public int getCount(){
        return list.size();
    }


    public Object getItem(int position){
        return list.get(position);
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        ContactHolder ch;
        View row;
        row=convertView;
        if(row==null){

            LayoutInflater lf=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=lf.inflate(R.layout.editorders,parent,false);
            ch=new ContactHolder();
            pos=position;
            ch.name=(TextView)row.findViewById(R.id.deliveryslots);
            ch.time2=(TextView)row.findViewById(R.id.takeorderstill);
            ch.active=(TextView) row.findViewById(R.id.textView16);
            row.setTag(ch);

        }
        else{
            ch=(ContactHolder)row.getTag();
            pos=position;
        }
        EditDeliveryTimings.Editdetails c=(EditDeliveryTimings.Editdetails) this.getItem(position);
        c.setPeriod(c.getPeriod());
        ch.name.setText(c.getName());
        if(c.getTime2().length()>1) {
            //String time=c.getTime2().substring(0, 2) + ":" + c.getTime2().substring(2);
            String time=c.getTime2();

            try {
                final SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
                final Date dateObj;
                dateObj = sdf.parse(time);
                ch.time2.setText(new SimpleDateFormat("hh:mm a").format(dateObj));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else
           ch.time2.setText(c.getTime2());

        if(c.getActive()==0)
        {
            ch.active.setTextColor(Color.parseColor("#FF424242"));
            ch.active.setText("Deactive");
        }
        else
        {
            ch.active.setText("Active");
            ch.active.setTextColor(Color.parseColor("#006400"));
        }


        return row;
    }


    static class ContactHolder{
        TextView name,time2, active;

    }

}
