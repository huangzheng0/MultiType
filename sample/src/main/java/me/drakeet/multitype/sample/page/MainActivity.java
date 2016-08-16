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

package me.drakeet.multitype.sample.page;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.drakeet.multitype.ItemContent;
import me.drakeet.multitype.MultiListener;
import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.TypeItem;
import me.drakeet.multitype.TypeItemFactory;
import me.drakeet.multitype.sample.ImageItemContent;
import me.drakeet.multitype.sample.R;
import me.drakeet.multitype.sample.RichItemContent;
import me.drakeet.multitype.sample.TextItemContent;
import me.drakeet.multitype.sample.util.ToastUtil;

public class MainActivity extends AppCompatActivity implements MultiListener.MultiOnClickListener {

    private TypeItemFactory factory;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.list);

        factory = new TypeItemFactory.Builder().build();
        TypeItem textItem = factory.newItem(new TextItemContent("world"));
        TypeItem imageItem = factory.newItem(new ImageItemContent(R.mipmap.ic_launcher));
        TypeItem richItem = factory.newItem(new RichItemContent("小艾大人赛高", R.mipmap.avatar));

        List<TypeItem> typeItems = new ArrayList<>(80);
        for (int i = 0; i < 20; i++) {
            typeItems.add(textItem);
            typeItems.add(imageItem);
            typeItems.add(richItem);
        }
        MultiTypeAdapter adapter = new MultiTypeAdapter(recyclerView, typeItems);
        HashMap<Class<? extends ItemContent>, int[]> listener = new HashMap<>();
        listener.put(TextItemContent.class, new int[]{R.id.text});
        listener.put(ImageItemContent.class, new int[]{R.id.image});
        listener.put(RichItemContent.class, new int[]{R.id.text, R.id.image});
        adapter.setMultiListener(new MultiListener(listener, this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(RecyclerView.ViewHolder viewHolder, View view) {
        ToastUtil.showToast(getApplicationContext(),
                String.format("第 %d item %s 被点击了", viewHolder.getAdapterPosition(), view.toString()));
    }

}
