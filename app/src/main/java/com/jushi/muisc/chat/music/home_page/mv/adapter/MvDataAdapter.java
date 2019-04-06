package com.jushi.muisc.chat.music.home_page.mv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.home_page.mv.model.MVBean;
import com.jushi.muisc.chat.common.utils.Utils;
import com.jushi.muisc.chat.common.view.JSTextView;

import java.util.List;

/**
 * MV信息
 */

public class MvDataAdapter extends RecyclerView.Adapter<MvDataAdapter.ViewHolder> {
    private Context mContext;
    private List<MVBean.ResultBean.MvListBean> mvListBeans;

    public MvDataAdapter(Context mContext, List<MVBean.ResultBean.MvListBean> mvListBeans) {
        this.mContext = mContext;
        this.mvListBeans = mvListBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mv_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MVBean.ResultBean.MvListBean mvListBean = mvListBeans.get(position);
        Glide.with(mContext).load(mvListBean.getThumbnail2()).crossFade().into(holder.imageView);
        holder.textView.setText(mvListBean.getTitle());
        if (listener != null){
            final MVBean.ResultBean.MvListBean mvBean = mvListBean;
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(mvBean,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mvListBeans.size();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(MVBean.ResultBean.MvListBean mvListBean, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ImageView imageView;
        private JSTextView textView;
        private LinearLayout itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.mv_image_info_item);
            textView = itemView.findViewById(R.id.mv_title_info_item);
            Utils.setLatestImageViewParams(mContext, imageView);
            itemLayout = itemView.findViewById(R.id.mv_item_layout);
            Utils.setLatestLinerParams(mContext, itemLayout);
        }
    }
}
