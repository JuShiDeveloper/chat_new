package com.jushi.muisc.chat.music.localmusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.localmusic.model.Song;
import com.jushi.muisc.chat.view.JSTextView;

import java.util.List;

/**
 * 本地歌曲
 */
public class LocalMusicAdapter extends RecyclerView.Adapter<LocalMusicAdapter.ViewHolder> {
    private Context mContext;
    private List<Song> songs;
    private int currentPosition = -1;
    private OnItemClickListener listener;

    public LocalMusicAdapter(Context mContext, List<Song> songs) {
        this.mContext = mContext;
        this.songs = songs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.all_recommend_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setStateChange(holder, position);
        Song song = songs.get(position);
        holder.tvNumber.setText(String.valueOf(position + 1));
        holder.tvSongName.setText(song.getSongName());
        holder.tvSinger.setText(song.getSongAuthor());
        if (listener != null){
            final Song song1 = song;
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(song1,pos);
                }
            });
        }
    }

    private void setStateChange(ViewHolder holder, int position) {
        if (currentPosition == position) {
            if (holder.volume.getVisibility() == View.GONE) {
                holder.volume.setVisibility(View.VISIBLE);
                holder.tvNumber.setVisibility(View.GONE);
            }
        } else {
            holder.volume.setVisibility(View.GONE);
            holder.tvNumber.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public void setStateChange(int position){
        currentPosition = position;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(Song song,int position);
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
