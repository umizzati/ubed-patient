package com.feka.ubed_patient.fragment.main_activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RatingBar;

import com.feka.ubed_patient.BaseApplication;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.activity.BaseActivity;
import com.feka.ubed_patient.adapter.ReviewAdapter;
import com.feka.ubed_patient.model.Review;
import com.feka.ubed_patient.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ReviewFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<Review> mReviewList = new ArrayList<>();
    private ReviewAdapter mReviewAdapter;
    FloatingActionButton reviewAddBtn;
    User mCurrentUser;
    AlertDialog mDialogReview;

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
        ListView mListView = v.findViewById(R.id.review_listview);
        reviewAddBtn = v.findViewById(R.id.review_add_btn);
        mReviewAdapter = new ReviewAdapter(getContext(), mReviewList);
        mListView.setAdapter(mReviewAdapter);

        reviewAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateForm();
            }
        });

        Query query;
        if (mCurrentUser.isAdmin()){
            query = BaseApplication.fireStoreDB.collection("reviews");
        }else{
            query = BaseApplication.fireStoreDB.collection("reviews").whereEqualTo("user_id", mCurrentUser.getUser_id());
        }

        refreshReview(query);
        updateReviewList(query);
        return v;
    }

    private void initiateForm() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.form_feedback, null);
        final RatingBar ratingBar = dialogView.findViewById(R.id.feedback_rating_bar);
        final EditText editText = dialogView.findViewById(R.id.feedback_review);
        final Button btn = dialogView.findViewById(R.id.feedback_btn);
        final FrameLayout progressBar = dialogView.findViewById(R.id.feedback_progressbar);
        dialogBuilder.setView(dialogView);

        //action
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                String myFormat = "EEE, dd MMMM yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Calendar myCalendar = Calendar.getInstance();
                Review review = new Review(
                        BaseActivity.user.getName(),
                        editText.getText().toString(),
                        ratingBar.getRating(),
                        sdf.format(myCalendar.getTime()));
                review.setUser_id(BaseActivity.user.getUser_id());
                addNewReview(review);
            }
        });

        // action view
        mDialogReview = dialogBuilder.create();
        mDialogReview.show();
    }

    private void addNewReview(Review review) {
        BaseApplication.fireStoreDB.collection("reviews")
                .add(review)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        mDialogReview.dismiss();
                        mListener.onSuccessFeedback();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mListener.onFailedFeedback();
                    }
                });
    }

    private void refreshReview(Query query) {
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(Objects.requireNonNull(task.getResult()).size() > 0){
                    mReviewList = (ArrayList<Review>) task.getResult().toObjects(Review.class);
                    mReviewAdapter.updateAdapter(mReviewList);
                }
//                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateReviewList(Query query){
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
        void onSuccessFeedback();
        void onFailedFeedback();
    }


}
