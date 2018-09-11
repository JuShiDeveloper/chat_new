package com.jushi.muisc.chat.music.home_page.recommend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.home_page.recommend.model.TodayRecommendModel;
import com.jushi.muisc.chat.common.transform.CornersTransform;
import com.jushi.muisc.chat.common.utils.Utils;
import com.jushi.muisc.chat.common.view.JSTextView;

import java.util.List;

/**
 * 首页中的今日推荐
 */

public class TodayRecommendAdapter extends RecyclerView.Adapter<TodayRecommendAdapter.ViewHolder> {
    private Context mContext;
    private List<TodayRecommendModel.ResultBean.ListBean> listBeans;

    public TodayRecommendAdapter(Context mContext, List<TodayRecommendModel.ResultBean.ListBean> listBeans) {
        this.mContext = mContext;
        this.listBeans = listBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_vew_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        TodayRecommendModel.ResultBean.ListBean listBean = listBeans.get(position);
        Glide.with(mContext).load(listBean.getPic_premium()).transform(new CornersTransform(mContext, 30)).crossFade().into(holder.imageView);
        holder.textView.setText(listBean.getTitle());
        if (listener != null){
            final TodayRecommendModel.ResultBean.ListBean bean = listBean;
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(bean,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(TodayRecommendModel.ResultBean.ListBean listBean, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ImageView imageView;
        private JSTextView textView;
        private LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.iv_recyclerView_item);
            textView = itemView.findViewById(R.id.jsTv_recyclerView_item);
            layout = itemView.findViewById(R.id.Recycler_item_layout);
            Utils.setRecommendImageLayoutParams(mContext, layout);
        }
    }
}
