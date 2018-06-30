package com.jushi.muisc.chat.music.recommend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.recommend.model.TodayRecommendModel;
import com.jushi.muisc.chat.view.JSTextView;

import java.util.List;

/**
 * 所有的今日推荐
 */

public class AllRecommendAdapter extends RecyclerView.Adapter<AllRecommendAdapter.ViewHolder> {

    private Context context;
    private List<TodayRecommendModel.ResultBean.ListBean> listBeans;
    private int currentPosition = -1;

    public AllRecommendAdapter(Context context, List<TodayRecommendModel.ResultBean.ListBean> listBeans) {
        this.context = context;
        this.listBeans = listBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_recommend_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TodayRecommendModel.ResultBean.ListBean listBean = listBeans.get(position);
        holder.tvNumber.setText(String.valueOf(position + 1));
        holder.tvSongName.setText(listBean.getTitle());
        holder.tvSinger.setText(listBean.getAuthor());
        if (currentPosition == position) {
            if (holder.volume.getVisibility() == View.GONE) {
                holder.volume.setVisibility(View.VISIBLE);
                holder.tvNumber.setVisibility(View.GONE);
            }
        } else {
            holder.volume.setVisibility(View.GONE);
            holder.tvNumber.setVisibility(View.VISIBLE);
        }
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
        setMoreBtnClickListener(holder);
    }

    private void setMoreBtnClickListener(ViewHolder holder) {
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setDateChanged(int position) {
        this.currentPosition = position;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(TodayRecommendModel.ResultBean.ListBean listBean, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private JSTextView tvSongName, tvSinger, tvNumber;
        private ImageView volume;
        private LinearLayout moreBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvNumber = itemView.findViewById(R.id.tv_all_recommend_number_tv);
            tvSongName = itemView.findViewById(R.id.all_recommend_item_songName_tv);
            tvSinger = itemView.findViewById(R.id.all_recommend_singer_tv);
            volume = itemView.findViewById(R.id.iv_volume);
            moreBtn = itemView.findViewById(R.id.all_recommend_item_moreBtn);
        }
    }
}
