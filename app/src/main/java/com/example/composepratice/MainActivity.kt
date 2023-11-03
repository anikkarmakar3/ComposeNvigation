package com.example.composepratice

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.material.BottomNavigation
import com.example.composepratice.model.UserData

class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*ComposePraticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }*/

            NavigationController()
        }
    }
}

@Composable
fun BottomBar() {

    Column {
        // Other composables here
        BottomNavigation(
            // Define your navigation items and actions here
        )
    }
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(elevation = 10.dp) {

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Home,"")
        },
            label = { Text(text = "Home") },
            selected = (selectedIndex.value == 0),
            onClick = {
                selectedIndex.value = 0
            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Favorite,"")
        },
            label = { Text(text = "Favorite") },
            selected = (selectedIndex.value == 1),
            onClick = {
                selectedIndex.value = 1
            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Person,"")
        },
            label = { Text(text = "Profile") },
            selected = (selectedIndex.value == 2),
            onClick = {
                selectedIndex.value = 2
            })
    }
}


@Composable
fun NavigationController() {
    val navController = rememberNavController()
    var emailAddress by remember { mutableStateOf(TextFieldValue("")) }
    NavHost(navController = navController, startDestination = "login") {
        composable(route = "login") {
            LoginScreen {
                navController.navigate("main/${it.userEmail}/${it.userPassword}")
            }
        }
        composable(route = "register") {
            RegisterScreen {
                navController.navigate("login")
            }
        }
        composable(route = "main/{email}/{password}", arguments = listOf(
            navArgument("email") {
                type = NavType.StringType
            },
            navArgument("password"){
                type = NavType.StringType
            }
        )) {
            val email = it.arguments?.getString("email")
            val password = it.arguments?.getString("password")
            mainScreen(email,password, navController)
        }
    }
}


@Composable
fun RegisterScreen(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Text(text = "This is register screen", fontSize = 20.sp, textAlign = TextAlign.Center)
        Button(onClick = { onClick }, shape = RoundedCornerShape(20.dp)) {
            Image(
                painterResource(id = R.drawable.baseline_arrow_right_24),
                contentDescription = "Cart button icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "Go to login screen")
        }
    }

}


@Composable
fun LoginScreen(getData: (data: UserData) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(text = "This is login screen", fontSize = 20.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.size(40.dp))
        var enteredEmail by remember { mutableStateOf("") }
        var enteredPassword by remember { mutableStateOf("") }
        EmailAddress { email ->
            enteredEmail = email
        }
        Spacer(modifier = Modifier.size(5.dp))
        Password {
            enteredPassword = it
        }
        Spacer(modifier = Modifier.size(40.dp))
        val userData=UserData(enteredEmail,enteredPassword)
        Button(onClick = { getData(userData) }, shape = RoundedCornerShape(20.dp)) {
            Image(
                painterResource(id = R.drawable.baseline_arrow_right_24),
                contentDescription = "Cart button icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "Go to login screen")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailAddress(getEmail: (email: String) -> Unit) {
    var emailAddress by remember { mutableStateOf(TextFieldValue()) }
    return OutlinedTextField(
        value = emailAddress,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
        onValueChange = {
            emailAddress = it
            getEmail(it.text)
        },
        label = { Text(text = "Email address") },
        placeholder = { Text(text = "Enter your e-mail") },
        singleLine = false,
        modifier = Modifier.fillMaxWidth(.8f),
        shape = MaterialTheme.shapes.small.copy(CornerSize(2.dp)),
        maxLines = 1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Password(getPassword: (password: String) -> Unit): String {
    var password by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = password,
        label = { Text(text = "Password") },
        placeholder = { Text(text = "Enter your password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = { it ->
            password = it
            getPassword(it.text)
        },
        singleLine = true,
        maxLines = 1,
        modifier = Modifier.fillMaxWidth(.8f)
    )
    return password.text
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreen(email: String?, password: String?, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Top App Bar", color = White)
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Blue)
            )
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(text = email.toString(), fontSize = 20.sp, textAlign = TextAlign.Center)
                Text(text = password.toString(), fontSize = 20.sp, textAlign = TextAlign.Center)
                Button(onClick = { navController.navigate("register") }, shape = RectangleShape) {
                    Image(
                        painterResource(id = R.drawable.baseline_arrow_right_24),
                        contentDescription = "Cart button icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = "Go to login screen")
                }
            }
        })

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    SimpleColumn()
}


@Composable
fun SimpleRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Row Text 1", Modifier.background(Color.Red))
        Text(text = "Row Text 2", Modifier.background(Color.White))
        Text(text = "Row Text 3", Modifier.background(Green))
        Text(text = "Row Text 3", Modifier.background(Color.Blue))
    }

}

@Composable
fun SimpleColumn() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello Anik",
            textAlign = TextAlign.Center
        )
        Text(text = "Sample Row Example", textAlign = TextAlign.Center, fontSize = 30.sp)
        SimpleRow()
        Text(text = "Sample Column Example", textAlign = TextAlign.Center, fontSize = 40.sp)
        Text(
            text = "Column Text 1",
            Modifier.background(Color.Red)
        )
        Text(text = "Column Text 2", Modifier.background(Color.White))
        Text(text = "Column Text 3", Modifier.background(Green))
        Text(
            text = "Column Text 3",
            Modifier.background(Color.Magenta),
            style = androidx.compose.ui.text.TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(5f, 5f),
                    blurRadius = 5f
                )
            )
        )
        Text("Hello Compose ".repeat(50), maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}