package com.jf.gasscaner.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jf.gasscaner.R;
import com.jf.gasscaner.base.BaseDBFragment;
import com.jf.gasscaner.databinding.FragmentTabUserBinding;
import com.jf.gasscaner.vm.UserFragmentVM;

/**
 * Created by admin on 2017/8/9.
 */

public class UserFragment extends BaseDBFragment<FragmentTabUserBinding,UserFragmentVM> {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindLayout(inflater, R.layout.fragment_tab_user,container,new UserFragmentVM(getActivity()));
        initView();
        return mRootView;
    }

    private void initView() {

    }

}
