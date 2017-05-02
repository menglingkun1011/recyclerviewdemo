package com.meng.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView re;

    private List<String> datas = new ArrayList<>();
    private MainActivity mContext;
    private Button btn;
    private FrameLayout fl;
    private boolean isShow = true;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        for (int i = 0; i < 50; i++) {
            datas.add("我是"+i+"个条目");
        }

        re = (RecyclerView) findViewById(R.id.recylerview);
        re.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        //添加分割线
        re.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.HORIZONTAL));
        adapter = new MyAdapter(datas);
        re.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String bean) {
                Toast.makeText(mContext, "position:"+bean, Toast.LENGTH_SHORT).show();
            }
        });

        btn = (Button) findViewById(R.id.btn);
        fl = (FrameLayout) findViewById(R.id.fl);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHide();
            }
        });
    }

    private void showOrHide() {
        if(isShow){
            btn.setText("隐藏");
            fl.setVisibility(View.VISIBLE);
        }else{
            btn.setText("显示");
            fl.setVisibility(View.GONE);
        }
        this.isShow = !isShow;
    }

    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{

        private final List<String> data;
        private OnItemClickListener mOnItemClickListener;

        public MyAdapter(List<String> data){
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v =  View.inflate(parent.getContext(), R.layout.item_layout,null);
            ViewHolder vh = new ViewHolder(v);
            v.setOnClickListener(this);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(data.get(position));
            holder.tv.setGravity(Gravity.CENTER);
            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(v, (String) v.getTag());
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            public final TextView tv;
            public ViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv);
            }
        }

        //定义接口
        public interface OnItemClickListener{
            /**
             *
             * @author Administrator
             * @time 2017/5/2,17:46
             * bean 是 实体对象
             **/
            void onItemClick(View view,String bean);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            mOnItemClickListener = listener;
        }

    }


}
