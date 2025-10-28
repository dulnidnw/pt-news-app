package com.example.pt_news_app.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.pt_news_app.presentation.navigation.NavRoutes
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImagePainter.State.Empty.painter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest
import com.example.pt_news_app.presentation.ui.profile.BottomNavBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {

    val state by viewModel.uiState.collectAsState()
    var selectedCategory by remember { mutableStateOf("Business") }
    val scope = rememberCoroutineScope()

    val imageUrl: String = ""

    LaunchedEffect(imageUrl) {
        Log.d("NewsCardHorizontal", "Loading image: $imageUrl")
    }


    LaunchedEffect(Unit) {
        viewModel.loadNews()
        viewModel.loadNewsFeed(selectedCategory)
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        containerColor = Color(0xFFF9F9F9)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(8.dp))
            SearchBar(onSearch = {})

            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Latest News",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text("See All ➜", color = Color.Gray, fontSize = 14.sp)
            }

            Spacer(Modifier.height(16.dp))

            // Horizontal top headlines
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(state.articles.take(5)) { article ->
                    NewsCardHorizontal(
                        title = article.title ?: "",
                        description = article.description ?: "",
                        imageUrl = article.urlToImage ?: ""
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Category Chips
            val categories = listOf(
                "Business",
                "Entertainment",
                "General",
                "Health",
                "Science",
                "Sports",
                "Technology"
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { category ->
                    CategoryChip(
                        category = category,
                        selected = category == selectedCategory,
                        onClick = {
                            selectedCategory = category
                            scope.launch {
                                viewModel.loadNewsFeed(category)
                            }
                        }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Vertical news list
//            AsyncImage(
//                model = imageUrl,
//                contentDescription = "Article Image",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .clip(RoundedCornerShape(10.dp))
////                ,
////                placeholder = painterResource(id = R.drawable.placeholder),
////                error = painterResource(id = R.drawable.image_error)
//            )

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
fun SearchBar(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Search") },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
        },
        shape = RoundedCornerShape(50),
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
}

@Composable
fun NewsCardHorizontal(title: String, description: String, imageUrl: String) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(180.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            ) {
                val state = painter.state
                if (state is coil3.compose.AsyncImagePainter.State.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE0E0E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF00B4D8))
                    }
                } else if (state is coil3.compose.AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE0E0E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Image failed", color = Color.Gray, fontSize = 12.sp)
                    }
                } else {
                    SubcomposeAsyncImageContent()
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

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = description,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun CategoryChip(category: String, selected: Boolean, onClick: () -> Unit) {
    Box(

        modifier = Modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(50))
            .background(if (selected) Color(0xFF00B4D8) else Color.White)
            .border(
                BorderStroke(1.dp, if (selected) Color.Transparent else Color(0xFFDADADA)),
                RoundedCornerShape(50)
            )
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            category,
            color = if (selected) Color.White else Color.Black,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
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
            AsyncImage(
                model = imageUrl,
                contentDescription = "Description",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
//            SubcomposeAsyncImage(
//                model = imageUrl,
//                contentDescription = title,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                val state = painter.state
//                if (state is coil3.compose.AsyncImagePainter.State.Loading) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color(0xFFE0E0E0)),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator(color = Color(0xFF00B4D8))
//                    }
//                } else if (state is coil3.compose.AsyncImagePainter.State.Error) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color(0xFFE0E0E0)),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text("Image failed", color = Color.Gray, fontSize = 12.sp)
//                    }
//                } else {
//                    SubcomposeAsyncImageContent()
//                }
//            }

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
