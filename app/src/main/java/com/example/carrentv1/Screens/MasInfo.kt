package com.example.carrentv1.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carrentv1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasInformacionScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.mas_info_titulo), color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.volver),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.car_rent),
                contentDescription = stringResource(R.string.logo_desc),
                modifier = Modifier
                    .size(120.dp)
                    .padding(top = 40.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.mas_info_titulo_main),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Contenido del texto
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.texto_1),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Text(text = stringResource(R.string.texto_2), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_3), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_4), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_5), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_6), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_7), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_8), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_9), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_10), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_11), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_12), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_13), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_14), fontSize = 16.sp, textAlign = TextAlign.Center)
                Text(text = stringResource(R.string.texto_15), fontSize = 16.sp, textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
