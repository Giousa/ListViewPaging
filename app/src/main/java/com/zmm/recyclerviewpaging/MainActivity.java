package com.zmm.recyclerviewpaging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.btn_pre)
    Button mBtnPre;
    @InjectView(R.id.btn_next)
    Button mBtnNext;
    @InjectView(R.id.list_view)
    ListView mListView;


    private List<String> mDeviceNames;
    private int mSize;
    private int mItemCount = 3;
    private int mCurrentPage = 0;
    private MyAdapterOne mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        init();
    }

    private void init() {
        mDeviceNames = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            mDeviceNames.add("TWS01211313_" + i);
        }

        mSize = mDeviceNames.size();

        initRecyclerView();
    }

    private void initRecyclerView() {

        mAdapter = new MyAdapterOne();
        mListView.setAdapter(mAdapter);
    }

    @OnClick({R.id.btn_pre, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pre:
                turnToPrePage();
                break;
            case R.id.btn_next:
                turnToNextPage();
                break;
        }
    }


    boolean isFirstOne() {
        return this.mCurrentPage == 0;
    }

    boolean isLastOne() {
        return this.mCurrentPage == mSize/mItemCount;
    }


    public void turnToPrePage() {

        if (!isFirstOne()) {
            mCurrentPage--;
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "已经是第一页", Toast.LENGTH_SHORT).show();
        }
    }

    public void turnToNextPage() {

        if (!isLastOne()) {
            mCurrentPage++;
            mAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this, "已经是最后一页", Toast.LENGTH_SHORT).show();
        }
    }

    public class MyAdapterOne extends BaseAdapter {

        public MyAdapterOne() {
        }

        @Override
        public int getCount() {
            return (isLastOne()) ? (mDeviceNames.size() % mItemCount) : mItemCount;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {

                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_device_register, parent, false);
                holder = new ViewHolder();
                holder.mDeviceId = convertView.findViewById(R.id.tv_device_id);
                holder.mDeviceItem = convertView.findViewById(R.id.ll_device_item);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            final int currentPosition = position + mCurrentPage * mItemCount;
            if(currentPosition < mDeviceNames.size()){
                holder.mDeviceId.setText(mDeviceNames.get(currentPosition));
                holder.mDeviceItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("当前选中的position = "+currentPosition);
                    }
                });
            }

            return convertView;
        }


        class ViewHolder {
            LinearLayout mDeviceItem;
            TextView mDeviceId;
        }
    }
}
