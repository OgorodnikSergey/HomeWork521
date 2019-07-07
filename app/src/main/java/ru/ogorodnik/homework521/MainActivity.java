package ru.ogorodnik.homework521;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
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

        // Нажимаем на кнопку ЛОГИН - загружаем данные логина и пароля из файла и сравниваем с введенными
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLoginAndPasswordValid()) {
                    //------------------------------------Загружаем логин
                    String strLog = readLogin();
                    //------------------------------------Загружаем пароль
                    String strPassword = readPassword();

                    if (password.getText().toString().equals (strPassword) & login.getText().toString().equals(strLog)){
                        exampleText.setText(getText(R.string.messageOkLogin));
                        Toast.makeText(MainActivity.this, getText(R.string.messageOkToastLogin), Toast.LENGTH_LONG).show();
                    } else {

                        exampleText.setText (getText(R.string.messageErrorLogin));
                        Toast.makeText(MainActivity.this, getText(R.string.messageErrorToastLogin), Toast.LENGTH_LONG).show();
                    }
                } else {
                    exampleText.setText (getText(R.string.messageErrorLoginEmpty));
                    Toast.makeText(MainActivity.this, getText(R.string.messageErrorToastLoginEmpty), Toast.LENGTH_LONG).show();
                }
            }
        });
//-------------------------------------------------------------------------------------------------------------------------------
        // Нажимаем на кнопку РЕГИСТРАЦИЯ - записываем данные логина и пароля в файл
        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginAndPasswordValid()) {

                    // --------------------------------- сохраняем логин
                    final String loginText = login.getText().toString();
                    //String text = login.getText().toString();
                    saveData(FILE_LOGIN, loginText);
                    login.getText().clear();
                    // --------------------------------- сохраняем пароль
                    final String passwordText = password.getText().toString();
                    //text = password.getText().toString();
                    saveData(FILE_PASSWORD, passwordText);
                    password.getText().clear();
                    //-----------------------------------
                    exampleText.setText(getText(R.string.messageOkRegistration));
                    Toast.makeText(MainActivity.this, getText(R.string.messageOkRegistration), Toast.LENGTH_LONG).show();
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
        br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
        return br.readLine();
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    } finally {
        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//------------------------------------------------------------
    private void saveData (String fileName, String text){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean isLoginAndPasswordValid() {
        final EditText login = (EditText) findViewById(R.id.login);
        final EditText password = (EditText) findViewById(R.id.password);
        return !login.getText().toString().equals("") && !password.getText().toString().equals("");
    }
}