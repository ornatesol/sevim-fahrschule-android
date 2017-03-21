package com.fahrschule.sevim.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.fahrschule.sevim.R;

import static com.fahrschule.sevim.utils.Utils.verifyArgument;

public class MainFragment extends BaseFragment {

    private static final String ARGS_TEXT_CONTENT = "args_text_content";

    @BindView(R.id.text_content)
    protected TextView content;

    private String textContent;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public static MainFragment newInstance(@NonNull String timingText) {
        final MainFragment frag = new MainFragment();

        final Bundle bundle = new Bundle(1);
        verifyArgument(!timingText.isEmpty());
        bundle.putString(ARGS_TEXT_CONTENT, timingText);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textContent = getString(R.string.generic_welcome_message);

        final Bundle arguments = getArguments();
        if(arguments != null) {
            if (arguments.getString(ARGS_TEXT_CONTENT) != null) {
                textContent = arguments.getString(ARGS_TEXT_CONTENT);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content.setText(textContent);
    }
}
