package com.example.kazuaki.blewerewolf;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kazuaki on 2016/01/16.
 */
public class Frag extends Fragment {
    public Frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_chat,container,false);
    }

}
