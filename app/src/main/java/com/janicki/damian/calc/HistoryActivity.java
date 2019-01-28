package com.janicki.damian.calc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private ArrayList<String> historyList;   //lista naszych dzialan
    private DbHelper database = new DbHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        readFromDatabase();  //funkcja wczytujaca dane z bazy
        init();  //to samo co w MainActivity
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }

    private void init() {
        ListView listViewHistory = findViewById(R.id.listView);  //widok listy
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(buttonBackOnClickListener);

        ArrayAdapter<String> equationAdapter = new ArrayAdapter<>(this, R.layout.row, historyList); //tworzymy adapter listy

        listViewHistory.setAdapter(equationAdapter); //ustawiamy nasz adapter do widoku listy
    }

    private void readFromDatabase() {
        historyList = database.getAllToArray();  //pobieramy z bazy liste wszystkich dzialan
    }

    private View.OnClickListener buttonBackOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {  //po kliknieciu przycisku back
            buttonBackClicked();
        }
    };

    private void buttonBackClicked() {  //otwieramy MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK,intent);
        finish();
    }

}
