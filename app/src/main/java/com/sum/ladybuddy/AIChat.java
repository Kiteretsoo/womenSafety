package com.sum.ladybuddy;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AIChat extends AppCompatActivity{
    private EditText userInput;
    private Button askButton, clearButton;
    private TextView responseView;
    TextInputEditText txt_question;
@SuppressLint("MissingInflatedId")
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_ai_chat);

    txt_question = findViewById(R.id.txt_question);
    askButton = findViewById(R.id.ask_button);
    clearButton = findViewById(R.id.clear_button);
    responseView = findViewById(R.id.response_view);

    askButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String que = txt_question.getText().toString();
            if (!que.isEmpty()) {
                callApi(que);
            }
        }
    });

    clearButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            txt_question.setText("");
            responseView.setText("");
        }
    });
}

    private void callBot(String que) {
        String simulatedResponse = "AI Response to: " + que;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"message\": \""+ que +"\"\r\n}");
        Request request = new Request.Builder()
                .url("http://127.0.0.1:5000/chat")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            simulatedResponse = response.body().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        responseView.setText(simulatedResponse);
    }

    private void callApi(String que) {
        String url = "http://192.168.0.102:5000/chat"; // Use 10.0.2.2 for local API in emulator
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        // Create JSON body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("message", que);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert JSON to RequestBody
        RequestBody body = RequestBody.create( MediaType.parse("application/json; charset=utf-8"), jsonBody.toString());

        // Create Request
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // Execute API Call Asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(AIChat.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String jsonResponse = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String message = jsonObject.getString("response");

                        runOnUiThread(() -> responseView.setText(message));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(AIChat.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }

}
