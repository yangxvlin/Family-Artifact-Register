package com.example.family_artifact_register.UI.Social;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FriendSearchActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println(query);
        }

        String[] result = new String[] {"Tim", "Matt", "Leon", "coffee", "xulin", "zhuoqun", "haichao", "1", "2", "3", "4"};;

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result));

        // TODO: default view has white text with white background, need to create a new custom view
        // TODO: implementation for search logic
    }

    public class myAdapter extends BaseAdapter {
        private String[] dataset;

        public myAdapter(String[] dataset) {
            this.dataset = dataset;
        }

        @Override
        public int getCount() {
            return dataset.length;
        }

        @Override
        public String getItem(int position) {
            return dataset[position];
        }

        @Override
        public long getItemId(int position) {
            return dataset[position].hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            ((TextView) convertView.findViewById(android.R.id.empty)).setText(getItem(position));
            return convertView;
        }
    }
}
