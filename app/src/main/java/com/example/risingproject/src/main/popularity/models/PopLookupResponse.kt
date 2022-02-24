package com.example.risingproject.src.main.popularity.models

data class PopLookupResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)
data class Result(
    val TodayStory: List<TodayStory>,
    val ProductCategories: List<ProductCategory>,
    val BestProduct: List<List<BestProduct>>
)
data class TodayStory(
    val HouseWarmId: Int,
    val HouseWarmTitle: String,
    val MainImage: String
)
data class ProductCategory(
    val CategoryImage: String,
    val CategoryName: String
)
data class BestProduct(
    val CategoryName: String,
    val Cost: String,
    val DiscountPercent: String,
    val ProductId: Int,
    val ProductImage: String,
    val ProductName: String,
    val Ranking: Int,
    val ReviewCount: Int,
    val StarGrade: String
)