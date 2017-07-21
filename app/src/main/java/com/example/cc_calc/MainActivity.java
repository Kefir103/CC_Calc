package com.example.cc_calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    protected Button[] buttons = new Button[16];//Массив кнопочек, чтоб можно было объединять в группы и спокойно вырубать ненужные при изменении СС входа
    protected int[] buttons_id = {R.id.numberButton_0, R.id.numberButton_1, R.id.numberButton_2,
            R.id.numberButton_3, R.id.numberButton_4, R.id.numberButton_5, R.id.numberButton_6,
            R.id.numberButton_7, R.id.numberButton_8, R.id.numberButton_9, R.id.numberButton_10,
            R.id.numberButton_11, R.id.numberButton_12, R.id.numberButton_13, R.id.numberButton_14,
            R.id.numberButton_15};//Массив id кнопочек, чтоб по кайфу тоже было и было легче все дела делать с определением кнопок
    public TextView textViewNumber, textViewResult;//Текстовые поля ввода числа и вывода результата
    protected Button resultButton, clearButton;//Кнопочки вывода результата и очищения всего, что движется и не движется
    protected String stringNumber = "";//Спорный момент, так как если мы ставим null,
    // то и с методом isEmpty(); не работает и выводит в textViewNumber первым значением null
    protected String resultString = "";//Решил не париться и сделал так же как и со stringNumber. Люблю костыли <3
    protected  int number = 0;//Первоначальное число
    protected int numberPositionOut = 0;//Выходная система счисления
    protected int numberPositionIn = 0;//Первоначальная тип система счисления
    protected Integer[] chosenCC = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};//Массив СС, чтоб все по кайфу было (пока пишу комментарий не могу
    //вспомнитиь почему же я все-таки остановился на этом, а не на массиве строк в strings.xml
    public Spinner spinnerIn, spinnerOut;//Собственно спиннеры (не те, которые ты крутишь, маленький модник(хотя в принципе прикольная тема))
    private static final String ResultTAG = "ResultTAG";//Для ЛогКошки
    private static final String ButtonsTAG = "ButtonsTAG";//Для ЛогКошки
    boolean isMoreThan10 = true;



    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewNumber = (TextView) findViewById(R.id.textViewNumber);
        textViewResult = (TextView) findViewById(R.id.textViewResult);


        resultButton = (Button) findViewById(R.id.resultButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        for (int i = 0; i < buttons.length; i++) {//Цикл определения кнопочек и установки листенера
            //Посмотри как тут красиво написано
            buttons[i] = (Button) findViewById(buttons_id[i]);
            buttons[i].setOnClickListener(buttonsInOnClickListener);
        }
        resultButton.setOnClickListener(buttonsInOnClickListener);
        clearButton.setOnClickListener(buttonsInOnClickListener);

        /*
        А вот тут немного мне конечно было непонятно, так как до адаптеров я не дошел еще в своем обучении, поэтому
        как люди писали так и я написал :)
         */
        spinnerIn = (Spinner) findViewById(R.id.startCC);
        spinnerOut = (Spinner) findViewById(R.id.endCC);
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, chosenCC);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIn.setAdapter(spinnerAdapter);
        spinnerOut.setAdapter(spinnerAdapter);
        spinnerIn.setSelection(8);
        spinnerOut.setSelection(0);
        spinnerIn.setOnItemSelectedListener(spinnerInOnItemSelectedListener);
        spinnerOut.setOnItemSelectedListener(spinnerOutOnItemSelectedListener);




    }
    /*
    Добавление листенера, который отвечает за первоначальную систему счисления числа
    */
    OnItemSelectedListener spinnerInOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            for (int i = 0; i < buttons_id.length; i++) {
                buttons[i].setEnabled(false);
            }
            for (int i = 0; i < position+2; i++) {
                buttons[i].setEnabled(true);
            }
            stringNumber = "";
            numberPositionIn = position + 2;
            textViewNumber.setText(textViewNumber.getHint());
            textViewNumber.setTextColor(textViewNumber.getHintTextColors());
        }

        @Override
        public void onNothingSelected (AdapterView<?> parent) {

        }
    };

    /*
    Добавление листенера на спиннер, который отвечает за конечную систему счисления
    Кстати при изменении СС выхода, еще и результат меняется
    Красиво? :3
    */
    OnItemSelectedListener spinnerOutOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            resultString = "";
            numberPositionOut = position + 2;
            searchResult(numberPositionOut, numberPositionIn);
            textViewResult.setText(resultString);
        }

        @Override
        public void onNothingSelected (AdapterView<?> parent) {

        }
    };



    /*
    Добавление листенера на кнопки, при нажатии на кнопку результат записывается в текстовое поле ввода числа и изменяется целочисленное значение,
    которое будет изменяться
    */
    View.OnClickListener  buttonsInOnClickListener = new OnClickListener() {
        @Override
        public void onClick (View v) {
            switch (v.getId()) {
                case R.id.numberButton_0:
                    number *= 10;//Для увеличения числа на 1 разряд (важно, чтоб стояло до изменения младшего разряда, иначе в конечном результате будет вычисляться
                                //число не 8 допустим, а 80
                    number += 0;//Изменение младшего разряда
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));//Вывод в лог текущего значения числа
                    if (stringNumber != "") {//Проверка на то, может ли наше число начинаться с 0 в поле ввода
                        stringNumber += "0";//Изменение числа для вывода в текстовое поле
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));//Спорный момент
                        textViewNumber.setText(stringNumber);//Обновление поля ввода числа
                    }
                    break;
                case R.id.numberButton_1://Все то же самое, что и с R.id.numberButton_0
                    number *= 10;
                    number += 1;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {//Чтоб норм вводилось число
                        stringNumber = "1";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "1";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_2://Все то же самое, что и с R.id.numberButton_0
                    number *= 10;
                    number += 2;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "2";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "2";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_3://Все то же самое, что и с R.id.numberButton_0
                    number *= 10;
                    number += 3;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "3";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "3";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_4://Все то же самое, что и с R.id.numberButton_0
                    number *= 10;
                    number += 4;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "4";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "4";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_5://Все то же самое, что и с R.id.numberButton_0
                    number *= 10;
                    number += 5;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "5";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "5";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_6://Все то же самое, что и с R.id.numberButton_0
                    number *= 10;
                    number += 6;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "6";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "6";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_7://Все то же самое, что и с R.id.numberButton_0
                    number *= 10;
                    number += 7;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "7";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "7";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_8://Все то же самое, что и с R.id.numberButton_0
                    number *= 10;
                    number += 8;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "8";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "8";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_9://Все то же самое, что и с R.id.numberButton_0
                    number *= 10;
                    number += 9;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "9";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "9";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_10://Все то же самое, что и с R.id.numberButton_0, за исключением того, что теперь двухзначные числа вводятся
                    number *= 100;
                    number += 10;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "A";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "A";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_11://Все то же самое, что и с R.id.numberButton_0, за исключением того, что теперь двухзначные числа вводятся
                    number *= 100;
                    number += 11;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "B";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "B";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_12://Все то же самое, что и с R.id.numberButton_0, за исключением того, что теперь двухзначные числа вводятся
                    number *= 100;
                    number += 12;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "C";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "C";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_13://Все то же самое, что и с R.id.numberButton_0, за исключением того, что теперь двухзначные числа вводятся
                    number *= 100;
                    number += 13;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));
                    if (stringNumber == "") {
                        stringNumber = "D";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "D";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_14://Все то же самое, что и с R.id.numberButton_0, за исключением того, что теперь двухзначные числа вводятся
                    number *= 100;
                    number += 14;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));

                    if (stringNumber == "") {
                        stringNumber = "E";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "E";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.numberButton_15://Все то же самое, что и с R.id.numberButton_0, за исключением того, что теперь двухзначные числа вводятся
                    number *= 100;
                    number += 15;
                    Log.d(ButtonsTAG, String.valueOf(v.getId()+" "+ number));

                    if (stringNumber == "") {
                        stringNumber = "F";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    } else {
                        stringNumber += "F";
                        textViewNumber.setTextColor(getResources().getColor(android.R.color.black));
                        textViewNumber.setText(stringNumber);
                    }
                    break;
                case R.id.resultButton://Кнопка для вывода результата
                    textViewResult.setTextColor(getResources().getColor(android.R.color.black));
                    searchResult(numberPositionOut, numberPositionIn);//Метод поиска результата
                    textViewResult.setText(resultString);
                    break;
                case R.id.clearButton://Кнопка для очищения полей
                    stringNumber = "";
                    resultString = "";
                    number = 0;
                    textViewNumber.setText(textViewNumber.getHint());
                    textViewNumber.setTextColor(textViewNumber.getHintTextColors());
                    textViewResult.setText(textViewResult.getHint());
                    textViewResult.setTextColor(textViewResult.getHintTextColors());
                    Log.d(ResultTAG, stringNumber + " " + resultString + " " + number + " " + numberPositionIn + " " + numberPositionOut);
                    Toast.makeText(MainActivity.this, "Result is clear", Toast.LENGTH_SHORT)
                          .show();
                    break;
            }

        }

    };


    /*
    Метод, который находит результат (Работает с системами счисления <= 10 на входе и выходе)
    Параметр positionIn, positionOut - системы счисления (позиция в спиннере + 2)
    (при изменении позиции спиннера выхода происходит нормальное изменение значения)
     */
    protected String searchResult (int positionOut, int positionIn){
        int result;
        int numberSearchResult = 0;
        resultString = "";
        Log.d(ResultTAG, "positionOut: " + positionOut);
        Log.d(ResultTAG, "positionIn: " + positionIn);
        int numberSearchResultIn = number;
        Log.d(ResultTAG, "numberSearchResultIn: " + numberSearchResultIn);
        if (positionIn != 10){//Проверка на необходимость перевода числа в десятичную систему счисления, поскольку допустим из 8 в 2 СС можно перевести
            //только при переводе сначала 8 -> 10, а потом уже 10 -> 2
            int i = 0;//Для степени в которое мы возводим число, обозначающее СС при переводе в 10 СС
            int j = stringNumber.length()-1;//Для метода isMoreThan10In (int);
            while (numberSearchResultIn != 0){
                isCCMoreThan10In(j);//Проверка на символ в вводимом числе
                Log.d(ResultTAG, "isMoreThan10: " + isMoreThan10);
                if (isMoreThan10 == false) {//Проверка на флаг
                    numberSearchResult += ((numberSearchResultIn % 10) * Math.pow(positionIn, i));//Если цифра < 10, то идем по одному разряду
                    Log.d(ResultTAG, "Number is: " + numberSearchResultIn % 10);
                    Log.d(ResultTAG, "numberSearchResult: " + numberSearchResult);
                    numberSearchResultIn /= 10;
                } else if (isMoreThan10 == true){//Проверка на флаг
                    numberSearchResult += ((numberSearchResultIn % 100) * Math.pow(positionIn, i));//Если цифра > 10, то идем на 2 разряда
                    Log.d(ResultTAG, "Number is: " + numberSearchResultIn % 100);
                    Log.d(ResultTAG, "numberSearchResult: " + numberSearchResult);
                    numberSearchResultIn /= 100;
                }
                Log.d(ResultTAG, "i = "+i);
                i++;//Итерация
                j--;//Итерация
                Log.d (ResultTAG, "numberSearchResultIn: " + numberSearchResultIn);
            }
        } else{
            numberSearchResult = number;//Если на входе была СС=10, то оставляем число как есть и просто не выполняем лишние действия
        }
        while (numberSearchResult != 0){
            result = numberSearchResult % positionOut;//Нахождение остатка числа
            // (в соответствии с самым известным алгоритмом, которым школьники даже умеют пользоваться в отличие от тебя)
            numberSearchResult /= positionOut;//Нахождение целой части (смотри скобки предыдущего коммента)
            isMoreThan10Out(result);//Проверка на наличие "цифр-символов" (для СС > 10 число 10 = А допустим и тд в выводе)
        }
        return resultString;//Собственно возвращаем значение, которое нам нужно и заносим его
    }

    /*
    Метод, который определяет числа на входе (чтоб не каждый раз поразрядно проходилось по числу, а допустим
    при попадании на цифру А, оно при преобразовании в число 10 сокращало преобразовываемое число на 2 разряда, а не на 1
    Метод как по мне костыльный, поэтому, Виталь, лучше подумать над нормальным алгоритмом для метода searchResult
    Но в принципе все работает как надо
     */
    protected void isCCMoreThan10In (int m){//Если попадается цифра-символ, то флаг = true, иначе = false
            if (stringNumber.charAt(m) == 'A'){//Не забывай кстати, что мы идем с младших разрядов, поэтому и по строке идем с правой части
                isMoreThan10 = true;
            } else if(stringNumber.charAt(m) == 'B'){
                isMoreThan10 = true;
            } else if (stringNumber.charAt(m) == 'C'){
                isMoreThan10 = true;
            } else if (stringNumber.charAt(m) == 'D'){
                isMoreThan10 = true;
            } else if (stringNumber.charAt(m) =='E'){
                isMoreThan10 = true;
            } else if (stringNumber.charAt(m) =='F'){
                isMoreThan10 = true;
            } else {
                isMoreThan10 = false;
            }
    }

    /*
    Метод, который определяет какая цифра будет на выходе, чтоб выводилось не 10 допустим, а выводилась А в строке результата
    Тоже по-костыльному, но работает
     */

    protected void isMoreThan10Out(int Result){
        if (Result == 10){//Собственно если у нас получилось число двухзначное в остатке, то преобразуем его в символ
            resultString = "A" + resultString;//Записываем в строку, сохраняя инверсию (а не resultString += "A", допустим)
        } else if (Result == 11){
            resultString = "B" + resultString;
        } else if (Result == 12){
            resultString = "C" + resultString;
        } else if (Result == 13) {
            resultString = "D" + resultString;
        } else if (Result == 14) {
            resultString = "E" + resultString;
        } else if (Result == 15) {
            resultString = "F" + resultString;
        } else {
            resultString = Result + resultString;
        }
    }
}

    /*
    Планы на дальнейшую жизнь:
    1) Дописать метод searchResult(int, int), чтоб работал еще и с другими системами счисления ну и чтоб результат нормально записывался, а то если получается число
    10, то так и записывается: 10, а не A (сделано)
    2) По-нормальному доделать интерфейс, добавить еще чтоб можно было по-символьно удалять, кнопочку для поиска числа с плавающей запятой
    3) Собственно реализовать метод (алгоритм я примерно вспомнил, но нужно будет определиться сколько шагов в нем делать, потому что он может идти до бесконечности)
    4) Оформить нормально комментарии, а то что-то мне слишком сильно понравилось такие комментарии писать
    5) Можно оформить активность, в которой будет показываться ход решения и тд

    Остальные предложения писать мне в VK: @id33522160
                                       Telegram: @kefir_103
     */