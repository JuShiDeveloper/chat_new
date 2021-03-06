package com.jushi.muisc.chat.music.play.play_music;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.common.PeriodicTask;
import com.jushi.muisc.chat.common.utils.DisplayUtils;
import com.jushi.muisc.chat.common.utils.SystemBarUtil;
import com.jushi.muisc.chat.music.common.utils.http.OkHttpTool;
import com.jushi.muisc.chat.music.common.utils.animation.RotateAnimatorTool;
import com.jushi.muisc.chat.common.transform.CircleTransform;
import com.jushi.muisc.chat.common.utils.DateUtils;
import com.jushi.muisc.chat.common.utils.SaveUtils;
import com.jushi.muisc.chat.common.view.JSTextView;
import com.jushi.muisc.chat.common.view.lrcview.ILrcBuilder;
import com.jushi.muisc.chat.common.view.lrcview.ILrcViewListener;
import com.jushi.muisc.chat.common.view.lrcview.impl.DefaultLrcBuilder;
import com.jushi.muisc.chat.common.view.lrcview.impl.LrcRow;
import com.jushi.muisc.chat.common.view.lrcview.impl.LrcView;
import com.jushi.muisc.chat.common.view.ripplesoundplayer.RippleVisualizerView;
import com.jushi.muisc.chat.common.view.ripplesoundplayer.renderer.ColorfulBarRenderer;
import com.jushi.muisc.chat.common.view.ripplesoundplayer.util.PaintUtil;
import com.jushi.camera.helper.PermissionHelper;
import com.jushi.rxPermissions.RxPermissions;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.functions.Action1;

public class PlayMusicActivity extends AppCompatActivity implements PlayMusicService.OnMusicPlayListener, View.OnClickListener {
    private View statusBar;
    private Toolbar toolbar;
    private SeekBar seekBar;
    private Spinner spinner;
    private RelativeLayout speedContainer;
    private JSTextView tvProgressTime, tvTotalTime, tvSongName, tvAuthor;
    private RadioButton preBtn, playBtn, nextBtn;
    private ImageView roundImage;
    private ImageView ivBg;//页面背景
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
    //音频动画
    private RippleVisualizerView rippleVisualizerView;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        SystemBarUtil.setTranslucentStatus(this);
        PlayMusicService.checkStartService(this);
        saveUtils = SaveUtils.getInstance(this);
        songName = saveUtils.getSavedSongName();
        author = saveUtils.getSaveAuthor();
        lrcPath = saveUtils.getSaveLrcPath();
        imagePath = saveUtils.getSaveAuthorImage();
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkRecordAudioPermission();
        } else {
            initRippleView();
        }
        changePlayBtnState();
    }

    private void checkRecordAudioPermission() {
        RxPermissions.getInstance(this).request(Manifest.permission.RECORD_AUDIO).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    initRippleView();
                }
            }
        });
    }

    private void initView() {
        findWidget();
        initToolBar();
        initSeekBar();
        runTimer();
        showSongInfo();
        seekBarListener();
        initSpinner();
        initAnimation();
    }

    private void initAnimation() {
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.refresh_head_drawable);
        ivBg.setBackgroundDrawable(animationDrawable);
    }

    private void findWidget() {
        statusBar = findViewById(R.id.play_music_status_bar);
        statusBar.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DisplayUtils.getStatusBarHeight(this)));
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
        spinner = findViewById(R.id.bei_su_play);
        speedContainer = findViewById(R.id.bei_su_container);
        ivBg = findViewById(R.id.iv_play_bg);


        preBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        PlayMusicService.setOnMusicPlayListener(this);
        animatorTool = new RotateAnimatorTool(this, roundImage);

        setViewClickListener();
        showLrc();
    }

    /**
     * 初始化音频动画view
     */
    private void initRippleView() {
        rippleVisualizerView = findViewById(R.id.line_renderer_demo);
        rippleVisualizerView.setMediaPlayer(PlayMusicService.getMediaPlayer());
//        rippleVisualizerView.setCurrentRenderer(new BarRenderer(16, PaintUtil.getBarGraphPaint(Color.WHITE)));
//        renderDemoView.setCurrentRenderer(new LineRenderer(PaintUtil.getLinePaint(Color.YELLOW)));
        rippleVisualizerView.setCurrentRenderer(new ColorfulBarRenderer(15, PaintUtil.getBarGraphPaint(Color.WHITE)
                , getResources().getColor(R.color.e80b0b)
                , getResources().getColor(R.color._397b04)));
        rippleVisualizerView.setAmplitudePercentage(3); //柱状的高度
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
//                showRoundImage();
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
            startRippleView();
            speedContainer.setVisibility(View.VISIBLE);
        } else {
            stopAnimation();
            stopPeriodiceTask();
            playBtn.setButtonDrawable(getDrawable(R.drawable.pause_controller_icon));
            stopRippleView();
            speedContainer.setVisibility(View.INVISIBLE);
        }
    }

    private void stopRippleView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> result = PermissionHelper.INSTANCE.checkSelfPermission(this, new String[]{Manifest.permission.RECORD_AUDIO});
            if (result.isEmpty()) {
                rippleVisualizerView.stop(); //停止音频动画
            } else {
                checkRecordAudioPermission();
            }
        } else {
            rippleVisualizerView.stop(); //停止音频动画
        }
    }

    private void startRippleView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> result = PermissionHelper.INSTANCE.checkSelfPermission(this, new String[]{Manifest.permission.RECORD_AUDIO});
            if (result.isEmpty()) {
                rippleVisualizerView.play(); //播放音频动画
            } else {
                checkRecordAudioPermission();
            }
        } else {
            rippleVisualizerView.play(); //播放音频动画
        }
    }

    private void initToolBar() {
        toolbar.setTitle(songName);
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

    private void initSpinner() {
        if (PlayMusicService.isPlaying()) {
            speedContainer.setVisibility(View.VISIBLE);
        }
        final String[] items = getResources().getStringArray(R.array.bei_su_play);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_layout,
                R.id.spinner_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) return;
                PlayMusicService.setPlaySpeed(Float.valueOf(items[i]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
//        tvSongName.setText(songName);
//        tvAuthor.setText(author);
        toolbar.setTitle(songName);
        toolbar.setSubtitle(author);
        if (imagePath != null) {
            Glide.with(this)
                    .load(imagePath)
                    .transform(new CircleTransform(this))
                    .into(roundImage);
        } else {
            Glide.with(this)
                    .load(R.mipmap.music_logo)
                    .transform(new CircleTransform(this))
                    .into(roundImage);
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
        animationDrawable.start();
    }

    //停止旋转动画
    private void stopAnimation() {
        animatorTool.stopSpin();
        animationDrawable.stop();
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

}
