package mrhot.in.mrhotforbusiness.activities.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.models.Item;

/**
 * Created by Anuran on 3/24/2017.
 */

public class AutoCompleteItemAdapter extends BaseAdapter implements Filterable {
    Item[] items;
    Context context;
    List<Item> itemList;
    List<Item> suggestions;
    //int resID;

    /*public AutoCompleteItemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Item[] items) {
        super(context, resource, items);
        this.items=items;
        this.context=context;
        this.resID=resource;
    }*/

    public AutoCompleteItemAdapter(Item[] items, Context context) {
        this.items = items;
        this.context = context;
        this.itemList= new ArrayList<Item>(Arrays.asList(items));
        this.suggestions=new ArrayList<Item>();
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.autocomplete_single_row,parent,false);
        TextView itemName,itemPrice,itemCategory;
        itemName=(TextView)view.findViewById(R.id.itemName);
        itemPrice=(TextView)view.findViewById(R.id.itemPrice);
        itemCategory=(TextView)view.findViewById(R.id.itemCategory);
        itemName.setText(items[position].getItemName());
        itemPrice.setText("\u20B9 "+items[position].getItemPrice());
        itemCategory.setText(items[position].getCategory());
        return view;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter(){

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Item)resultValue).getItemName().toString();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            if(constraint!=null){
                suggestions.clear();
                for(Item item:itemList){
                    if(item.getItemName().toLowerCase().contains(constraint.toString().toLowerCase()))
                        suggestions.add(item);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;

            }else{
                return null;
            }

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results != null && results.count > 0) {
                notifyDataSetChanged();
            }
        }
    };
}
