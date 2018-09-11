package com.jushi.muisc.chat.music.home_page.artist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.home_page.artist.model.ArtistsModel;
import com.jushi.muisc.chat.common.transform.CircleTransform;
import com.jushi.muisc.chat.common.utils.ShadowUtils;
import com.jushi.muisc.chat.common.view.JSTextView;

import java.util.List;

/**
 * 所有歌手
 */

public class AllArtistAdapter extends RecyclerView.Adapter<AllArtistAdapter.ViewHolder> {

    private Context mContext;
    private List<ArtistsModel.ArtistBean> artistBeans;

    public AllArtistAdapter(Context mContext, List<ArtistsModel.ArtistBean> artistBeans) {
        this.mContext = mContext;
        this.artistBeans = artistBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.all_artist_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArtistsModel.ArtistBean artistBean = artistBeans.get(position);
        if (artistBean.getAvatar_middle().equals("") || artistBean.getAvatar_middle() == null) {
            Glide.with(mContext).load(R.drawable.default_music_image).transform(new CircleTransform(mContext)).into(holder.artistImage);
        } else {
            Glide.with(mContext).load(artistBean.getAvatar_middle()).transform(new CircleTransform(mContext)).into(holder.artistImage);
        }
        holder.tvAtistName.setText(artistBean.getName());
        holder.tvArtistCountry.setText(artistBean.getCountry());

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

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(ArtistsModel.ArtistBean artistBean,int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private RelativeLayout itemLayout;
        private ImageView artistImage;
        private JSTextView tvAtistName, tvArtistCountry;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemLayout = itemView.findViewById(R.id.all_artist_recycler_item_layout);
            artistImage = itemView.findViewById(R.id.all_artist_recycler_item_artist_image);
            tvAtistName = itemView.findViewById(R.id.all_artist_recycler_item_tv_artist_name);
            tvArtistCountry = itemView.findViewById(R.id.all_artist_recycler_item_artist_artist_country);
            ShadowUtils.setShadowDown_2(mContext, itemLayout);
        }
    }
}
