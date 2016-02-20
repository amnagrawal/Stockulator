package com.aman.stockulator;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aman on 26/12/15.
 */
public class ListEntriesAdapter extends RecyclerView.Adapter<ListEntriesAdapter.ListItemViewHolder> {

    private List<ListItemInfo> itemInfoList;

    public ListEntriesAdapter(List<ListItemInfo> itemInfoList){
        this.itemInfoList = itemInfoList;
    }


    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item, parent, false);

        ListItemViewHolder holder = new ListItemViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        ListItemInfo listItemInfo = itemInfoList.get(position);
        holder.vName.setText(listItemInfo.getCompanyName());
    }

    @Override
    public int getItemCount() {
        return itemInfoList.size();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView vName;
        protected RelativeLayout relativeLayout;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            vName = (TextView) itemView.findViewById(R.id.list_item_name);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
            relativeLayout.setOnClickListener(this);
            itemView.setClickable(true);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
            ListItemInfo listItemInfo = itemInfoList.get(itemPosition);
            Log.i("item content: ", listItemInfo.getCompanyName());

            Intent intent = new Intent(v.getContext(), Company.class);
            intent.putExtra("Name", vName.getText());
            v.getContext().startActivity(intent);
        }
    }
}
