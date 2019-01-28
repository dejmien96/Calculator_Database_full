package com.janicki.damian.calc;

import android.provider.BaseColumns;

public final class DbContract {  //klasa z naszymi stalymi nazwami do bazy danych, jest final czyli nie mozemy z niej dziedziczyc
    private DbContract(){}  //prywatny konstruktor zebysmy nie mogli utworzyc obiektu tej klasy

    public static class FeedHistory implements BaseColumns {  //wszystkie pola sa statyczne zebysmy mogli z nich korzystac bez tworzenia obiektu tej kasly
       //są final czyli nie mozemy ich zmieniac
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_DATA = "data";
        public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
        public static final String DESCRIPTION_OPTIONS = "TEXT NOT NULL";
    }

}
