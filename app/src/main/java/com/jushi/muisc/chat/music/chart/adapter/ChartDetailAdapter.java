package com.jushi.muisc.chat.music.chart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.dialog.download.ShowMoreMenuDialog;
import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.music.public_model.LatestMusicModel;
import com.jushi.muisc.chat.view.JSTextView;

import java.util.List;

/**
 * 有新的Url的榜单歌曲详情
 */
public class ChartDetailAdapter extends RecyclerView.Adapter<ChartDetailAdapter.ViewHolder> {
    private Context context;
    private List<LatestMusicModel.SongListBean> songListBeans;
    private int currentPosition = -1;

    public ChartDetailAdapter(Context context, List<LatestMusicModel.SongListBean> songListBeans) {
        this.context = context;
        this.songListBeans = songListBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_recommend_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LatestMusicModel.SongListBean listBean = songListBeans.get(position);
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
        if (listener != null) {
            final LatestMusicModel.SongListBean bean = listBean;
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(bean, pos);
                }
            });
        }
        setMoreBtnClick(holder, position);
    }

    private void setMoreBtnClick(ViewHolder holder, final int position) {
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Song song = new Song();
                song.setSongId(songListBeans.get(position).getSong_id());
                song.setSongName(songListBeans.get(position).getTitle());
                song.setSongAuthor(songListBeans.get(position).getAuthor());
                ShowMoreMenuDialog.showMenuDialog(context, song);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songListBeans.size();
    }

    public void setPositionChanged(int position) {
        this.currentPosition = position;
        notifyDataSetChanged();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(LatestMusicModel.SongListBean listBean, int position);
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
