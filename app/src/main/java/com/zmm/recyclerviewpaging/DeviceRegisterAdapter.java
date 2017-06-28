package com.zmm.recyclerviewpaging;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/6/28
 * Time:下午1:49
 */

public class DeviceRegisterAdapter extends RecyclerView.Adapter<DeviceRegisterAdapter.DeviceRegisterHolder>   {

    private Context mContext;
    private List<String> deviceNames;
    private OnItemClickListener mOnItemClickListener;
    private int mLayoutPosition = -1;
    private boolean isLast = false;
    private int mItemCount = 3;


    public DeviceRegisterAdapter(Context context,List<String> deviceNames) {
        this.deviceNames = deviceNames;
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public DeviceRegisterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.item_device_register, parent, false);
        return new DeviceRegisterHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeviceRegisterHolder holder, final int position) {
        holder.setData(position);
        holder.mDeviceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                mLayoutPosition = holder.getLayoutPosition();
                if(mOnItemClickListener != null){
                    mOnItemClickListener.OnClickListener(position);
                }
            }
        });

        if(holder.getLayoutPosition() == mLayoutPosition){
            holder.mDeviceItem.setBackgroundResource(R.drawable.shape_rectangle_blue);
        }else {
            holder.mDeviceItem.setBackgroundResource(R.drawable.shape_rectangle);

        }
    }

    @Override
    public int getItemCount() {
//        return deviceNames.size();
        return isLast ? (deviceNames.size() % mItemCount) : mItemCount;
    }


    public class DeviceRegisterHolder extends RecyclerView.ViewHolder {

        private LinearLayout mDeviceItem;
        private TextView mDeviceId;

        public DeviceRegisterHolder(View itemView) {
            super(itemView);
            mDeviceId = itemView.findViewById(R.id.tv_device_id);
            mDeviceItem = itemView.findViewById(R.id.ll_device_item);

        }

        public void setData(int position) {
            itemView.setTag(position);

            if(deviceNames != null && deviceNames.size() > 0){
                for (int i = 0; i < deviceNames.size(); i++) {
                    mDeviceId.setText(deviceNames.get(i));
                }
            }

        }
    }

    public interface OnItemClickListener{
        void OnClickListener(int index);
    }


}
