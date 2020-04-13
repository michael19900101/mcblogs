package com.aotuman.studydemo.handler;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aotuman.studydemo.R;

/**
 * Handler demo 主界面
 */
public class HandlerMainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_handler_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.btn_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_handlerMainFragment_to_handlerMethod01Fragment);
            }
        });
        getView().findViewById(R.id.btn_02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_handlerMainFragment_to_handlerMethod02Fragment2);
            }
        });
        getView().findViewById(R.id.btn_03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_handlerMainFragment_to_handlerMethod03Fragment);
            }
        });
        getView().findViewById(R.id.btn_04).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_handlerMainFragment_to_handlerMethod04Fragment);
            }
        });
        getView().findViewById(R.id.btn_05).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_handlerMainFragment_to_handlerThreadFragment);
            }
        });
    }
}
