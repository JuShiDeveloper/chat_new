package com.jushi.muisc.chat.music.play_music;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.service.PlayMusicService;
import com.jushi.muisc.chat.tools.PeriodicTask;
import com.jushi.muisc.chat.tools.music.OkHttpTool;
import com.jushi.muisc.chat.tools.music.RotateAnimatorTool;
import com.jushi.muisc.chat.transform.CircleTransform;
import com.jushi.muisc.chat.utils.DateUtils;
import com.jushi.muisc.chat.music.utils.SaveUtils;
import com.jushi.muisc.chat.view.JSTextView;
import com.jushi.muisc.chat.view.lrcview.ILrcBuilder;
import com.jushi.muisc.chat.view.lrcview.ILrcViewListener;
import com.jushi.muisc.chat.view.lrcview.impl.DefaultLrcBuilder;
import com.jushi.muisc.chat.view.lrcview.impl.LrcRow;
import com.jushi.muisc.chat.view.lrcview.impl.LrcView;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayMusicActivity extends AppCompatActivity implements PlayMusicService.OnMusicPlayListener, View.OnClickListener {

    private Toolbar toolbar;
    private SeekBar seekBar;
    private JSTextView tvProgressTime, tvTotalTime, tvSongName, tvAuthor;
    private RadioButton preBtn, playBtn, nextBtn;
    private ImageView roundImage;
    private LrcView lrcView;
    private int duration;
    private int currentDuration;
    private PeriodicTask periodicTask;
    private SaveUtils saveUtils;
    private String songName, author, lrcPath, imagePath;
    private int progressTime;
    //更新歌词的定时器
    private Timer mTimer;
    //更新歌词的定时任务
    private TimerTask mTask;
    private RotateAnimatorTool animatorTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_music);
        PlayMusicService.checkStartService(this);
        saveUtils = SaveUtils.getInstance(this);
        songName = saveUtils.getSavedSongName();
        author = saveUtils.getSaveAuthor();
        lrcPath = saveUtils.getSaveLrcPath();
        imagePath = saveUtils.getSaveAuthorImage();
        initView();
    }

    private void initView() {
        findWidget();
        initToolBar();
        initSeekBar();
        runTimer();
        showSongInfo();
        seekBarListener();
    }

    private void findWidget() {
        toolbar = findViewById(R.id.play_music_activity_toolBar);
        seekBar = findViewById(R.id.play_music_activity_seekBar);
        tvProgressTime = findViewById(R.id.play_activity_music_progress_time);
        tvTotalTime = findViewById(R.id.play_activity_music_total_time);
        tvSongName = findViewById(R.id.play_music_activity_songName);
        tvAuthor = findViewById(R.id.play_music_activity_author);
        lrcView = findViewById(R.id.play_music_activity_lrcView);
        preBtn = findViewById(R.id.prev_button_play_activity);
        playBtn = findViewById(R.id.play_button_play_activity);
        nextBtn = findViewById(R.id.next_button_play_activity);
        roundImage = findViewById(R.id.round_image_play_activity);

        preBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        PlayMusicService.setOnMusicPlayListener(this);
        animatorTool = new RotateAnimatorTool(this, roundImage);

        changePlayBtnState();
        setViewClickListener();
        showLrc();
    }

    private void setViewClickListener() {
        //旋转的圆形图片点击事件监听，隐藏自己
        roundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roundImage.getVisibility() == View.VISIBLE) {
                    roundImage.setVisibility(View.GONE);
                }
            }
        });

        //显示歌词控件的自定义点击事件(有歌词在滚动时)
        lrcView.setOnClickListener(new LrcView.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRoundImage();
            }
        });
        //无歌词显示时的点击事件
        lrcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoundImage();
            }
        });
    }

    //显示旋转的圆形图片
    private void showRoundImage() {
        if (roundImage.getVisibility() == View.GONE) {
            roundImage.setVisibility(View.VISIBLE);
        }
    }

    //播放按钮控件的状态改变
    @SuppressLint("NewApi")
    private void changePlayBtnState() {
        if (PlayMusicService.isPlaying()) {
            startAnimation();
            startPeriodicTask();
            playBtn.setButtonDrawable(getDrawable(R.drawable.play_controller_icon));
        } else {
            stopAnimation();
            stopPeriodiceTask();
            playBtn.setButtonDrawable(getDrawable(R.drawable.pause_controller_icon));
        }
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initSeekBar() {
        //获取当前播放歌曲的总时间
        duration = PlayMusicService.getDuration();
        //获取当前播放歌曲的进度时间
        currentDuration = PlayMusicService.getCurrentDuration();
        seekBar.setMax(duration);
        seekBar.setProgress(currentDuration);
        showMusicTime();
    }

    //显示时间(总时间和进度时间)
    private void showMusicTime() {
        if (duration >= 0) {
            tvTotalTime.setText(DateUtils.getGenTimeMS(duration));
            tvProgressTime.setText(DateUtils.getGenTimeMS(currentDuration));
        }
    }

    private void seekBarListener() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressTime = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopPeriodiceTask();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekTo(progressTime);
                startPeriodicTask();
            }
        });
    }

    //拖动进度条时改变播放进度
    private void seekTo(int progress) {
        PlayMusicService.seekTo(progress);
    }

    //执行周期性事件获取当前播放的时间
    private void runTimer() {
        periodicTask = new PeriodicTask(new PeriodicTask.Task() {
            @Override
            public void execute() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initSeekBar();
                    }
                });
            }
        }, 800);
        startPeriodicTask();
    }

    //开始执行周期任务
    private void startPeriodicTask() {
        if (PlayMusicService.isPlaying()) {
            if (periodicTask != null)
                periodicTask.start();
        }
    }

    //结束执行周期任务
    private void stopPeriodiceTask() {
        if (periodicTask != null)
            periodicTask.stop();
    }

    //显示当前播放歌曲的信息
    private void showSongInfo() {
        tvSongName.setText(songName);
        tvAuthor.setText(author);
        if (imagePath != null) {
            Glide.with(this)
                    .load(imagePath)
                    .transform(new CircleTransform(this))
                    .into(roundImage);
        } else {
            roundImage.setImageResource(R.drawable.play_music_activity_round_image);
        }
    }

    //显示当前播放歌曲的歌词
    private void showLrc() {
        if (null == lrcPath) {
            lrcView.setLoadingTipText("暂无歌词内容");
            return;
        }
        if (lrcPath.toLowerCase().startsWith("http")) { //判断歌词地址是否是网络地址
            getContentFromLrcHttp(lrcPath);
        } else {
            getContentFromLrcFile(lrcPath);
        }

    }

    private void displayLrc(final String lrc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //解析歌词构造器
                ILrcBuilder builder = new DefaultLrcBuilder();
                //解析歌词返回LrcRow集合
                List<LrcRow> rows = builder.getLrcRows(lrc);
                //将得到的歌词集合传给mLrcView用来展示
                lrcView.setLrc(rows);
                beginLrcPlay();
                //拖动歌词
                lrcView.setListener(new ILrcViewListener() {
                    @Override
                    public void onLrcSeeked(int newPosition, LrcRow row) {
                        seekTo((int) row.time);
                    }
                });
            }
        });
    }

    /**
     * 读取本地lrc文件流
     *
     * @param fileName
     * @return
     */
    public void getContentFromLrcFile(String fileName) {
        String result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                if (line.trim().equals(""))
                    continue;
                result += line + "\r\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        displayLrc(result);
    }

    /**
     * 读取网络歌词文件
     */
    private void getContentFromLrcHttp(String lrcPath) {
        OkHttpTool.httpClient(lrcPath, new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onResponse(Response response) {
                try {
                    String result = response.body().string();
                    displayLrc(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //开始滚动歌词
    private void beginLrcPlay() {
        if (mTimer == null) {
            mTimer = new Timer();
            mTask = new LrcTask();
            mTimer.scheduleAtFixedRate(mTask, 0, 800);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPeriodiceTask();
        stopAnimation();
    }

    //开始旋转动画
    private void startAnimation() {
        animatorTool.startSpin();
    }

    //停止旋转动画
    private void stopAnimation() {
        animatorTool.stopSpin();
    }

    //音乐播放时回调的方法，传递歌曲信息过来
    @Override
    public void onMusicPlay(String songName, String author, String imagePath, String lrcPath, int index) {
        this.songName = songName;
        this.author = author;
        this.lrcPath = lrcPath;
        this.imagePath = imagePath;
        showSongInfo();
        showLrc();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prev_button_play_activity: //上一首按钮
                PlayMusicService.playPre();
                break;
            case R.id.play_button_play_activity:  //播放按钮
                if (PlayMusicService.isPlaying()) {
                    PlayMusicService.pause();
                } else {
                    PlayMusicService.startPlay();
                }
                break;
            case R.id.next_button_play_activity: //下一首按钮
                PlayMusicService.playNext();
                break;
        }
        //按钮被点击后需更改按钮状态
        changePlayBtnState();
    }

    /**
     * 展示歌词的定时任务
     */
    class LrcTask extends TimerTask {
        @Override
        public void run() {
            //获取歌曲播放的位置
            final long timePassed = currentDuration;
            runOnUiThread(new Runnable() {
                public void run() {
                    //滚动歌词
                    lrcView.seekLrcToTime(timePassed);
                }
            });

        }
    }

    ;
}
