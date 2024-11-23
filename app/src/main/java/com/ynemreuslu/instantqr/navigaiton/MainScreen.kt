package com.ynemreuslu.instantqr.navigaiton

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.ynemreuslu.instantqr.ui.history.HistoryScreen
import com.ynemreuslu.instantqr.ui.scanner.ScannerScreen
import com.ynemreuslu.instantqr.R
import com.ynemreuslu.instantqr.model.QRCode
import com.ynemreuslu.instantqr.ui.calendar.CalendarScreen
import com.ynemreuslu.instantqr.ui.contact.ContactScreen
import com.ynemreuslu.instantqr.ui.creator.CreatorScreen
import com.ynemreuslu.instantqr.ui.details.DetailsScreen
import com.ynemreuslu.instantqr.ui.email.EmailScreen
import com.ynemreuslu.instantqr.ui.location.LocationScreen
import com.ynemreuslu.instantqr.ui.phone.PhoneScreen
import com.ynemreuslu.instantqr.ui.sms.SmsScreen
import com.ynemreuslu.instantqr.ui.text.TextScreen
import com.ynemreuslu.instantqr.ui.url.UrlScreen
import com.ynemreuslu.instantqr.ui.wifi.WifiScreen

@Composable
fun MainScreen(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }

    NavHost(
        navController = navController,
        startDestination = "scanner",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(300)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(300)
            )
        }
    ) {
        composable("scanner") {
            ScreenWithBottomBar(
                navController = navController,
                snackbarData = snackbarHostState,
                topBar = { TopBar(stringResource(R.string.scanner)) }
            ) {
                ScannerScreen(
                    hostState = snackbarHostState,
                    onDetails = { qrCode ->
                        val gson = Gson()
                        val qrCodeJson = gson.toJson(qrCode)
                        val encodedQrCode = Uri.encode(qrCodeJson)
                        navController.navigate("details/$encodedQrCode")
                    }
                )
            }
        }

        composable("history") {
            ScreenWithBottomBar(
                navController = navController,
                topBar = { TopBar(stringResource(R.string.history)) }
            ) {
                HistoryScreen()
            }
        }

        composable("creator") {
            ScreenWithBottomBar(
                navController = navController,
                topBar = { TopBar(stringResource(R.string.creator)) }
            ) {
                CreatorScreen(
                    onUrlSelected = { navController.navigate("url") },
                    onTextSelected = { navController.navigate("text") },
                    onContactSelected = { navController.navigate("contact") },
                    onPhoneSelected = { navController.navigate("phone") },
                    onEmailSelected = { navController.navigate("email") },
                    onLocationSelected = { navController.navigate("location") },
                    onSmsSelected = { navController.navigate("sms") },
                    onWifiSelected = { navController.navigate("wifi") },
                    onCalendarSelected = { navController.navigate("calendar") }
                )
            }
        }

        composable("url") {
            ScreenWithBottomBar(
                navController = navController,
                topBar = {
                    NavigationTopBar(
                        onBackPress = { navController.popBackStack() },
                        stringResource(R.string.url_label)
                    )
                },
                showBottomBar = false
            ) {
                UrlScreen()
            }
        }

        composable("text") {
            ScreenWithBottomBar(
                navController = navController,
                topBar = {
                    NavigationTopBar(
                        onBackPress = { navController.popBackStack() },
                        stringResource(R.string.text_label)
                    )
                },
                showBottomBar = false
            ) {
                TextScreen()
            }
        }

        composable("contact") {
            ScreenWithBottomBar(
                navController = navController,
                topBar = {
                    NavigationTopBar(
                        onBackPress = { navController.popBackStack() },
                        stringResource(R.string.contact_label)
                    )
                },
                showBottomBar = false
            ) {
                ContactScreen()
            }
        }

        composable("email") {
            ScreenWithBottomBar(
                navController = navController,
                topBar = {
                    NavigationTopBar(
                        onBackPress = { navController.popBackStack() },
                        stringResource(R.string.email_label)
                    )
                },
                showBottomBar = false
            ) {
                EmailScreen()
            }
        }

        composable("location") {
            ScreenWithBottomBar(
                navController = navController,
                topBar = {
                    NavigationTopBar(
                        onBackPress = { navController.popBackStack() },
                        stringResource(R.string.location_label)
                    )
                },
                showBottomBar = false
            ) {
                LocationScreen()
            }
        }

        composable("sms") {
            ScreenWithBottomBar(
                navController = navController,
                topBar = {
                    NavigationTopBar(
                        onBackPress = { navController.popBackStack() },
                        stringResource(R.string.sms_label)
                    )
                },
                showBottomBar = false
            ) {
                SmsScreen()
            }
        }

        composable("phone") {
            ScreenWithBottomBar(
                navController = navController,
                topBar = {
                    NavigationTopBar(
                        onBackPress = { navController.popBackStack() },
                        text = stringResource(R.string.phone_label)
                    )
                },
                showBottomBar = false
            ) {
                PhoneScreen()
            }
        }

        composable("wifi") {
            ScreenWithBottomBar(
                navController = navController,
                topBar = {
                    NavigationTopBar(
                        onBackPress = { navController.popBackStack() },
                        text = stringResource(R.string.wifi_label)
                    )
                },
                showBottomBar = false
            ) {
                WifiScreen()
            }
        }

        composable("calendar") {
            ScreenWithBottomBar(
                navController = navController,
                topBar = {
                    NavigationTopBar(
                        onBackPress = { navController.popBackStack() },
                        text = stringResource(R.string.calendar_label)
                    )
                },
                showBottomBar = false
            ) {
                CalendarScreen()
            }
        }

        composable("details/{code}") { navBackStackEntry ->
            val encodedQrCode = navBackStackEntry.arguments?.getString("code")
            val qrCodeJson = Uri.decode(encodedQrCode)
            val gson = Gson()
            val qrCode = gson.fromJson(qrCodeJson, QRCode::class.java)

            ScreenWithBottomBar(
                navController = navController,
                topBar = {
                    NavigationTopBar(
                        onBackPress = { navController.popBackStack() },
                        text = stringResource(R.string.details)
                    )
                },
                showBottomBar = false
            ) {
                qrCode?.let { DetailsScreen(qrCode = it) }
            }
        }
    }
}


@Composable
fun ScreenWithBottomBar(
    navController: NavHostController,
    topBar: @Composable () -> Unit,
    showBottomBar: Boolean = true,
    snackbarData: SnackbarHostState = SnackbarHostState(),
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        snackbarHost = { SnackbarHost(snackbarData) },
        bottomBar = { if (showBottomBar) BottomNavigationBarScreen(navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTopBar(onBackPress: () -> Unit, text: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(text: String) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(),
        title = {
            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
        }
    )
}