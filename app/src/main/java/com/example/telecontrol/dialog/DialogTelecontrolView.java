package com.example.telecontrol.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.telecontrol.R;
import com.example.telecontrol.view.ControlView;

import java.lang.ref.SoftReference;

public class DialogTelecontrolView extends DialogFragment implements View.OnClickListener {

    EditText name;
    EditText topic;
    Button save;
    ControlView view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onButtonClick = (OnButtonClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onDialogClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        getDialog().setTitle("Орган управления");
        view = inflater.inflate(R.layout.dialog_telecontrol_view, null);
        name = view.findViewById(R.id.name_obj);
        topic = view.findViewById(R.id.name_topic);
        save = view.findViewById(R.id.save);
        save.setOnClickListener(this);
        return view;
    }

    public void setControlView(ControlView viewRef){
        this.view = viewRef;
    }

    @Override
    public void onClick(View v) {
        this.view.setName(name.getText().toString());
        this.view.setTopic(topic.getText().toString());
        onButtonClick.onDialogClickListener(view.getName(), view.getTopic());
        dismiss();
    }

    public String getName(){
        return this.view.getName();
    }

    public String getTopic(){
        return this.view.getTopic();
    }

    public interface OnButtonClick{
        void onDialogClickListener(String name, String topic);
    }
    OnButtonClick onButtonClick;

    @Override
    public void onDetach() {
        super.onDetach();
        onButtonClick = null;
    }
}
