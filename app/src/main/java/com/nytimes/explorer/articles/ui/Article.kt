package com.nytimes.explorer.articles.ui

import android.content.Intent
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.net.Uri

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberImagePainter
import com.nytimes.explorer.articles.data.model.search.Article
import java.util.*


@Composable
fun Article(
    article: Article,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var imageUrl = article.multimedia.firstOrNull { !it.url.isNullOrBlank() }?.url

    if (imageUrl != null && !imageUrl.contains("https://static01.nyt")) {
        imageUrl = "https://static01.nyt.com/${imageUrl}"
    }
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
            )
            if (!article.byline?.original.isNullOrBlank()) {
                Text(
                    text = article.byline.original,
                    fontWeight = FontWeight.Light,
                )
            }
            var date = ""
            if (article.pub_date.contains("+")) {
                val df = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ")
                val toFormat = SimpleDateFormat("EEE, dd-MM-yyyy HH:mm")
                toFormat.timeZone = TimeZone.getDefault()
                val d1 = df.parse(article.pub_date)
                val date = toFormat.format(d1)


            } else {
                val df = SimpleDateFormat("yyyy-MM-dd")
                val toFormat = SimpleDateFormat("EEE, dd-MM-yyyy")
                val d1 = df.parse(article.pub_date)
                val date = toFormat.format(d1)
            }
            if (date.isNotBlank()) {
                Text(
                    text = date,
                    fontWeight = FontWeight.Light,
                )
            }
        }
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp)
        )
    }
}



