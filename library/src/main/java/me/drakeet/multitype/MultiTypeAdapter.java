/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.drakeet.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author drakeet
 */
public class MultiTypeAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<? extends TypeItem> typeItems;

    private LayoutInflater inflater;

    private RecyclerView mRecycleView;

    private MultiListener mMultiListener;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mMultiListener != null) {
                ViewHolder viewHolder = findItemViewHolder(view);
                mMultiListener.mOnClickListener.onClick(viewHolder, view);
            }
        }
    };

    public MultiTypeAdapter(RecyclerView recycleView, @NonNull List<? extends TypeItem> typeItems) {
        this.typeItems = typeItems;
        mRecycleView = recycleView;
    }


    @Override
    public int getItemViewType(int position) {
        ItemContent content = typeItems.get(position).content;
        return MultiTypePool.getContents().indexOf(content.getClass());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int indexViewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ViewHolder viewHolder = MultiTypePool.getProviderByIndex(indexViewType).onCreateViewHolder(inflater, parent);
        if (mMultiListener != null)
            initListener(viewHolder.itemView, indexViewType);
        return viewHolder;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = getItemViewType(position);
        TypeItem typeItem = typeItems.get(position);
        MultiTypePool.getProviderByIndex(type).onBindViewHolder(holder, typeItem);
    }


    private void initListener(View itemRoot, int viewType) {
        Class<? extends ItemContent> itemContentClass = MultiTypePool.getContents().get(viewType);
        int[] ids = mMultiListener.mViewId.get(itemContentClass);
        View v;
        for (int i = 0; i < ids.length; i++) {
            v = itemRoot.findViewById(ids[i]);
            if (v != null) v.setOnClickListener(mOnClickListener);
        }
    }

    public void setMultiListener(MultiListener multiListener) {
        mMultiListener = multiListener;
    }

    @Override
    public int getItemCount() {
        return typeItems.size();
    }

    private ViewHolder findItemViewHolder(View itemChildView) {
        ViewGroup parent = (ViewGroup) itemChildView.getParent();
        if (parent == mRecycleView)
            return mRecycleView.getChildViewHolder(itemChildView);
        else
            return findItemViewHolder(parent);
    }

}