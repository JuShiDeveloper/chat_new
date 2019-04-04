package com.jushi.muisc.chat.music.zhibo_video.radio.common.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.zhibo_video.radio.model.RadioListEntity;

import java.util.List;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioViewHolder> {
    private Context context;
    private List<RadioListEntity.ResultBean.ChannellistBean> datas;
    private String title;
    private final String PUBLIC_CHANNEL = "公共频道";
    private final String MUSIC_CHANNEL = "音乐人频道";

    public RadioAdapter(Context context, List<RadioListEntity.ResultBean.ChannellistBean> datas, String title) {
        this.context = context;
        this.datas = datas;
        this.title = title;
    }

    @Override
    public RadioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RadioViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_radio_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RadioViewHolder holder, int position) {
        if (position > 5) return;
        holder.textView.setText(datas.get(position).getName());
        String imageUrl = "";
        if (title.equals(PUBLIC_CHANNEL)) {
            imageUrl = datas.get(position).getThumb();
        }
        if (title.equals(MUSIC_CHANNEL)) {
            imageUrl = datas.get(position).getAvatar();
        }
        Glide.with(context).load(imageUrl)
                .crossFade()  //淡入
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class RadioViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private View itemView;

        public RadioViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = itemView.findViewById(R.id.item_title);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }
}
