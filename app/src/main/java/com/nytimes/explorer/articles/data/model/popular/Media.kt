package com.nytimes.explorer.articles.data.model.popular

import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("media-metadata")
    val mediaMetadata: List<MediaMetadata>,
)