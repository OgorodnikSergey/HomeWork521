package ru.ogorodnik.homework521;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_LOGIN = "login.txt";
    private static final String FILE_PASSWORD = "password.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView exampleText = (TextView) findViewById(R.id.textView2);
        final EditText login = (EditText) findViewById(R.id.login);
        final EditText password = (EditText) findViewById(R.id.password);
        final Button loginBtn = (Button) findViewById(R.id.loginBtn);
        final Button registrationBtn = (Button) findViewById(R.id.registrationBtn);

        // Нажимаем на кнопку ЛОГИН - загрузаем данный логина и пароля из файла и сравниваем с введенными
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!login.getText().toString().equals("") || !password.getText().toString().equals("")) {
                    //------------------------------------Загружаем логин
                    String strLog = readLogin();
                    //------------------------------------Загружаем пароль
                    String strPassword = readPassword();

                    if (password.getText().toString().equals (strPassword) & login.getText().toString().equals(strLog)){
                        exampleText.setText("Поздравляю вы ввели правильный Логин и Пароль");
                        Toast.makeText(MainActivity.this, "Вы вошли в систему", Toast.LENGTH_LONG).show();
                    } else {

                        exampleText.setText (getText(R.string.messageErrorLogin));
                        Toast.makeText(MainActivity.this, getText(R.string.messageErrorToastLogin), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
//-------------------------------------------------------------------------------------------------------------------------------
        // Нажимаем на кнопку РЕГИСТРАЦИЯ - записываем данные логина и пароля в файл
        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!login.getText().toString().equals("") && !password.getText().toString().equals("")) {

                    // --------------------------------- сохраняем логин
                    String text = login.getText().toString();
                    saveData(FILE_LOGIN, text);
                    login.getText().clear();
                    // --------------------------------- сохраняем пароль
                    text = password.getText().toString();
                    saveData(FILE_PASSWORD, text);
                    password.getText().clear();
                    //-----------------------------------
                    exampleText.setText("Вы зарегистрированы");
                    Toast.makeText(MainActivity.this, "Файлы сохранены " + getFilesDir(), Toast.LENGTH_LONG).show();
                } else {
                    exampleText.setText (getText(R.string.messageErrorRegistr));
                    Toast.makeText(MainActivity.this, getText(R.string.messageErrorToastRegistr), Toast.LENGTH_LONG).show();
                }
            }
        });

}
//------------------------------------------------------------
    private String readLogin() {
        return readLineFromFile(FILE_LOGIN);
    }
//------------------------------------------------------------
    private String readPassword() {
        return readLineFromFile(FILE_PASSWORD);
    }
//------------------------READ FILE---------------------------
    private String readLineFromFile(String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    openFileInput(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String Stroka = "";
        // читаем содержимое
        while (true) {
            try {
                if (((Stroka = br.readLine()) != null)) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stroka;
    }
//------------------------------------------------------------
    private void saveData (String fileName, String text){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(fileName, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}