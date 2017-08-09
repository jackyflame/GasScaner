package com.jf.gasscaner.base.vm;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.haozi.baselibrary.db.BasePresent;
import com.haozi.cqhl.BR;
import com.haozi.cqhl.base.adapter.BaseViewHolder;

/**
 * Created by Haozi on 2017/5/26.
 */

public abstract class BaseCommonditySwipListVM<T extends BasePresent,X extends RecyclerView.Adapter<BaseViewHolder>> extends BaseSwipListVM<T,X> {

    private boolean isFirstRowHeader;

    public BaseCommonditySwipListVM(Context context, T present) {
        super(context,present);
    }

    @Bindable
    public LinearLayoutManager getLayoutManager() {
        if(layoutManager == null){
            layoutManager = new GridLayoutManager(mContext,2);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            if(isFirstRowHeader){
                ((GridLayoutManager)layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if(position == 0){
                            return 2;
                        }else{
                            return 1;
                        }
                    }
                });
            }
        }
        return layoutManager;
    }

    public void setFirstRowHeader(boolean firstRowHeader) {
        isFirstRowHeader = firstRowHeader;
    }
}
