package vn.com.phamvantruongit.chatsocket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ChatBoxActivity extends AppCompatActivity {
    private com.github.nkzawa.socketio.client.Socket socket;
    private String nickName;
    RecyclerView rvMessage;
    ChatBoxAdapter chatBoxAdapter;
    EditText edMessage;
    Button btnSend;
    List<Message> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox);
        nickName=getIntent().getExtras().getString("nickname");
        try {
            socket= IO.socket("");
            socket.connect();
            socket.emit("join",nickName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.on("userjoinedthechat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data= (String) args[0];
                        Toast.makeText(ChatBoxActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        init();
    }

    private void init() {
        list=new ArrayList<>();

        rvMessage=findViewById(R.id.rvMessage);
        edMessage=findViewById(R.id.edMessage);
        btnSend=findViewById(R.id.btnSend);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        rvMessage.setLayoutManager(layoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edMessage.getText().toString().isEmpty()){
                    socket.emit("messagedetection",nickName,edMessage.getText().toString());
                    edMessage.setText("");
                }
            }
        });

        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       JSONObject data= (JSONObject) args[0];
                       try {
                           String nickname=data.getString("senderNickname");
                           String message=data.getString("message");
                           Message m=new Message(nickname,message);
                           list.add(m);
                           chatBoxAdapter=new ChatBoxAdapter(list);
                           rvMessage.setAdapter(chatBoxAdapter);
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               });
            }
        });

        socket.on("userdisconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data= (String) args[0];
                        Toast.makeText(ChatBoxActivity.this, data , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        socket.disconnect();
    }
}
