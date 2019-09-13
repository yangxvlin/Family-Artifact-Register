package com.example.family_artifact_register;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class FriendActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ListView friends = findViewById(R.id.friend_list);
        friends.setAdapter(new MyAdapter());
    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Friends.friends.length;
        }

        @Override
        public String getItem(int position) {
            return Friends.friends[position];
        }

        @Override
        public long getItemId(int position) {
            return Friends.friends[position].hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.friend_list_item, container, false);
            }

            ((TextView) convertView.findViewById(R.id.username))
                    .setText(getItem(position));
            return convertView;
        }
    }

}
