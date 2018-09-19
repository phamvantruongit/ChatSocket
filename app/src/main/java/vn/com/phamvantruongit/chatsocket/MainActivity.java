package vn.com.phamvantruongit.chatsocket;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.socketio.client.Socket;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private EditText edNickName;
    //https://medium.com/@mohamedaymen.ourabi11/creating-a-realtime-chat-app-with-android-nodejs-and-socket-io-1050bc20c70
    //https://github.com/nkzawa/socket.io-android-chat
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn);
        edNickName=findViewById(R.id.edNickName);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edNickName.getText().toString().trim().isEmpty()){
                    Intent i=new Intent(MainActivity.this,ChatBoxActivity.class);
                    i.putExtra("nickname",edNickName.getText().toString());
                    startActivity(i);
                }
            }
        });
    }

}
