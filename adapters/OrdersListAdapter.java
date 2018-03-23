package mrhot.in.mrhotforbusiness.activities.adapters;
import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.OrderListHead;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static mrhot.in.mrhotforbusiness.R.color.green;

//For expandable list view use BaseExpandableListAdapter
public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.ViewHolder> {

    private static final String API_URL="http://mrhot.in/androidApp/dynamicOrdersjd.php";
    private List<OrderListHead> list ;
    private Context context;

    public OrdersListAdapter(List<OrderListHead> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_head,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        OrderListHead olh = list.get(position);

        final String orderid = olh.getId();
        String accepted = olh.getAccepted();


        holder.name.setText(olh.getName());
        holder.slot.setText("SLOT: " + olh.getSlot());
        holder.cod.setText("COD: \u20B9" + olh.getCod()+"/-");
        holder.itms.setText( olh.getItms());
        holder.cntct.setText(olh.getCntct());
        holder.Etv.setText("Address:- "+olh.getRoomNo()+":"+ olh.getAdrs()
                +"\n"+"Total Bill:- "+olh.getCost()
                +"\n"+"Order Id:- "+olh.getId()
                +"\n"+"Customer Id:- "+olh.getHotcode()
                +"\n"+"Order Time:- "+olh.getOrderTime());

        if(accepted.equals("1")){
            holder.main.setBackgroundResource(green);
            holder.mainCVHeader.setBackgroundResource(R.color.green);
        }else if(accepted.equals("-1")){
            holder.main.setBackgroundResource(R.color.red);
            holder.mainCVHeader.setBackgroundResource(R.color.lightred);
        }

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAccepted(orderid,"1");
                holder.main.setBackgroundResource(green);
                holder.mainCVHeader.setBackgroundResource(R.color.green);
            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAccepted(orderid,"-1");
                holder.main.setBackgroundResource(R.color.red);
                holder.mainCVHeader.setBackgroundResource(R.color.lightred);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView name;
        TextView slot;
        TextView cod;
        TextView itms;
        TextView cntct;
        ExpandableTextView Etv;
        LinearLayout main;
        CardView mainCVHeader;

        TextView accept;
        TextView cancel;

        public ViewHolder(View itemView) {
            super(itemView);

             name = (TextView) itemView.findViewById(R.id.nameTV);
             slot = (TextView) itemView.findViewById(R.id.slotTV);
             cod = (TextView) itemView.findViewById(R.id.codTV);
             itms = (TextView) itemView.findViewById(R.id.itmTV);
             cntct = (TextView) itemView.findViewById(R.id.cntctTV);
            Etv = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);

            accept = (TextView) itemView.findViewById(R.id.acceptBtn);
            cancel = (TextView) itemView.findViewById(R.id.rejectBtn);
            main=(LinearLayout) itemView.findViewById(R.id.mainHeader);
            mainCVHeader= (CardView) itemView.findViewById(R.id.mainCVHeader);
        }
    }

    public void setAccepted(final String orderid,final String accepted){

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/setAcceptOrder.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray dataArray=jsonObject.optJSONArray("data");

//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(context,"Item set.",Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"Something went wrong while accepting this item.",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("orderid",orderid);
                map.put("accepted",accepted);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

   }