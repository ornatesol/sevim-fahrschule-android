package com.fahrschule.sevim.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.fahrschule.sevim.MainApplication;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.models.MessageItem;
import com.fahrschule.sevim.network.MessagesApi;
import com.fahrschule.sevim.network.RetrofitMessagesApi;
import com.fahrschule.sevim.utils.Utils;
import java.util.ArrayList;
import javax.inject.Inject;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MessagesListFragment extends BaseFragment {

    @BindView(R.id.rv_messages_list)
    protected RecyclerView recyclerView;

    @Inject
    MessagesApi messagesApi;

    private OnListFragmentInteractionListener listener;

    private Subscription loadMessagesSubscription;

    private ArrayList<MessageItem> messageList;

    MessageRecyclerViewAdapter adapter;

    private Dialog progressDialog;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public static MessagesListFragment newInstance() {
        return new MessagesListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).component().inject(this);
        messageList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        adapter = new MessageRecyclerViewAdapter(messageList, listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        loadMessages();
        setActionbarTitle();
    }

    private void setActionbarTitle() {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.messages);
        }
    }

    private void loadMessages() {
        showProgress(true);
        loadMessagesSubscription = messagesApi.getAllMessages(RetrofitMessagesApi.API_URL)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ArrayList<MessageItem>>() {
                            @Override
                            public void call(ArrayList<MessageItem> messageItems) {
                                showProgress(false);
                                adapter.swapItems(messageItems);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement "
                    + "OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroyView() {
        if(loadMessagesSubscription != null && !loadMessagesSubscription.isUnsubscribed()) {
            loadMessagesSubscription.unsubscribe();
        }
        super.onDestroyView();
    }

    public void showProgress(boolean show) {
        if (show) {
            if (progressDialog == null) {
                progressDialog = Utils.showProgressUpdateDialog(getActivity(),
                        getString(R.string.loading));
            }
        } else if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListItemClicked(MessageItem item);
        void onListItemLoaded(View view, MessageItem item);
    }
}
