package com.example.chatu.view.login

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatu.MainActivity
import com.example.chatu.R
import com.example.chatu.view.register.LoginViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.launch


@Composable
fun video(){
    val context = LocalContext.current
    val rawId = R.raw.clouds
    val packagename = context.packageName
    val uri = "android.resource://$packagename/$rawId"
    val exoPlayer = ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(Uri.parse(uri)))
        prepare()
        playWhenReady = true
        repeatMode = ExoPlayer.REPEAT_MODE_ALL
    }
    val playerView = StyledPlayerView(context).apply {
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        useController = false
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }
    DisposableEffect(AndroidView(factory = {playerView}, modifier = Modifier.fillMaxSize())){

        onDispose {
            exoPlayer.release()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    home: () -> Unit,
    back: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    val email: String by loginViewModel.email.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val loading: Boolean by loginViewModel.loading.observeAsState(initial = false)

    val scrollstate = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    LaunchedEffect(key1 = keyboardHeight) {
        coroutineScope.launch {
            scrollstate.scrollBy(keyboardHeight.toFloat())
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        video()
        if (loading) {
            CircularProgressIndicator()
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            modifier = Modifier
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(state = scrollstate)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_1), contentDescription = "Logo",
                modifier = Modifier.size(125.dp)
            )

            OutlinedTextField(
                value = email, onValueChange = {
                    loginViewModel.updateEmail(it)
                },
                label = {
                    Text(text = "Enter Your Email-Id", fontWeight = FontWeight.Bold)
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                visualTransformation = VisualTransformation.None

            )


            var pass by remember {
                mutableStateOf(false)
            }

            val ic = if (pass) {
                painterResource(id = R.drawable.baseline_visibility_24)
            } else {
                painterResource(id = R.drawable.baseline_visibility_off_24)
            }
            OutlinedTextField(
                value = password, onValueChange = {
                    loginViewModel.updatePassword(it)
                },
                label = {
                    Text(text = "Enter Your Password", fontWeight = FontWeight.Bold)
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Email")
                },

                trailingIcon = {
                    IconButton(onClick = {
                        pass = !pass
                    }) {
                        Icon(painter = ic, contentDescription = "Visibility Icon")
                    }
                },
                visualTransformation = if (pass) VisualTransformation.None
                else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )

            )

            Button(
                onClick = { loginViewModel.loginUser(home = home) },
                modifier = Modifier.width(320.dp)
            ) {
                Text(text = "Sign-In", modifier = Modifier.padding(vertical = 8.dp))
            }

            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier.padding(25.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Don't Have an Account?", fontWeight = FontWeight.Bold)
                val context = LocalContext.current
                TextButton(onClick = {
                    val navogate4 = Intent(context, MainActivity::class.java)
                    context.startActivity(navogate4)
                }) {
                    Text(
                        text = "Sign-Up", color = Color.Blue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}



