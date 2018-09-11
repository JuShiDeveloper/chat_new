package com.jushi.muisc.chat.music.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.common.utils.ShadowUtils;
import com.jushi.muisc.chat.common.view.JSTextView;

import java.util.List;

/**
 *
 * 历史搜索关键词
 */

public class HistorySearchAdapter extends RecyclerView.Adapter<HistorySearchAdapter.ViewHolder> {
    private Context mContext;
    private List<String> historyList;

    public HistorySearchAdapter(Context mContext, List<String> historyList) {
        this.mContext = mContext;
        this.historyList = historyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_search_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String keyWords = historyList.get(position).replace("+"," ");
        holder.textView.setText(keyWords);
        if (listener != null){
            final String key = keyWords;
            final int pos = position;
            holder.redioView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(key,pos);
                }
            });
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteButtonClick(key,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String keyWords, int position);
        void onDeleteButtonClick(String keyWords,int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private JSTextView textView;
        private RadioButton deleteBtn,redioView;
        private RelativeLayout itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = itemView.findViewById(R.id.history_search_recycler_item_textView);
            deleteBtn = itemView.findViewById(R.id.history_search_recycler_item_delete_btn);
            itemLayout = itemView.findViewById(R.id.history_search_item_layout);
            ShadowUtils.setShadowDown_2(mContext,itemLayout);
            redioView = itemView.findViewById(R.id.history_search_itemView);
        }
    }
}
