package com.nytimes.explorer.articles.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.nytimes.explorer.articles.data.model.Article


@Composable
fun Article(
    article: Article,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = article.headline.main,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = article.byline?.original ?: "",
            fontWeight = FontWeight.Light,
        )


    }
    Column() {
        Image(
            painter = rememberImagePainter(article.multimedia.firstOrNull()?.url),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))

}