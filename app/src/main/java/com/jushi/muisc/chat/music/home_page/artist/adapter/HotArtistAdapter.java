package com.jushi.muisc.chat.music.home_page.artist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.home_page.artist.model.ArtistsModel;
import com.jushi.muisc.chat.common.transform.CircleTransform;
import com.jushi.muisc.chat.common.utils.Utils;
import com.jushi.muisc.chat.common.view.JSTextView;

import java.util.List;

/**
 * 首页中的热门歌手
 */

public class HotArtistAdapter extends RecyclerView.Adapter<HotArtistAdapter.ViewHolder> {
    private Context mContext;
    private List<ArtistsModel.ArtistBean> artistBeans;

    public HotArtistAdapter(Context mContext, List<ArtistsModel.ArtistBean> artistBeans) {
        this.mContext = mContext;
        this.artistBeans = artistBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_vew_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArtistsModel.ArtistBean artistBean = artistBeans.get(position);
        Glide.with(mContext).load(artistBean.getAvatar_big()).transform(new CircleTransform(mContext)).crossFade().into(holder.imageView);
        holder.textView.setText(artistBean.getName());
        if (listener != null){
            final ArtistsModel.ArtistBean bean = artistBean;
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
        return artistBeans.size();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ArtistsModel.ArtistBean artistBean, int position);
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
            Utils.setArtistImageViewParams(mContext, imageView);
        }
    }
}
