package com.yeshuwahane.scores.data.model.gamecarddata

data class PromotionCard(
    val _metadata: com.yeshuwahane.scores.data.model.gamecarddata.Metadata,
    val card: List<com.yeshuwahane.scores.data.model.gamecarddata.Card>,
    val position: Int
)