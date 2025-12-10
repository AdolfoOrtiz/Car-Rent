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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carrentv1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreguntasFrecuentesScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.pf_titulo),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.pf_volver),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
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
                contentDescription = stringResource(id = R.string.pf_logo_desc),
                modifier = Modifier
                    .size(120.dp)
                    .padding(top = 40.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Título principal
            Text(
                text = stringResource(id = R.string.pf_titulo),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtítulo
            Text(
                text = stringResource(id = R.string.pf_subtitulo),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = stringResource(id = R.string.pf_p1), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = stringResource(id = R.string.pf_p2), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = stringResource(id = R.string.pf_p3), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = stringResource(id = R.string.pf_p4), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = stringResource(id = R.string.pf_p5), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = stringResource(id = R.string.pf_p6), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = stringResource(id = R.string.pf_p7), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = stringResource(id = R.string.pf_p8), fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
