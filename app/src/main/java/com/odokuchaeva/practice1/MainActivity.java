package com.odokuchaeva.practice1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Константы для ключей сохранения состояния
    private static final String KEY_COUNTER = "counter";
    private static final String KEY_HISTORY = "history";

    private TextView counterValue;
    private ListView historyList;
    private int x = 0;
    private ArrayList<String> history = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counterValue = findViewById(R.id.counterValue);
        historyList = findViewById(R.id.historyList);
        if (savedInstanceState != null) {
            x = savedInstanceState.getInt(KEY_COUNTER, 0);
            history = savedInstanceState.getStringArrayList(KEY_HISTORY);
            if (history == null) {
                history = new ArrayList<>();
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);
        historyList.setAdapter(adapter);
        findViewById(R.id.plus).setOnClickListener(v -> change(1));
        findViewById(R.id.minus).setOnClickListener(v -> change(-1));
        findViewById(R.id.zero).setOnClickListener(v -> reset());
        update();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNTER, x);
        outState.putStringArrayList(KEY_HISTORY, history);
    }

    private void change(int n) {
        int y = x;
        x += n;
        addHistory("Изменено", y, x);
        update();
    }

    private void reset() {
        int y = x;
        x = 0;
        addHistory("Сброшено", y, x);
        update();
    }

    private void update() {
        counterValue.setText(String.valueOf(x));
    }

    private void addHistory(String operation, int y, int x) {
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String entry = time + " - " + operation + " с " + y + " до " + x;
        history.add(0, entry);
        adapter.notifyDataSetChanged();
    }
}