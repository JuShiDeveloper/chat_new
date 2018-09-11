package com.jushi.muisc.chat.music.chart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.chart.model.ChartDataModel;
import com.jushi.muisc.chat.common.transform.CornersTransform;
import com.jushi.muisc.chat.common.view.JSTextView;

import java.util.List;

/**
 * 榜单页面
 */
public class ChartDataAdapter extends RecyclerView.Adapter<ChartDataAdapter.ViewHolder> {
    private Context context;
    private List<ChartDataModel.ContentBeanX> contentBean;

    public ChartDataAdapter(Context context, List<ChartDataModel.ContentBeanX> contentBean) {
        this.context = context;
        this.contentBean = contentBean;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chart_recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChartDataModel.ContentBeanX contentBeanX = contentBean.get(position);
        if (position == 4){
            Glide.with(context).load(contentBeanX.getPic_s444()).
                    transform(new CornersTransform(context, 30)).
                    crossFade().into(holder.chartImage);
        }else {
            Glide.with(context).load(contentBeanX.getPic_s192()).
                    transform(new CornersTransform(context, 30)).
                    crossFade().into(holder.chartImage);
        }
        holder.tvChartTitle.setText(contentBeanX.getName());
        holder.tvMusicTitle1.setText("1."+contentBeanX.getContent().get(0).getTitle());
        holder.tvMusicTitle2.setText("2."+contentBeanX.getContent().get(1).getTitle());
        holder.tvMusicTitle3.setText("3."+contentBeanX.getContent().get(2).getTitle());
        if (position ==contentBean.size() - 1 ){
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            params.bottomMargin = 35;
            holder.itemView.setLayoutParams(params);
        }
        if (listener != null){
            final ChartDataModel.ContentBeanX beanX = contentBeanX;
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(beanX,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return contentBean.size();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(ChartDataModel.ContentBeanX contentBeanX,int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private JSTextView tvChartTitle, tvMusicTitle1, tvMusicTitle2, tvMusicTitle3;
        private ImageView chartImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvChartTitle = itemView.findViewById(R.id.chart_title_text);
            tvMusicTitle1 = itemView.findViewById(R.id.chart_music_title_1);
            tvMusicTitle2 = itemView.findViewById(R.id.chart_music_title_2);
            tvMusicTitle3 = itemView.findViewById(R.id.chart_music_title_3);
            chartImage = itemView.findViewById(R.id.chart_item_image);
        }
    }
}
