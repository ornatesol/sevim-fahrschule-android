package com.fahrschule.sevim.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fahrschule.sevim.MainApplication;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.adapters.CalendarListAdapter;
import com.fahrschule.sevim.models.MessageItem;
import com.fahrschule.sevim.network.MessagesApi;
import com.fahrschule.sevim.network.RetrofitMessagesApi;
import com.fahrschule.sevim.utils.Utils;

import java.util.ArrayList;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CalendarActivity extends AppCompatActivity implements CalendarListAdapter.OnCalendarItemClicked {

    private static final int RC_CALENDAR_WRITE_PERM = 124;

    public static final String CALENDAR_TYPE = "calendar.type";
    public static final String IS_WEDDING = "is.wedding";

    private ArrayList<MessageItem> messageList;
    private CalendarListAdapter calendarListAdapter;


    @Inject
    MessagesApi messagesApi;
    private Dialog progressDialog;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.title)
    TextView title;

    private MessageItem messageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);
        MainApplication.get(this).component().inject(this);

        initUi();

        if (Utils.isOnline(this)) {
            processBundleData();
        } else {
            Utils.showConnectionErrorDialog(this,
                    getString(R.string.error_no_connection_data_is_old));
        }
    }

    private void processBundleData() {
        Bundle bundle = getIntent().getExtras();
        String type;
        Boolean isWedding;

        showProgress(true);
        if (bundle != null) {
            type = getIntent().getExtras().getString(CALENDAR_TYPE);
            isWedding = getIntent().getExtras().getBoolean(IS_WEDDING);
            if (isWedding) {
                title.setText(R.string.wedding_title);
            } else {
                title.setText(R.string.reinickendorf_title);
            }
            if (type.contains(getString(R.string.dutch))) {
                if (isWedding) {
                    loadCalendarData(RetrofitMessagesApi.API_URL_WEDDING_DE);
                } else {
                    loadCalendarData(RetrofitMessagesApi.API_URL_REICKENDORF_DE);
                }
            } else if (type.contains(getString(R.string.turkish))) {
                if (isWedding) {
                    loadCalendarData(RetrofitMessagesApi.API_URL_WEDDING_TR);
                } else {
                    loadCalendarData(RetrofitMessagesApi.API_URL_REICKENDORF_TR);
                }

            }
        }
    }

    private void initUi() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        messageList = new ArrayList<MessageItem>();
        calendarListAdapter = new CalendarListAdapter(this, this, messageList);
        recyclerView.setAdapter(calendarListAdapter);
    }

    private void loadCalendarData(String url) {
        messagesApi.getAllMessages(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ArrayList<MessageItem>>() {
                               @Override
                               public void call(ArrayList<MessageItem> messageItems) {
                                   showProgress(false);
                                   calendarListAdapter.swapItems(messageItems);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   showProgress(false);
                                   throwable.printStackTrace();
                               }
                           }
                );
    }


    public void showProgress(boolean show) {
        if (show) {
            if (progressDialog == null) {
                progressDialog = Utils.showProgressUpdateDialog(this,
                        getString(R.string.loading));
            }
        } else if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void selectedItem(MessageItem messageItem) {
        this.messageItem = messageItem;
        checkCalendarPermission();
    }


    @AfterPermissionGranted(RC_CALENDAR_WRITE_PERM)
    public void checkCalendarPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_CALENDAR)) {
            // Have permission, do the thing!
            showAlert();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.permission_text),
                    RC_CALENDAR_WRITE_PERM,
                    Manifest.permission.WRITE_CALENDAR);
        }
    }


    private void addToCalender() {
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.DTSTART, messageItem.getStartDate().getTime());
        values.put(CalendarContract.Events.DTEND, messageItem.getEndDate().getTime());
        values.put(CalendarContract.Events.DESCRIPTION, messageItem.getDetail());
        values.put(CalendarContract.Events.TITLE, messageItem.getDetail());
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        TimeZone timeZone = TimeZone.getDefault();
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        Toast.makeText(this, "Ereign erfolgreich erstellt", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void showAlert() {
        AlertDialog.Builder b = new AlertDialog.Builder(this)
                .setTitle(R.string.add_to_calendar)
                .setPositiveButton(R.string.ja,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                addToCalender();
                            }
                        }
                )
                .setNegativeButton(R.string.Stornieren,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );
        b.show();

    }
}
