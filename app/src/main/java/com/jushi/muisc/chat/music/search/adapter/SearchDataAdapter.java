package com.jushi.muisc.chat.music.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.dialog.download.ShowMoreMenuDialog;
import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.music.search.model.SearchDataModel;
import com.jushi.muisc.chat.common.view.JSTextView;

import java.util.List;

/**
 * 搜索出来的歌曲信息
 */

public class SearchDataAdapter extends RecyclerView.Adapter<SearchDataAdapter.ViewHolder> {

    private Context mContext;
    private List<SearchDataModel.SongListBean> songListBeans;
    private int currentPosition = -1;

    public SearchDataAdapter(Context mContext, List<SearchDataModel.SongListBean> songListBeans) {
        this.mContext = mContext;
        this.songListBeans = songListBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.all_recommend_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchDataModel.SongListBean songBean = songListBeans.get(position);
        if (currentPosition == position) {
            if (holder.volume.getVisibility() == View.GONE) {
                holder.volume.setVisibility(View.VISIBLE);
                holder.tvNumber.setVisibility(View.GONE);
            }
        } else {
            holder.volume.setVisibility(View.GONE);
            holder.tvNumber.setVisibility(View.VISIBLE);
        }
        holder.tvNumber.setText(String.valueOf(position + 1));
        String title = songBean.getTitle().replace("<em>", "").replace("</em>", "");
        String author = songBean.getAuthor().replace("<em>", "").replace("</em>", "");
        holder.tvSongName.setText(title);
        holder.tvSinger.setText(author);
        if (listener != null) {
            final SearchDataModel.SongListBean bean = songBean;
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
                song.setSongName(songListBeans.get(position).getTitle().replace("<em>", "").replace("</em>", ""));
                song.setSongAuthor(songListBeans.get(position).getAuthor().replace("<em>", "").replace("</em>", ""));
                ShowMoreMenuDialog.showMenuDialog(mContext, song);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songListBeans.size();
    }

    public void setDateChanged(int position) {
        this.currentPosition = position;
        notifyDataSetChanged();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(SearchDataModel.SongListBean songBean, int position);
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
