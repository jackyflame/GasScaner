package com.jf.gasscaner.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jf.gasscaner.R;
import com.jf.gasscaner.base.BaseDBFragment;
import com.jf.gasscaner.databinding.FragmentTabRegisterBinding;
import com.jf.gasscaner.vm.RigisterFragmentVM;

/**
 * Created by admin on 2017/8/9.
 */
public class RigisterFragment extends BaseDBFragment<FragmentTabRegisterBinding,RigisterFragmentVM> {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindLayout(inflater, R.layout.fragment_tab_register,container,new RigisterFragmentVM(getActivity()));
        initView();
        return mRootView;
    }

    private void initView() {

    }

}
