package com.jf.gasscaner.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jf.gasscaner.R;
import com.jf.gasscaner.base.BaseDBFragment;
import com.jf.gasscaner.base.ScanCallBackIft;
import com.jf.gasscaner.databinding.FragmentTabCheckBinding;
import com.jf.gasscaner.vm.CheckFragmentVM;
import com.speedata.libid2.IDInfor;

/**
 * Created by admin on 2017/8/9.
 */

public class CheckFragment extends BaseDBFragment<FragmentTabCheckBinding,CheckFragmentVM> implements ScanCallBackIft {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindLayout(inflater, R.layout.fragment_tab_check,container,new CheckFragmentVM(this));
        initView();
        return mRootView;
    }

    private void initView() {
        mBinding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.scanReslt();
            }
        });
    }

    @Override
    public void scanCallback(IDInfor idInfor) {

    }
}
