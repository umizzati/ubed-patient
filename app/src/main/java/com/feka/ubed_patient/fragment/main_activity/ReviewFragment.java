package com.feka.ubed_patient.fragment.main_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.feka.ubed_patient.BaseApplication;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.activity.BaseActivity;
import com.feka.ubed_patient.adapter.ReviewAdapter;
import com.feka.ubed_patient.model.Review;
import com.feka.ubed_patient.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ReviewFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<Review> mReviewList = new ArrayList<>();
    private ReviewAdapter mReviewAdapter;
    private User mCurrentUser;
    private ListView mListView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = BaseActivity.user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review, container, false);
        mListView = v.findViewById(R.id.review_listview);
        swipeRefreshLayout = v.findViewById(R.id.reviewSwipeRefreshLayout);

        mReviewAdapter = new ReviewAdapter(getContext(), mReviewList);
        mListView.setAdapter(mReviewAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshReview();
            }
        });

        refreshReview();
        updateReviewList();
        return v;
    }

    private void refreshReview() {
        Query query = BaseApplication.fireStoreDB.collection("reviews");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(Objects.requireNonNull(task.getResult()).size() > 0){
                    mReviewList = (ArrayList<Review>) task.getResult().toObjects(Review.class);
                    mReviewAdapter.updateAdapter(mReviewList);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateReviewList(){
        final Query query = BaseApplication.fireStoreDB.collection("reviews");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null) {
                    mReviewList = (ArrayList<Review>) snapshot.toObjects(Review.class);
                    mReviewAdapter.updateAdapter(mReviewList);
                }
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
    }


}
