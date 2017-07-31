package com.dxq.netease.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dxq.netease.R;
import com.dxq.netease.adapter.CommentAdapter;
import com.dxq.netease.bean.CommentComparator;
import com.dxq.netease.bean.KeyBoardBean;
import com.dxq.netease.conf.Constant;
import com.dxq.netease.http.HttpHelper;
import com.dxq.netease.http.StringCallBack;
import com.dxq.netease.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.dxq.netease.activity.NewsDetailActivity.NEWS_ID;

public class KeyBoardManActivity extends AppCompatActivity {

    private ListView mListViewReply;
    private TextView mTvSendReply;
    private EditText mEtReply;
    private String mNews_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_key_board_man);

        Intent intent = getIntent();
        mNews_id = "";
        if (intent != null) {
            mNews_id = intent.getStringExtra(NEWS_ID);
        }

        initView();
        initData();

    }

    private void initView() {
        mListViewReply = (ListView) findViewById(R.id.listView_reply);
        mTvSendReply = (TextView) findViewById(R.id.tv_send_reply);
        mEtReply = (EditText) findViewById(R.id.et_reply);
    }

    private void initData() {
        String keyBoardUrl = Constant.getKeyBoardUrl(mNews_id);
        Log.d(getClass().getSimpleName() + "dxq", "initData: " + keyBoardUrl);
        HttpHelper.getInstance().requestForAsyncGET(keyBoardUrl, new StringCallBack() {
            @Override
            public void onFail(IOException e) {
            }

            @Override
            public void onSuccess(String result) {
                parseJson(result);
            }
        });
    }

    private void parseJson(String result) {
        //准备一个集合
        ArrayList<KeyBoardBean> keyBoardBeenList = new ArrayList<>();
        try {
            //先准备出来最外层所对应的jsonObject
            JSONObject jsonObject = new JSONObject(result);
            //将jsonObject中的id数组以及下方de包含各个评论的comments的json对象
            JSONArray commentIds = jsonObject.getJSONArray("commentIds");
            JSONObject comments = jsonObject.getJSONObject("comments");
            //遍历id数组,将每个id取出来(如果id包含逗号,拿就只取最后一个逗号后面的id出来)
            for (int i = 0; i < commentIds.length(); i++) {
                String commentId = commentIds.getString(i);
                if (commentId.contains(",")) {
                    //就只取最后一个逗号后边的id
                    int lastIndexOf = commentId.lastIndexOf(",");
                    commentId = commentId.substring(lastIndexOf + 1);
                }
                //通过id作为key,去comments的json对象里取出对应的评论json字符串出来
                JSONObject comment = comments.getJSONObject(commentId);
                Log.d(getClass().getSimpleName() + "dxq", "parseJson: " + comment.toString());
                //通过gson将第四步取出来的json字符串来进行解析为javaBean
                KeyBoardBean keyBoardBean = JsonUtils.parseJson(comment.toString(), KeyBoardBean.class);
                keyBoardBeenList.add(keyBoardBean);
            }
            //假设需要按照点赞数来进行排序效果
            Collections.sort(keyBoardBeenList, new CommentComparator());
            //设置adapter
            CommentAdapter adapter = new CommentAdapter(keyBoardBeenList);
            mListViewReply.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
