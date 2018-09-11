package com.jushi.muisc.chat.music.zhibo_video.zhibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.zhibo_video.zhibo.model.ZhiBoModel;
import com.jushi.muisc.chat.common.transform.CornersTransform;
import com.jushi.muisc.chat.common.utils.Utils;
import com.jushi.muisc.chat.common.view.JSTextView;

import java.util.List;

/**
 * 直播信息
 */

public class LiveDataAdapter extends RecyclerView.Adapter<LiveDataAdapter.ViewHolder> {
    private Context mContext;
    private List<ZhiBoModel.DataBeanX.DataBean> liveBeans;

    public LiveDataAdapter(Context mContext, List<ZhiBoModel.DataBeanX.DataBean> liveBeans) {
        this.mContext = mContext;
        this.liveBeans = liveBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.live_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ZhiBoModel.DataBeanX.DataBean dataBean = liveBeans.get(position);
        Glide.with(mContext).load(dataBean.getLiveimg())
                .transform(new CornersTransform(mContext, 30)) // 圆角
                .crossFade()  //淡入
                .into(holder.liveImage);
        holder.tvPeopleNumber.setText(dataBean.getUsercount() + "人");
        holder.tvNickName.setText(dataBean.getNickname());
        if (listener != null){
            final ZhiBoModel.DataBeanX.DataBean bean = dataBean;
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
        return liveBeans.size();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ZhiBoModel.DataBeanX.DataBean dataBean, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private JSTextView tvPeopleNumber, tvNickName;
        private ImageView liveImage;
        private RelativeLayout itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvPeopleNumber = itemView.findViewById(R.id.live_people_number);
            tvNickName = itemView.findViewById(R.id.live_nickName_info_tv);
            liveImage = itemView.findViewById(R.id.live_image_info_iv);
            itemLayout = itemView.findViewById(R.id.live_item_layout);
            Utils.setZhiBoImageLayoutParams(mContext, itemLayout);
        }
    }
}
