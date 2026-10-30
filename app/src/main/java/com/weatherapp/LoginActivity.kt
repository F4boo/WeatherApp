package com.weatherapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.theme.WeatherAppTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherAppTheme {
                LoginPage()
            }
        }
    }
}

@Composable
fun LoginPage(modifier: Modifier = Modifier) {

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    val activity = LocalActivity.current as Activity

    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {

        Text(
            text = "Bem-vindo/a!",
            fontSize = 24.sp
        )

        Spacer(
            modifier = Modifier.size(12.dp)
        )

        OutlinedTextField(
            value = email,
            label = {
                Text("Digite seu e-mail")
            },
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(
            modifier = Modifier.size(12.dp)
        )

        OutlinedTextField(
            value = password,
            label = {
                Text("Digite sua senha")
            },
            onValueChange = {
                password = it
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(
            modifier = Modifier.size(12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = {
                    Firebase.auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    activity,
                                    "Login OK!",
                                    Toast.LENGTH_LONG
                                ).show()

                                activity.startActivity(
                                    Intent(
                                        activity,
                                        MainActivity::class.java
                                    ).setFlags(
                                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                                    )
                                )
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Login FALHOU!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                },
                enabled = email.isNotEmpty() && password.isNotEmpty()
            ) {
                Text("Login")
            }

            Button(
                onClick = {

                    email = ""
                    password = ""

                }
            ) {

                Text("Limpar")
            }
        }
        Spacer(
            modifier = Modifier.size(12.dp)
        )

        Button(
            onClick = {

                activity.startActivity(
                    Intent(
                        activity,
                        RegisterActivity::class.java
                    )
                )

            }
        ) {
            Text("Cadastrar")
        }
    }
}