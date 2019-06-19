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

                if (!login.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    //------------------------------------Загружаем логин
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(
                                openFileInput(FILE_LOGIN)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String strLog = "";
                    // читаем содержимое
                    while (true) {
                        try {
                            if (((strLog = br.readLine()) != null)) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //------------------------------------Загружаем пароль
                    br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(
                                openFileInput(FILE_PASSWORD)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String strPassword = "";
                    // читаем содержимое
                    while (true) {
                        try {
                            if (((strPassword = br.readLine()) != null)) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (password.getText().toString().equals (strPassword) & login.getText().toString().equals(strLog)){
                        exampleText.setText("Поздравляю вы ввели правильный Логин и Пароль");
                        Toast.makeText(MainActivity.this, "Вы вошли в систему", Toast.LENGTH_LONG).show();
                    } else {

                        exampleText.setText (getText(R.string.messageError));
                    }

                }
            }
        });
//-------------------------------------------------------------------------------------------------------------------------------
        // Нажимаем на кнопку РЕГИСТРАЦИЯ - записываем данные логина и пароля в файл
        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // --------------------------------- сохраняем логин
                String text = login.getText().toString();
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(FILE_LOGIN, MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(text.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                login.getText().clear();
                // --------------------------------- сохраняем пароль
                text = password.getText().toString();
                fos = null;
                try {
                    fos = openFileOutput(FILE_PASSWORD, MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(text.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    password.getText().clear();
                    //-----------------------------------
                    exampleText.setText("Вы зарегистрированы");
                    Toast.makeText(MainActivity.this, "Файлы сохранены " + getFilesDir(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}