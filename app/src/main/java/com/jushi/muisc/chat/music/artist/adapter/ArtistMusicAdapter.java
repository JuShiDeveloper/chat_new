package com.jushi.muisc.chat.music.artist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.artist.model.ArtistMusic;
import com.jushi.muisc.chat.view.JSTextView;

import java.util.List;

/**
 * 歌手的歌
 */
public class ArtistMusicAdapter extends RecyclerView.Adapter<ArtistMusicAdapter.ViewHolder> {

    private Context context;
    private List<ArtistMusic.SonglistBean> songBeans;
    private int currentPosition = -1;

    public ArtistMusicAdapter(Context context, List<ArtistMusic.SonglistBean> songBeans) {
        this.context = context;
        this.songBeans = songBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_recommend_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        changeState(holder, position);
        ArtistMusic.SonglistBean songBean = songBeans.get(position);
        holder.tvNumber.setText(String.valueOf(position + 1));
        holder.tvSongName.setText(songBean.getTitle());
        holder.tvSinger.setText(songBean.getAuthor());

        if (listener != null){
            final ArtistMusic.SonglistBean bean = songBean;
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(bean,pos);
                }
            });
        }
    }

    private void changeState(ViewHolder holder, int position) {
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
        return songBeans.size();
    }

    public void setSelectChanged(int position){
        currentPosition = position;
        notifyDataSetChanged();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(ArtistMusic.SonglistBean songBean,int position);
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
