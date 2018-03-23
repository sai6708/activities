package mrhot.in.mrhotforbusiness.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.models.sold_data;
import mrhot.in.mrhotforbusiness.activities.utils.Product;

import static mrhot.in.mrhotforbusiness.activities.SoldDataPage.products;

/**
 * Created by Jasbir_Singh on 18-06-2017.
 */

public class SoldDataAdapter extends BaseAdapter {
    Context context;
    List<sold_data> data = new ArrayList<>();
    String avail,shift;
    String item;

    //Constructor

    public SoldDataAdapter(Context context, List<sold_data> data) {
        this.context = context;
        this.data = data;
        //this.shift=shift;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.sold_data_page_custom,null);

        TextView solditem;
        Switch lever;

        solditem=(TextView)view.findViewById(R.id.itemname);
        lever=(Switch)view.findViewById(R.id.switch2);
        System.out.println(data.get(position).getItem()+"   and "+data.get(position).getsuccess());
        solditem.setText(data.get(position).getItemname());
        int leverflag=data.get(position).getsuccess();
        if(leverflag==1)
        {
            lever.setChecked(true);
        }
        else
        {
            lever.setChecked(false);
        }

        lever.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && data.get(position).getsuccess()==0)
                {
                    avail="1";
                    item=""+data.get(position).getItem();
                    Product soldout= new Product(item,data.get(position).getItemname(),"","",avail);
                    products.add(soldout);
                    data.get(position).setSuccess(1);
                }
                else if(!isChecked && data.get(position).getsuccess()==1)
                {
                    avail="0";
                    item=""+data.get(position).getItem();
                    Product soldout= new Product(item,data.get(position).getItemname(),"","",avail);
                    products.add(soldout);
                    data.get(position).setSuccess(0);
                }
                else{

                }
            }
        });
        return view;
    }


}
