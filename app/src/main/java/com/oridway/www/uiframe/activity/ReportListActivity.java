package com.oridway.www.uiframe.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oridway.www.uiframe.R;
import com.oridway.www.uiframe.adpter.ReportOnlineRecyclerAdapter;
import com.oridway.www.uiframe.bean.ClsOnlineReport;
import com.oridway.www.uiframe.utils.OnlineReportListCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 1.通过ItemTouchHelper.Callback实现长按拖动
 * 2.只有进入编辑状态下才允许拖动和显示复选框和拖动图标
 * 3.通过isEditable的值判断是否编辑状态
 * 4.每次点击编辑按钮即切换编辑状态都要把isEditable的值取反
 * 5.只有选中至少一个条目才会弹出工具栏
 */

public class ReportListActivity extends AppCompatActivity implements View.OnClickListener, ReportOnlineRecyclerAdapter.Callback {

    @BindView(R.id.tv_title_middle)
    TextView title;
    @BindView(R.id.title_left)
    ImageView backButton;
    @BindView(R.id.online_report_listview)
    RecyclerView mRecyclerView;
    @BindView(R.id.edit_tv)
    TextView edit;
    @BindView(R.id.filter_tv)
    TextView filter;
    @BindView(R.id.btn_delete)
    TextView delete;
    @BindView(R.id.btn_release)
    TextView release;
    @BindView(R.id.btn_close)
    TextView close;
    @BindView(R.id.btn_top)
    TextView top;

    private Context mContext;
    private boolean isEditable;
    private ReportOnlineRecyclerAdapter mAdapter;
    private List<ClsOnlineReport> mClsOnlineReportList;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_report);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }

    protected void initData() {
        mContext = this;
        getOfflineData(60);

        OnlineReportListCallback callback = new OnlineReportListCallback(mAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);

        setIsEditable(true);
        setRecyclerViewDraggable(false);
    }

    private void getOfflineData(int num) {

        List<ClsOnlineReport> clsOnlineReportList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            ClsOnlineReport onlineReport = new ClsOnlineReport();
            onlineReport.setActiveDate("activeDate " + i);
            onlineReport.setAutoCloseDate("autoCloseDate " + i);
            onlineReport.setBulletinID("bulletinID " + i);
            onlineReport.setBulletinTime("bulletinTime " + i);
            onlineReport.setBulletinTitle("bulletinTitle " + i);
            onlineReport.setCreater("creater " + i);
            onlineReport.setCreaterID("creater " + i);
            onlineReport.setIsActive("isActive " + i);
            onlineReport.setModiManID("modiManID " + i);
            onlineReport.setModiManName("modiManName " + i);
            onlineReport.setOnTop("onTop " + i);
            onlineReport.setOrderNum("oderNum " + i);
            clsOnlineReportList.add(onlineReport);
        }

        mClsOnlineReportList = clsOnlineReportList;
        mAdapter = new ReportOnlineRecyclerAdapter(mClsOnlineReportList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void initView() {
        title.setText("可拖拽列表");
        edit.setVisibility(View.VISIBLE);
        hideBottomMenu();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    protected void initListener() {
        filter.setOnClickListener(this);
        backButton.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_left) {
            finish();
        }
        if (v.getId() == R.id.edit_tv) {
            for (ClsOnlineReport clsOnlineReport : mClsOnlineReportList) {
                clsOnlineReport.setIsCheckBoxVisible(!clsOnlineReport.getIsCheckBoxVisible());
                clsOnlineReport.setIsChecked(false);
            }

            mAdapter.notifyDataSetChanged();
            hideBottomMenu();

            setRecyclerViewDraggable(getIsEditable());
            setIsEditable(!getIsEditable());
        }
    }


    @Override
    public void onClick(View v, int position) {

        switch (v.getId()) {
            case R.id.online_report_author:
            case R.id.online_report_time:
            case R.id.online_report_title:
            case R.id.online_report_checkbox:

                ClsOnlineReport clsOnlineReport = mClsOnlineReportList.get(position);
                if (clsOnlineReport.getIsCheckBoxVisible()) {
                    clsOnlineReport.setIsChecked(!clsOnlineReport.getIsChecked());
                    mAdapter.notifyDataSetChanged();
                }

                for (int i = 0; i < mClsOnlineReportList.size(); i++) {
                    ClsOnlineReport onlineReport = mClsOnlineReportList.get(i);
                    if (onlineReport.getIsChecked()) {
                        showBottomMenu();
                        break;
                    }
                    if (i == mClsOnlineReportList.size() - 1) {
                        hideBottomMenu();
                    }
                }
        }

        if (v.getId() == R.id.btn_top) {
            Toast.makeText(mContext, "在此处调用接口", Toast.LENGTH_LONG).show();
        }
        if (v.getId() == R.id.btn_close) {
            Toast.makeText(mContext, "在此处调用接口", Toast.LENGTH_LONG).show();
        }
        if (v.getId() == R.id.btn_release) {
            Toast.makeText(mContext, "在此处调用接口", Toast.LENGTH_LONG).show();
        }
        if (v.getId() == R.id.btn_delete) {
            Toast.makeText(mContext, "在此处调用接口", Toast.LENGTH_LONG).show();
        }
    }

    private void hideBottomMenu() {
        delete.setVisibility(View.GONE);
        top.setVisibility(View.GONE);
        release.setVisibility(View.GONE);
        close.setVisibility(View.GONE);
    }

    private void showBottomMenu() {
        delete.setVisibility(View.VISIBLE);
        top.setVisibility(View.VISIBLE);
        release.setVisibility(View.VISIBLE);
        close.setVisibility(View.VISIBLE);
    }

    public boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    private void setRecyclerViewDraggable(boolean draggable) {
        if (draggable) {
            itemTouchHelper.attachToRecyclerView(mRecyclerView);
        } else {
            itemTouchHelper.attachToRecyclerView(null);
        }
    }
}