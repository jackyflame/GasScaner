package com.jf.gasscaner.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
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
public class RigisterFragment extends BaseDBFragment<FragmentTabRegisterBinding, RigisterFragmentVM> implements ScanCallBackIft {

    /**
     * 拍摄图片
     */
    public static final int INPUT_CONTENT_TACKPIC = 1002;
    /**
     * 添加图片
     */
    public static final int INPUT_CONTENT_ADDPIC = 1003;

    private ImageView img_takepic;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindLayout(inflater, R.layout.fragment_tab_register, container, new RigisterFragmentVM(this));
        initView();
        return mRootView;
    }

    private void initView() {
        img_takepic = mBinding.imgTakepic;
        mBinding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IDInfor idInforNew = new IDInfor();
                idInforNew.setName("张三");
                idInforNew.setNum("510703197909151118");
                idInforNew.setSex("男");
                idInforNew.setNation("汉族");
                idInforNew.setAddress("四川省成都市成华区将军路"+System.currentTimeMillis()%1000+"号");
                idInforNew.setYear("1988");
                idInforNew.setMonth("05");
                idInforNew.setDay("05");
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                idInforNew.setBmps(bmp);
                viewModel.scanResult(idInforNew);
            }
        });
        mBinding.edtJyl.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int length = dest.toString().substring(index).length();
                    if (length == 3) {
                        return "";
                    }
                }
                return null;
            }
        }});
        mBinding.txvAddress.setSaveEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //检查返回结果状态
        if (requestCode == INPUT_CONTENT_TACKPIC || requestCode == INPUT_CONTENT_ADDPIC) {
            //返回图片处理方式
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case INPUT_CONTENT_TACKPIC:
                        //得到图片
                        File pic = SystemUtil.getTakePicFile();
                        //viewModel.uploadHeaderImage(pic);
                        Bitmap bitmap = BitmapUtils.getScaleBitmap(pic.getPath(), 800, 600);
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
            }
            //调用摄像头以后重新初始化
            if (getActivity() instanceof ScanActivity) {
                ((ScanActivity) getActivity()).initID(true);
            }
        }
    }

    public void cleanPic() {
        img_takepic.setImageResource(R.mipmap.ic_takephoto);
    }

    public void cleanAddress(){
        mBinding.txvAddress.setText("123");
        mBinding.txvAddress.invalidate();
    }

    @Override
    public void scanCallback(IDInfor idInfor) {
        viewModel.scanResult(idInfor);
    }
}
