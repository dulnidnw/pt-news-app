package com.example.pt_news_app.presentation.ui.seeAll

import android.util.Log
import android.util.Log.e
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.pt_news_app.presentation.navigation.NavRoutes
import com.example.pt_news_app.presentation.ui.seeAll.SearchBar
import com.example.pt_news_app.presentation.ui.seeAll.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeAllScreen(navController: NavController, viewModel: SearchViewModel) {

    val state by viewModel.uiState.collectAsState()
    var selectedCategory by remember { mutableStateOf("Business") }
    rememberCoroutineScope()

    val imageUrl: String = ""

    LaunchedEffect(imageUrl) {
        Log.d("NewsCardHorizontal", "Loading image: $imageUrl")
    }


    LaunchedEffect(Unit) {
        viewModel.loadNews()
    }



    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        containerColor = Color(0xFFF9F9F9),
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
//                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(8.dp))

            SearchBar(onSearch = { query ->
                viewModel.filterNews(query) // or call API-based search here
            })

            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

            }

            Spacer(Modifier.height(16.dp))

            state.articles.drop(5).forEach { article ->
                NewsCardVertical(
                    title = article.title ?: "",
                    imageUrl = article.urlToImage ?: ""
                )

                Spacer(Modifier.height(16.dp))
            }

        }
    }

}

@Composable
fun SearchBar(onSearch: (String) -> Unit, viewModel: SearchViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()

    OutlinedTextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            viewModel.filterNews(it)  // filter on typing
        }, placeholder = { Text("Search") },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
        },
        shape = RoundedCornerShape(50),
        label = { Text("Search by title") },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedContainerColor = Color(0xFFF2F2F2),
            focusedContainerColor = Color(0xFFF2F2F2)
        )
    )

    LazyColumn {
        item {
            Text(
                "About 1060 results for Sri lanka",
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }

        items(state.filteredArticles) { article ->
            NewsArticleItem(article)
        }
    }
}

@Composable
fun NewsCardVertical(title: String, imageUrl: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box {
            val context = LocalContext.current

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    AndroidView(
                        factory = { ctx ->
                            ImageView(ctx).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    400  // adjust height as you need
                                )
                                scaleType = ImageView.ScaleType.CENTER_CROP
                            }
                        },
                        update = { imageView ->
                            try {
                                Glide.with(imageView.context)
                                    .load(imageUrl)
                                    .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                                    .error(android.R.drawable.stat_notify_error)
                                    .into(imageView)
                            } catch (e: Exception) {
                                println("An unexpected error occurred: ${e.message}")
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = title)
                }
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                        )
                    )
            )

            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            onClick = { navController.navigate(NavRoutes.screenHome) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite") },
            label = { Text("Favourite") },
            selected = false,
            onClick = { navController.navigate(NavRoutes.screenFavorite) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { navController.navigate(NavRoutes.screenProfile) }
        )
    }
}
