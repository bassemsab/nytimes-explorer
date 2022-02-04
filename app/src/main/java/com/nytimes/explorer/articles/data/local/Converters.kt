package com.nytimes.explorer.articles.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.nytimes.explorer.articles.data.model.Article
import com.nytimes.explorer.core.util.JsonParser

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromArticlesJson(json: String): List<Article> {
        return jsonParser.fromJson<ArrayList<Article>>(
            json,
            object : TypeToken<ArrayList<Article>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toArticlesJson(meanings: List<Article>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Article>>() {}.type
        ) ?: "[]"
    }
}