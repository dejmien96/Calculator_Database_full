package com.janicki.damian.calc;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity {
    private TextView resultView;  //okienko z wynikieme
    private String result;  //zmmienna przechowujaca string w ktorym jest nasze dzialanie do policzenia

    private DbHelper database = new DbHelper(this);  //polaczenie do bazy danych



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();  //wywolanie funkcji inicjalizujacej zmienne
    }

    @Override
    protected void onDestroy() { //to sie wywoluje podczas zamykania aplikacji
        super.onDestroy();  // wywolanie funkcji onDestroy z klasy nadrzędnej, czyli z tej po ktorej dziedziczymy, w naszym przypadku AppCompatActivity
        database.close();  //zamkniecie polaczenia do bazy danych
    }

    private void init() {
        resultView = findViewById(R.id.wynik);  //przypisanie zmiennej do naszego widoku wyniku (okienka z wynikiem)
        result = "";  //ustawiamy jako puste dzialanie
    }

    protected void onClickButton(View v){  //to sie wywoluje po nacisnieciu przycisku z cyfra badz znakiem + - / * lub znakiem C czylli kasowanie. W XMLu activity_history.xml te przyciski maja ustawione:   android:onClick="onClickButton"

        Button clicked = (Button) v;  //zmienna przechowujaca nasz klikniety przycisk

        String operator = clicked.getText().toString();  //pobieramy znak z przycisku

            if(operator.equals("C")) {  //jesli znak to C
                result = ""; //czyscimy nasze dzialanie
            }
            else{
                result += operator;  //w przeciwnym wypadku do naszego dzialania dopisujemy znak z przycisku
            }

            resultView.setText(result);  //pokazujemy dzialanie w okienku z dzialaniem
    }

    protected void onClickPoint(View v){  //po kliknieciu przecinka
        if(result != "") {  //jesli dzialanie nie jest puste to wstawiamy kropke (musi byc kropka a nie przecinek, bo inaczej biblioteka dlo liczenia wywali blad)
            result += ".";
            resultView.setText(result);
        }
    }


    protected void onClickEqual(View v){  //po kliknieciu rowna sie
        if(result != "") { //jesli dzialanie nie jest puste

           try{
               Expression expression = new ExpressionBuilder(result).build();  //tutaj tworzymy obiekt z zewnetrznej biblioteki exp4j, ktora za nasz liczy dzialanie w stringu
               double calculated = expression.evaluate();  // obliczamy
               resultView.setText(formatDoubleToString(calculated));  //tutaj w patametrze jest funkcja ktora formatuje nasz wynik do ostatniej cyfry znaczacej. Czyli zeby nie bylo 12.8000000, tylko po samo 12
               database.addValue(concatWithEquealitySign(result,calculated));  //dodajemy do bazy, funkcja concat laczy nasze dzialanie z wynikiem wstawiajac pomiedzy znak rowna sie

               result = "";  //po wykonaniu obliczen czyscimy nasze dzialanie
           }
            catch(Exception e){
                Toasty.error(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT, true).show();  //jesli obliczanie sie nie powiedzie to wyswetlamy toast, jest tutaj uzyta zewnetrzna bibioteka Toasty, dzieki czemy nasze ostrzezenie jest w czerwonym kolorze
            }
        }
    }

    public String concatWithEquealitySign(String result, double calculated) {  //funkcja wstawia znak rowna sie pomiedzy dzialanie i wynik
        return result + " = " + calculated;
    }

    protected void onClickHistory(View v){  //otwieramy nowe activity, czyli ekran z historia
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
    protected void onClickBack(View v){  //po kliknieciu strzalki w lewo kasujemy ostatni znak z dzialania
        result = deleteLastChar(result);
        resultView.setText(result);
    }

     public String deleteLastChar(String string) {   //funkcaj kasujaca ostatni znak ze stringa
        if (string != null) {
            if(string.length() > 0){
                string = string.substring(0, string.length() - 1);
            }
        }
        return string;
    }


     public String formatDoubleToString(double d){  //funkcja formatujaca double na stringa
        if(d == (long) d) {
            return String.format("%d", (long) d);
        }
        else {
            return String.format("%s", d);
        }
    }
}
