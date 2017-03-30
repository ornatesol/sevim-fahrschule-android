package com.fahrschule.sevim.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import butterknife.BindView;
import com.fahrschule.sevim.R;

public class MessageDetailFragment extends BaseFragment {

    private static final String ARGS_MESSAGE_DETAILS = "args_message_details";

    private String detailsTextToDisplay;

    @BindView(R.id.text_message_details)
    protected TextView content;

    public MessageDetailFragment() {
        // Required empty public constructor
    }

    public static MessageDetailFragment newInstance(@NonNull String detailsText) {
        final MessageDetailFragment frag = new MessageDetailFragment();

        final Bundle bundle = new Bundle(1);
        bundle.putString(ARGS_MESSAGE_DETAILS, detailsText);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            final Bundle arguments = getArguments();
            if (arguments != null) {
                if (arguments.getString(ARGS_MESSAGE_DETAILS) != null) {
                    detailsTextToDisplay = arguments.getString(ARGS_MESSAGE_DETAILS);
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content.setText(detailsTextToDisplay);
    }
}
