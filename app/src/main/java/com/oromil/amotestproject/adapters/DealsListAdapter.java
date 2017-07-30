package com.oromil.amotestproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.oromil.amotestproject.R;
import com.oromil.amotestproject.utils.TimeUtil;
import com.oromil.amotestproject.data.model.Lead;
import com.oromil.amotestproject.data.model.LeadsStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oromil on 27.07.2017.
 */

public class DealsListAdapter extends RecyclerView.Adapter<DealsListAdapter.ViewHolder> {

    private ArrayList<Lead> mDealsList;
    private ArrayList<LeadsStatus>mDealsStatusList;

    public DealsListAdapter(){
        mDealsList = new ArrayList<>();
        mDealsStatusList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deals_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        for (LeadsStatus status:mDealsStatusList) {
            if (mDealsList.get(position).getStatusId().equals(status.getId())){
                holder.tvDealStatus.setText(status.getName());
                holder.statusLayout.setBackgroundColor(status.getColor().hashCode());
            }
        }

        holder.tvDealName.setText(mDealsList.get(position).getName());

        if (mDealsList.get(position).getPrice().equals(""))
            holder.tvDealPrice.setText("0");
        else holder.tvDealPrice.setText(mDealsList.get(position).getPrice());

        int timestamp = mDealsList.get(position).getDateCreate();
        holder.tvDealDate.setText(TimeUtil.getDateCurrentTimeZone(timestamp).toString());
    }

    @Override
    public int getItemCount() {
        return mDealsList.size();
    }

    public void setDealsList(List<Lead>dealsList){
        this.mDealsList = (ArrayList<Lead>) dealsList;
    }
    public void setDealsStatusList(List<LeadsStatus>dealsStatusList){
        this.mDealsStatusList = (ArrayList<LeadsStatus>) dealsStatusList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDealName;
        TextView tvDealPrice;
        TextView tvDealDate;
        TextView tvDealStatus;
        FrameLayout statusLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            statusLayout = (FrameLayout) itemView.findViewById(R.id.statusLayout);
            tvDealStatus = (TextView) itemView.findViewById(R.id.tvDealStatus);
            tvDealDate = (TextView) itemView.findViewById(R.id.tvDealDate);
            tvDealName = (TextView) itemView.findViewById(R.id.tvDealName);
            tvDealPrice = (TextView) itemView.findViewById(R.id.tvDealPrice);
        }
    }


}
