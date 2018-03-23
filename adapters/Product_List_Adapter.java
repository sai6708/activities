package mrhot.in.mrhotforbusiness.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.AddItemActivity;
import mrhot.in.mrhotforbusiness.activities.ChangeMenuActivity;
import mrhot.in.mrhotforbusiness.activities.models.Item;
import mrhot.in.mrhotforbusiness.activities.models.sold_item;
import mrhot.in.mrhotforbusiness.activities.utils.Product;

/**
 * Created by Jasbir_Singh on 09-06-2017.
 */
/*public class Product_List_Adapter extends BaseAdapter {
    Context context;
    List<Item> data=new ArrayList<>();

    public Product_List_Adapter(Context context, List<Item> data) {
        this.context = context;
        this.data = data;
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
        View view=layoutInflater.inflate(R.layout.render_product_list,null);
       // TextView itemName,itemDesc,itemPrice;
       // ImageView edit;
        TextView item_name;
        TextView sold;
        Switch sold_avail;
        TextView avail;
        item_name=(TextView)view.findViewById(R.id.item_name);
        sold=(TextView)view.findViewById(R.id.sold);
        sold_avail=(Switch) view.findViewById(R.id.sold_avail);
        avail=(TextView)view.findViewById(R.id.avail);
        Product product = data.get(position);

        item_name.setText(data.get(position).getItemName());
        sold.setText();
        //itemName.setText(data.get(position).getItemName());
        //itemDesc.setText(data.get(position).getDescription());
        //itemPrice.setText("\u20B9 "+data.get(position).getItemPrice());
        //edit=(ImageView)view.findViewById(R.id.btnEdit);
       // edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, AddItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("position",position+"");
                bundle.putString("name",data.get(position).getItemName());
                bundle.putString("id",data.get(position).getItemId());
                bundle.putString("quantity",data.get(position).getQuantity());
                bundle.putString("desc",data.get(position).getDescription());
                bundle.putString("category",data.get(position).getCategory());
                bundle.putString("price",data.get(position).getItemPrice());
                bundle.putString("fastingitem",data.get(position).getFastingitem());
                bundle.putString("spicy",data.get(position).getSpicy());
                bundle.putString("jain",data.get(position).getJain());
                bundle.putString("day", ChangeMenuActivity.daySpinner.getSelectedItem().toString());
                if(ChangeMenuActivity.lunchRadio.isChecked())
                    bundle.putString("shift","Lunch");
                else
                    bundle.putString("shift","Dinner");
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
        return view;
    }
}*/

public class Product_List_Adapter extends BaseAdapter {
    private Context context;
    private List<sold_item> data = new ArrayList<>();
    private List<Product> product_data = new ArrayList<>();

    //Constructor

    public Product_List_Adapter(Context context, List<sold_item> data, List<Product> product_data) {
        this.context = context;
        this.data = data;
        this.product_data = product_data;
    }


    /*public Product_List_Adapter(Context context, List<sold_item> data,List<Product> product_data) {
        super();
        this.context = context;
        this.data = data;
        this.product_data = product_data;
    }
*/

 /*   @Override
    public void add(@Nullable Object object) {
        super.add(object);
        mProductList.add((Product)object);
    }*/



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
   /* @Override
    public int getCount() {
        return super.getCount();
        //return mProductList.size();//first Changed Here
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);//second Changed here
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return super.getPosition(item);
    }
*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //////////final RecyclerView.ViewHolder holder;

        View view = convertView;
        class Myview{
            TextView item_name;
            TextView sold;
            Switch sold_avail;
            TextView avail;

        }
        Myview myview;

        if(view==null){
            LayoutInflater layoutInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflator.inflate(R.layout.render_product_list,parent,false);
            myview=new Myview();
            myview.item_name = (TextView)view.findViewById(R.id.item_name);
            myview.sold = (TextView)view.findViewById(R.id.sold);
            myview.sold_avail = (Switch)view.findViewById(R.id.sold_avail);
            myview.avail = (TextView)view.findViewById(R.id.avail);

            view.setTag(myview);

        }else {
            myview=(Myview) view.getTag();
        }
        //sold_item product_pos = data.get(position);

     /*   View view = View.inflate(mContext, R.layout.render_product_list,null);//Third Change here
        TextView item_name = (TextView)view.findViewById(R.id.item_name);
        TextView sold = (TextView)view.findViewById(R.id.sold);
        TextView avail = (TextView)view.findViewById(R.id.avail);
        //Set Text For TextView Here
        item_name.setText(mProductList.get(position).getItem_name());
        sold.setText(mProductList.get(position).getSold());
        avail.setText(mProductList.get(position).getAvail());

        //Save Product Id To Tag

        view.setTag(mProductList.get(position).getId());
*/


        myview.item_name.setText(data.get(position).getItemName());
        myview.sold.setText(product_data.get(position).getSold());
        myview.sold_avail.setText(product_data.get(position).getSold_avail());
        myview.avail.setText(product_data.get(position).getAvail());
        // final View finalView = view;
        // final View finalView = view;
        myview.sold_avail.setChecked(false);
        myview.sold_avail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(context, "Switch Checked ", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

}

