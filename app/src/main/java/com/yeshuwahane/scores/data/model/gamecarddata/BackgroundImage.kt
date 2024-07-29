package com.yeshuwahane.scores.data.model.gamecarddata

data class BackgroundImage(
    val ACL: List<Any>,
    val _version: Int,
    val content_type: String,
    val created_at: String,
    val created_by: String,
    val dimension: com.yeshuwahane.scores.data.model.gamecarddata.Dimension,
    val file_size: String,
    val filename: String,
    val is_dir: Boolean,
    val parent_uid: String,
    val publish_details: List<com.yeshuwahane.scores.data.model.gamecarddata.PublishDetailXX>,
    val tags: List<Any>,
    val title: String,
    val uid: String,
    val updated_at: String,
    val updated_by: String,
    val url: String
)