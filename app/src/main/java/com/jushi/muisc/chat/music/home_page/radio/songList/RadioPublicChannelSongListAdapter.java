package com.jushi.muisc.chat.music.home_page.radio.songList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.common.transform.CircleTransform;
import com.jushi.muisc.chat.music.home_page.radio.entity.RadioSongListEntity;

import java.util.List;

/**
 * 电台   公共频道下某一电台的歌曲列表
 */
public class RadioPublicChannelSongListAdapter extends RecyclerView.Adapter<RadioPublicChannelSongListAdapter.SongListViewHolder> {

    private Context context;
    private List<RadioSongListEntity.ResultBean.SonglistBean> songlistBeans;
    private OnItemClickListener listener;

    public RadioPublicChannelSongListAdapter(Context context, List<RadioSongListEntity.ResultBean.SonglistBean> songlistBeans) {
        this.context = context;
        this.songlistBeans = songlistBeans;
    }

    @Override
    public SongListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SongListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_radio_public_channel_song_list, parent, false));
    }

    @Override
    public void onBindViewHolder(SongListViewHolder holder, int position) {
        RadioSongListEntity.ResultBean.SonglistBean bean = songlistBeans.get(position);
        Glide.with(context).load(bean.getThumb()).placeholder(R.mipmap.music_logo)
                .error(R.mipmap.music_logo)
                .transform(new CircleTransform(context))
                .crossFade().into(holder.imageView);
        holder.tvSongName.setText(bean.getTitle());
        holder.tvArtist.setText(bean.getArtist());
        if (listener == null) return;
        final int posi = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(songlistBeans.get(posi), posi);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songlistBeans.size();
    }

    public class SongListViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ImageView imageView, moreBtn;
        private TextView tvSongName, tvArtist;

        public SongListViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.song_image);
            moreBtn = itemView.findViewById(R.id.item_more_btn);
            tvSongName = itemView.findViewById(R.id.song_title);
            tvArtist = itemView.findViewById(R.id.artist_name);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(RadioSongListEntity.ResultBean.SonglistBean songlistBean, int position);
    }
}
