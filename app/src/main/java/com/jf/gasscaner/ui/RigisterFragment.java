package com.jf.gasscaner.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.haozi.baselibrary.utils.BitmapUtils;
import com.haozi.baselibrary.utils.SystemUtil;
import com.jf.gasscaner.R;
import com.jf.gasscaner.base.BaseDBFragment;
import com.jf.gasscaner.base.ScanCallBackIft;
import com.jf.gasscaner.databinding.FragmentTabRegisterBinding;
import com.jf.gasscaner.vm.RigisterFragmentVM;
import com.speedata.libid2.IDInfor;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by admin on 2017/8/9.
 */
public class RigisterFragment extends BaseDBFragment<FragmentTabRegisterBinding,RigisterFragmentVM> implements ScanCallBackIft {

    /**拍摄图片*/
    public static final int INPUT_CONTENT_TACKPIC = 1002;
    /**添加图片*/
    public static final int INPUT_CONTENT_ADDPIC = 1003;

    private ImageView img_takepic;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindLayout(inflater, R.layout.fragment_tab_register,container,new RigisterFragmentVM(this));
        initView();
        return mRootView;
    }

    private void initView() {
        img_takepic = mBinding.imgTakepic;
        mBinding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.scanResult(null);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //检查返回结果状态
        if(requestCode == INPUT_CONTENT_TACKPIC || requestCode == INPUT_CONTENT_ADDPIC ){
            if(resultCode == RESULT_OK){
                switch (requestCode) {
                    case INPUT_CONTENT_TACKPIC:
                        //得到图片
                        File pic = SystemUtil.getTakePicFile();
                        //viewModel.uploadHeaderImage(pic);
                        Bitmap bitmap = BitmapUtils.getScaleBitmap(pic.getPath(),800,600);
                        img_takepic.setImageBitmap(bitmap);
                        viewModel.uploadImage(pic);
                        break;
                    case INPUT_CONTENT_ADDPIC:
                        //得到图片
                        pic = SystemUtil.getAddPicFile(data);
                        //viewModel.uploadHeaderImage(pic);
                        bitmap = BitmapFactory.decodeFile(pic.getPath());
                        img_takepic.setImageBitmap(bitmap);
                        viewModel.uploadImage(pic);
                        break;
                }
            }else{
                return;
            }
        }
    }

    public void cleanPic(){
        img_takepic.setImageResource(R.mipmap.ic_takephoto);
    }

    @Override
    public void scanCallback(IDInfor idInfor) {
        viewModel.scanResult(idInfor);
    }
}
