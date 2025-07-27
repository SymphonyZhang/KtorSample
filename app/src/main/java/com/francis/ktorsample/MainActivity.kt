package com.francis.ktorsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.francis.ktorsample.presentation.ProductsViewModel
import com.francis.ktorsample.ui.theme.KtorSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KtorSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: ProductsViewModel by viewModels()

                    //val products by viewModel.products.collectAsStateWithLifecycle()
                    Greeting(
                        /*products,*/
                        onClick = {
                            viewModel.login()
                        },
                        getCollect = {
                            viewModel.getCollects()
                        },
                        getBanner = {
                            viewModel.getBanners()
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(/*products: List<Product>, */onClick: () -> Unit,
             getCollect: () -> Unit,
             getBanner: () -> Unit,
             modifier: Modifier = Modifier
) {

    Column {
        ElevatedButton(onClick = onClick, modifier = modifier.fillMaxWidth()) {
            Text("Login")
        }
        ElevatedButton(onClick = getCollect, modifier = modifier.fillMaxWidth()) {
            Text("Get Collects")
        }
        ElevatedButton(onClick = getBanner, modifier = modifier.fillMaxWidth()) {
            Text("Get Banners")
        }

        /*LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(products) {
                Card(border = BorderStroke(
                    width = 2.dp,
                    color = Color.Black
                )) {
                    Text(it.id.toString(), fontSize = 30.sp, color = Color.Green,modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Text(it.title, color = Color.Red, fontSize = 22.sp)
                    Text(it.description, color = Color.DarkGray, fontSize = 16.sp)
                }
                HorizontalDivider(modifier = modifier.height(1.dp))
            }
        }*/
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KtorSampleTheme {
        Greeting(
            /*listOf(
                Product(
                    id = 1,
                    "Essence Mascara Lash Princess",
                    description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula."
                )
            ),*/
            onClick = {},
            getCollect = {},
            getBanner = {}
        )
    }
}