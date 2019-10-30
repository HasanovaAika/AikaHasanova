package com.example.androidlabs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {

    ListView chatWindow;
    ArrayList<Message> messages = new ArrayList<>();
    ChatAdapter adapter;
    SQLiteDatabase db;
    EditText editChat;
    Button btnSend;
    Button btnReceive;
    Cursor results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab4_layout);

        editChat = (EditText) findViewById(R.id.chatRoomEditText);
        btnSend = findViewById(R.id.chatRoomSendButton);
        btnReceive = findViewById(R.id.chatRoomReceiveButton);
        chatWindow = findViewById(R.id.chatRoomList);
        adapter = new ChatAdapter(messages);
        chatWindow.setAdapter(adapter);

        btnSend.setOnClickListener(e -> {
            if (!(editChat.getText().toString().equals(""))) {
                String text = editChat.getText().toString();
                ContentValues newRowValue = new ContentValues();
                newRowValue.put(DatabaseHelper.COL_MESSAGE, text);
                newRowValue.put(DatabaseHelper.COL_ISSENT, true);
                long newID = db.insert(DatabaseHelper.TABLE_NAME, null, newRowValue);
                Message message = new Message(newID, text, true);
                messages.add(message);
                chatWindow.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                editChat.setText("");

                //messages.add(new Message(editChat.getText().toString(), true));
                //adapter.notifyDataSetChanged();
                //editChat.setText("");
            }
        });

        btnReceive.setOnClickListener(e -> {
            if (!(editChat.getText().toString().equals(""))) {
                String text = editChat.getText().toString();
                ContentValues newRowValue = new ContentValues();
                newRowValue.put(DatabaseHelper.COL_MESSAGE, text);
                newRowValue.put(DatabaseHelper.COL_ISSENT, false);
                long newID = db.insert(DatabaseHelper.TABLE_NAME, null, newRowValue);
                Message message = new Message(newID, text, false);
                messages.add(message);
                chatWindow.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                editChat.setText("");

                //messages.add(new Message(editChat.getText().toString(), false));
                //adapter.notifyDataSetChanged();
                //editChat.setText("");
            }
        });

        DatabaseHelper helper = new DatabaseHelper(this);
        db = helper.getWritableDatabase();

        String[] columns = {DatabaseHelper.COL_MESSAGEID, DatabaseHelper.COL_MESSAGE, DatabaseHelper.COL_ISSENT};
        results = db.query(false, DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        int messageID_colIndex = results.getColumnIndex(DatabaseHelper.COL_MESSAGEID);
        int message_colIndex = results.getColumnIndex(DatabaseHelper.COL_MESSAGE);
        int isSent_colIndex = results.getColumnIndex(DatabaseHelper.COL_ISSENT);

        while (results.moveToNext()) {
            long messageID = results.getLong(messageID_colIndex);
            String message = results.getString(message_colIndex);
            int isSent = results.getInt(isSent_colIndex);

            adapter.add(new Message(messageID, message, isSent == 1));
            adapter.notifyDataSetChanged();
        }
        printCursor(results);
    }
    public void printCursor(Cursor c) {

        Log.i("Database version ", db.getVersion() + "");
        Log.i("Number of columns ", results.getColumnCount() + "");
        Log.i("Name of columns ", Arrays.toString(results.getColumnNames()) + "");
        Log.i("Number of results ", results.getCount() + "");
        Log.i("Row results ", "");

        results.moveToFirst();

        for (int i = 0; i < results.getCount(); i++) {
            while (!results.isAfterLast()) {
                Log.i("MessageID", results.getString(results.getColumnIndex(DatabaseHelper.COL_MESSAGEID)) + "");
                Log.i("Message", results.getString(results.getColumnIndex(DatabaseHelper.COL_MESSAGE)) + "");
                Log.i("isSent", results.getString(results.getColumnIndex(DatabaseHelper.COL_ISSENT)) + "");
                results.moveToNext();

            }
        }

    }

    protected class ChatAdapter extends BaseAdapter {

        private ArrayList<Message> messages;

        protected ChatAdapter(ArrayList chatArray) {
            this.messages = chatArray;
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void add(Message message) {
            this.messages.add(message);
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            String message = messages.get(position).getMessage();
            boolean sent = messages.get(position).isSent();
            View chatView;

            if (sent) {
                chatView = inflater.inflate(R.layout.activity_lab4_send, parent, false);
            } else {
                chatView = inflater.inflate(R.layout.activity_lab4_receive, parent, false);
            }

            RelativeLayout layout = chatView.findViewById(R.id.layout);
            TextView text = (TextView) layout.getChildAt(1);
            text.setText(message);
            return layout;
        }
    }
}