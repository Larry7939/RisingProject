package com.example.risingproject.src.main.detail.models

data class DetailHousesResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)
data class Result(
    val Comments: List<Any>,
    val Count: List<Count>,
    val HouseWarmContents: List<Any>,
    val HouseWarmInfo: List<HouseWarmInfo>,
    val IncludeTotalProduct: List<IncludeTotalProduct>,
    val SimilarHouseWarm: List<SimilarHouseWarm>,
    val WrittenBy: List<WrittenBy>
)
data class Count(
    val CommentCount: Int,
    val LikeCount: Int,
    val ScrapCount: Int,
    val ViewCount: Int
)
data class HouseWarmInfo(
    val Area: String,
    val Budget: String,
    val BuildingType: String,
    val DetailWork: Any,
    val FamilyType: String,
    val HouseWarmId: Int,
    val Image: String,
    val Location: String,
    val Style: String,
    val Type: String,
    val UserCreatedAt: String,
    val UserNickName: String,
    val UserProfileImage: String,
    val Width: String,
    val Worker: String
)
data class IncludeTotalProduct(
    val BrandName: String,
    val Cost: String,
    val Image: String,
    val ProductId: Int,
    val ProductName: String
)
data class SimilarHouseWarm(
    val Image: String,
    val ScrapCount: Int,
    val Title: String,
    val UserNickName: String,
    val UserProfileImage: String,
    val ViewCount: Int
)
data class WrittenBy(
    val NickName: String,
    val ProfileImage: String
)