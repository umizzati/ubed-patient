package com.feka.ubed_patient.fragment.welcome_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.feka.ubed_patient.BaseApplication;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class LoginFragment extends Fragment {

    private loginListener listener;
    public interface loginListener{
        void onSuccessLogin(User user);
    }

    Button loginBtn;
    FrameLayout loginProgressbar;
    EditText email, password;
    String _email, _password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        loginBtn = v.findViewById(R.id.loginBtn);
        loginProgressbar = v.findViewById(R.id.loginProgressbar);
        email = v.findViewById(R.id.email);
        password = v.findViewById(R.id.password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run_progress();
                if (isValid()){
                    onLogin();
                }else
                    invalidMsg();
            }
        });

        return v;
    }

    private void onLogin(){
        Query query = BaseApplication.fireStoreDB.collection("users").whereEqualTo("email", _email).whereEqualTo("password", _password);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(Objects.requireNonNull(task.getResult()).size() > 0){
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    DocumentSnapshot documentSnapshot = documents.get(0);
                    String user_id = documentSnapshot.getId();
                    User user = documentSnapshot.toObject(User.class);
                    user.setUser_id(user_id);
                    listener.onSuccessLogin(user);
                }else
                    invalidMsg();
            }
        });

//        DocumentReference docRef = BaseApplication.fireStoreDB.collection("users").
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                }
//            }
//        });
    }

    public void run_progress() {
        loginBtn.setVisibility(View.GONE);
        loginProgressbar.setVisibility(View.VISIBLE);
    }

    public void stop_progress() {
        loginProgressbar.setVisibility(View.GONE);
        loginBtn.setVisibility(View.VISIBLE);
    }

    private boolean isValid() {
        _email = email.getText().toString();
        _password = password.getText().toString();

        if(_email.equals(""))
            return false;

        return !_password.equals("");
    }

    private void invalidMsg(){
        Toast.makeText(getActivity(), "Invalid email or password! ", Toast.LENGTH_SHORT).show();
        stop_progress();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof loginListener) {
            listener = (loginListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement LoginFragment.loginListener");
        }
    }


}
