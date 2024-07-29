package com.yeshuwahane.scores.data.model.gamecarddata

data class Entry(
    val ACL: com.yeshuwahane.scores.data.model.gamecarddata.ACL,
    val _in_progress: Boolean,
    val _version: Int,
    val created_at: String,
    val created_by: String,
    val future_game: com.yeshuwahane.scores.data.model.gamecarddata.FutureGame,
    val game_card_config: com.yeshuwahane.scores.data.model.gamecarddata.GameCardConfig,
    val locale: String,
    val past_game_card: com.yeshuwahane.scores.data.model.gamecarddata.PastGameCard,
    val promotion_cards: List<com.yeshuwahane.scores.data.model.gamecarddata.PromotionCard>,
    val publish_details: List<com.yeshuwahane.scores.data.model.gamecarddata.PublishDetailXX>,
    val tags: List<Any>,
    val title: String,
    val uid: String,
    val upcoming_game: com.yeshuwahane.scores.data.model.gamecarddata.UpcomingGame,
    val updated_at: String,
    val updated_by: String
)