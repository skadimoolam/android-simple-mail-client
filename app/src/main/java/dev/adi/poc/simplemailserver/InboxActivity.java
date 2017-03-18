package dev.adi.poc.simplemailserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.ason.Ason;
import com.afollestad.ason.AsonArray;
import com.afollestad.bridge.Form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import dev.adi.poc.simplemailserver.adapter.InboxListAdapter;
import dev.adi.poc.simplemailserver.helper.HttpHelper;
import dev.adi.poc.simplemailserver.helper.UrlHelper;
import dev.adi.poc.simplemailserver.model.MailItem;

public class InboxActivity extends AppCompatActivity {

    final String TAG = InboxActivity.class.getSimpleName();
    ProgressDialog progressDialog;
    SharedPreferences.Editor sharePrefEditor;

    List<MailItem> mailItems = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView inboxList;
    InboxListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        getSupportActionBar().setTitle("Inbox");
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        inboxList = (RecyclerView) findViewById(R.id.list_inbox);
        inboxList.setLayoutManager(new LinearLayoutManager(this));
        inboxList.setItemAnimator(new DefaultItemAnimator());

//        adapter = new InboxListAdapter(this, mailItems);
//        inboxList.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInboxData();
            }
        });
        sharePrefEditor = getSharedPreferences(MainActivity.PERF_NAME, MODE_PRIVATE).edit();

        getInboxData();
    }

    private void getInboxData() {
        if (HttpHelper.hasNetworkAccess(this)) {
            progressDialog = ProgressDialog.show(this, "Loading...", "Please wait!", false);

            Form formData = new Form();
            formData.add("user_id", getIntent().getStringExtra("user_id"));

            HttpHelper.postData(UrlHelper.url_inbox_view, formData, null, new HttpHelper.OnRequestCompleteListener() {
                @Override
                public void OnSuccess(Ason data) {
                    try {
                        AsonArray<MailItem> asonItems = new AsonArray<>(data.get("data").toString());
                        List<MailItem> items = asonItems.deserializeList(MailItem.class);
                        Collections.reverse(items);
                        inboxList.setAdapter(new InboxListAdapter(InboxActivity.this, items));
                    } catch (Exception e) {
                        findViewById(R.id.tv_prompt_no_email).setVisibility(View.VISIBLE);
                        inboxList.setVisibility(View.GONE);
                    }

                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void OnError(String error) {
                    Log.i(TAG, error);
                    Toast.makeText(InboxActivity.this, error, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            showToast("Cannot connect to the Internet");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_logout:
                attemptLogout();
                break;

            default:
//                showToast("Dont know what you clicked");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void attemptLogout() {
        sharePrefEditor.remove("user-data");
        sharePrefEditor.remove("user-id");
        sharePrefEditor.apply();

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
