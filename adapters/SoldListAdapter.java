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
import mrhot.in.mrhotforbusiness.activities.models.Item;
import mrhot.in.mrhotforbusiness.activities.models.sold_item;
import mrhot.in.mrhotforbusiness.activities.utils.Product;

/**
 * Created by Jasbir_Singh on 16-06-2017.
 */

public class SoldListAdapter extends BaseAdapter {
    Context context;
    List<sold_item> data=new ArrayList<>();
    //ist<Product> products=new ArrayList<>();

    public SoldListAdapter(Context context, List<sold_item> data) {
        this.context = context;
        this.data = data;
        //this.products = products;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.render_product_list,null);
        final TextView item_name;
        TextView sold;
        Switch sold_avail;
        TextView avail;
        item_name=(TextView)view.findViewById(R.id.item_name);
        sold=(TextView)view.findViewById(R.id.sold);
        sold_avail=(Switch)view.findViewById(R.id.sold_avail);
        avail=(TextView)view.findViewById(R.id.avail);
        item_name.setText(data.get(position).getItemName());
        sold.setText(data.get(position).getSold());
        sold_avail.setText(data.get(position).getSold_avail());
        avail.setText(data.get(position).getAvail());
        sold_avail.setChecked(true);//Checked all the levers
        sold_avail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(context, "Checked", Toast.LENGTH_SHORT).show();
                }
                else {
                    String get_itemName =item_name.getText().toString().trim();
                    //Toast.makeText(context, ""+get_itemName, Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }
}
