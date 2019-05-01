package com.oridway.www.uiframe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.oridway.www.uiframe.R;
import com.oridway.www.uiframe.adpter.TopicListAdapter;
import com.oridway.www.uiframe.bean.ClsTopic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TopicListActivity";
    @BindView(R.id.section_list_view)
    ListView mListView;
    @BindView(R.id.tv_title_middle)
    TextView title;
    @BindView(R.id.title_left)
    ImageView back;
    @BindView(R.id.edit_tv)
    TextView edit;
    @BindView(R.id.filter_tv)
    TextView filter;
    @BindView(R.id.btn_delete_topic)
    LinearLayout btnDelete;

    private Context mContext;
    private boolean isEditable;
    private List<ClsTopic> mClsTopicList;
    private TopicListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }

    protected void initData() {

        mContext = this;
//        getSectionList();
        initOfflineData(20);
        isEditable = false;
    }

    private void initOfflineData(int sum) {

        List<ClsTopic> clsTopicList = new ArrayList<>();
        for (int i = 0; i < sum; i++) {
            ClsTopic clsTopic = new ClsTopic();
            clsTopic.setRowNum(" rowNum" + i);
            clsTopic.setSectionID(" sectionID" + i);
            clsTopic.setSectionName(" sectionName" + i);
            clsTopic.setSectionManager(" sectionManager" + i);
            clsTopic.setTopicID(" topicID" + i);
            clsTopic.setTitle(" title" + i);
            clsTopic.setTopicDesc(" topicDesc" + i);
            clsTopic.setCreateDate(" createDate" + i);
            clsTopic.setAuthorID(" authorID" + i);
            clsTopic.setAuthor(" author" + i);
            clsTopic.setViewNum(" viewNum" + i);
            clsTopic.setReplyNum(" replyNum" + i);
            clsTopic.setIsBoutique(" isBoutique" + i);
            clsTopic.setSequence(" sequence" + i);
            clsTopic.setIsTop(" isTop" + i);
            clsTopic.setIsRecommend(" isRecommend" + i);
            clsTopic.setReplyID(" replyID" + i);
            clsTopic.setReplyContent(" replyContent" + i);
            clsTopic.setReplyTime(" replyTime" + i);
            clsTopic.setReplyAuthorID(" replyAuthorID" + i);
            clsTopic.setReplyAuthorName(" replyAuthorName" + i);
            clsTopic.setdCount(" dCount" + i);
            clsTopicList.add(clsTopic);
        }
        mClsTopicList = clsTopicList;
        mAdapter = new TopicListAdapter(mClsTopicList, mContext);
        mListView.setAdapter(mAdapter);
    }

    protected void initView() {
        title.setText("二级列表");
        filter.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);
    }

    protected void initListener() {

        btnDelete.setOnClickListener(this);
        edit.setOnClickListener(this);
        back.setOnClickListener(this);
        mListView.setOnItemClickListener((parent, view, position, id) -> {

            ClsTopic clsTopic = mClsTopicList.get(position);
            if (getIsEditable()) {
                if (clsTopic.getIsCheckBoxVisible()) {
                    clsTopic.setIsChecked(!clsTopic.getIsChecked());
                    mAdapter.notifyDataSetChanged();
                }
                for (int i = 0; i < mClsTopicList.size(); i++) {
                    ClsTopic topic = mClsTopicList.get(i);
                    if (topic.getIsChecked()) {
                        btnDelete.setVisibility(View.VISIBLE);
                        break;
                    }
                    if (i == mClsTopicList.size() - 1) {
                        btnDelete.setVisibility(View.GONE);
                    }
                }
            } else {
                String topicID = clsTopic.getTopicID();
                Intent intent = new Intent(mContext, ReplyListActivity.class);
                intent.putExtra("topicID", topicID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.title_left) {
            finish();
        }
        if (v.getId() == R.id.edit_tv) {
            switchEditable();
        }
        if (v.getId() == R.id.btn_delete_topic) {
        }
    }

    private void switchEditable() {
        for (ClsTopic clsTopic : mClsTopicList) {
            clsTopic.setIsCheckBoxVisible(!clsTopic.getIsCheckBoxVisible());
            clsTopic.setIsChecked(false);
        }
        mAdapter.notifyDataSetChanged();
        btnDelete.setVisibility(View.GONE);
        setIsEditable(!getIsEditable());
    }

    public boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean editable) {
        isEditable = editable;
    }
}