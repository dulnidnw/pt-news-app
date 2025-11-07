package com.example.pt_news_app.presentation.ui.newsDetail

import android.R
import android.R.attr.description
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.example.pt_news_app.data.local.db.NewsDatabase
import com.example.pt_news_app.data.local.entity.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DetailScreen(
    title: String,
    subtitle: String,
    body: String, imageUrl: String,
    author: String,
    publishAt: String,
    content: String,
    onBackClick: () -> Unit = {}
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.background_light))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.TopCenter)
        ) {
            AndroidView(
                factory = { ctx ->
                    ImageView(ctx).apply {
                        val displayMetrics = ctx.resources.displayMetrics
                        val screenHeight = displayMetrics.heightPixels

                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            (screenHeight * 0.6).toInt()
                        )
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { imageView ->
                    Glide.with(imageView.context)
                        .load(imageUrl)
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                        .error(android.R.drawable.stat_notify_error)
                        .into(imageView)
                },
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 40.dp, start = 16.dp)
                    .background(Color(0xFF4CC9F0), CircleShape)
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 300.dp)
        ) {

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Column(modifier = Modifier.padding(16.dp)) {
                    ExpandableText(body)
                }

                FloatingActionButton(
                    onClick = {
                        val favorite = Favorite(
                            title = title,
                            subtitle = subtitle,
                            body = body,
                            imageUrl = imageUrl,
                            author = author,
                            publishAt = publishAt,
                            content = content
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            val favoriteDao = NewsDatabase.get(context).favoriteDao()
                            val existing = favoriteDao.getFavoriteByTitle(favorite.title)

                            withContext(Dispatchers.Main) {
                                if (existing != null) {
                                    Toast.makeText(context, "Already added to favorites", Toast.LENGTH_SHORT).show()
                                } else {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        favoriteDao.insertFavorite(favorite)
                                    }
                                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                    },
                    containerColor = Color(0xFF4CC9F0),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier
                        .padding(24.dp)
                        .align(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "favorite",
                        tint = Color.White
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .align(Alignment.TopCenter)
                .offset(y = 220.dp),

            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White  // Set your desired background color
            )

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "By: $author",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(4.dp))
                Text(text = subtitle, fontWeight = FontWeight.Normal, fontSize = 14.sp)
                Spacer(Modifier.height(4.dp))
                Text(text = "Published : $publishAt", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }

}

@Composable
fun ExpandableText(text: String) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = if (expanded) text else text.take(3000) + "...",
            fontSize = 16.sp,
            lineHeight = 22.sp,
            color = Color.Black
        )
        TextButton(onClick = { expanded = !expanded }) {
            Text(if (expanded) "Show less" else "Show more")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        title = "Sample Article Title",
        subtitle = "This is a sample subtitle for the article",
        body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.",
        imageUrl = "https://picsum.photos/400/600",
        author = "author",
        publishAt = "2025.45.23",
        content = "ghjgsdkjadkjadhkasjdh",
        onBackClick = { }
    )
}