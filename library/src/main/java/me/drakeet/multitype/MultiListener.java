package me.drakeet.multitype;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;

/**
 * Created by pool on 2016/8/15.
 *
 */
public class MultiListener {
    final HashMap<Class<? extends ItemContent>, int[]> mViewId;
    final MultiOnClickListener mOnClickListener;

    /**
     *@param viewId  根据 ItemContent 的类型设置点击事件, int[] 为对应 需要监听的ViewId,
     *               如果 id 找不到会被忽略掉.
     */
    public MultiListener(HashMap<Class<? extends ItemContent>, int[]> viewId, MultiOnClickListener onClickListener) {
        this.mViewId = viewId;
        this.mOnClickListener = onClickListener;
    }


    public interface MultiOnClickListener {
        void onClick(RecyclerView.ViewHolder viewHolder, View view);
    }

}
