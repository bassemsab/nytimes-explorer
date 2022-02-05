package com.nytimes.explorer.articles.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberImagePainter
import com.nytimes.explorer.articles.data.model.Article


@Composable
fun Article(
    article: Article,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageUrl = article.multimedia.firstOrNull { !it.url.isNullOrBlank() }?.url

    val rowScale = if (imageUrl.isNullOrBlank()) 1.0f else 0.7f
    Row(
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(article.web_url)
                startActivity(context, openURL, null)
            },
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,


        ) {
        Column(modifier = Modifier.fillMaxWidth(rowScale)) {
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
        Image(
            painter = rememberImagePainter("https://static01.nyt.com/${imageUrl}"),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp)
        )
    }
}



