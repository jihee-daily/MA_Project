package kr.ac.jbnu.se.danim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

public class HikedialogFragment extends androidx.fragment.app.DialogFragment implements View.OnClickListener {
    private Fragment fragment;
    public HikedialogFragment(){
    }
    public static UserdataActivity getInstance(){
        UserdataActivity e = new UserdataActivity();
        return e;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_hikedialog, container, false);
        Button btn_okay = (Button) view.findViewById(R.id.okBtn);
        btn_okay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        // Bundle args = getArguments();
        // String value = args.getString("key"); //정보 전달받음

        //  fragment = getActivity().getSupportFragmentManager().findFragmentByTag("tag");
        //  if(fragment != null){
        //        androidx.fragment.app.DialogFragment dialogFragment = (androidx.fragment.app.DialogFragment) fragment;
        //      dialogFragment.dismiss();
        //  }
        Button btn_cancel = (Button) view.findViewById(R.id.cancelBtn);
        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dismiss();
            }
        });
        return view;
    }
    @Override
    public void onClick(View v){}
}
