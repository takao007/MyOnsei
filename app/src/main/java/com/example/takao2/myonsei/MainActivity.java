package com.example.takao2.myonsei;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        // ボタンをクリックした時、音声認識を開始し
        // トーストに認識結果を表示する。
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // インテント作成です
                    Intent intent = new Intent(
                            RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                    intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                    // メッセージを設定。
                    intent.putExtra(
                            RecognizerIntent.EXTRA_PROMPT,
                            "お話ください");

                    // インテント発行
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException e) {
                    // このインテントに応答できるアクティビティがインストールされていない場合
                    Toast.makeText(MainActivity.this,
                            "音声認識は使用できません。", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // requestCode は startActivityForResult関数の第2引数の値
        if (requestCode == 0 && resultCode == RESULT_OK) {
            String msg = "";

            // 結果文字列リスト
            ArrayList<String> ret = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            for (int i = 0; i< ret.size(); i++) {
                // 音声認識の結果をトーストに表示
                msg += ret.get(i) + ", " ;
            }

            // トーストを使って結果を表示
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
