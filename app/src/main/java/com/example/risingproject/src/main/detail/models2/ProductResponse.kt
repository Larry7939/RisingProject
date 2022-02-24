package com.example.risingproject.src.main.detail.models2


data class ProductResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)
data class Result(
    val ProductImage: List<ProductImage>,
    val ProductInfo: List<ProductInfo>,
    val ProductInquiryCount: List<ProductInquiryCount>,
    val ProductIntro: List<ProductIntro>,
    val ProductReview: List<ProductReview>,
    val ProductStylingShot: List<ProductStylingShot>,
    val ReviewPhoto: List<ReviewPhoto>,
    val SimilarProduct: List<SimilarProduct>,
    val TotalReview: List<TotalReview>
)
data class ProductImage(
    val imageUrl: String
)
data class ProductInfo(
    val Benefit: String,
    val BrandName: String,
    val Cost: String,
    val DeliveryCost: String,
    val DeliveryType: String,
    val Discount: String,
    val ProductId: Int,
    val ProductName: String,
    val ReviewCount: Int,
    val SaleCost: String,
    val StarGrade: String,
    val largeCategoryId: Int
)
data class ProductInquiryCount(
    val InquiryCount: Int
)
data class ProductIntro(
    val imageUrl: String
)
data class ProductReview(
    val Contents: String,
    val CreatedAt: String,
    val Image: String,
    val ProfileImage: Any,
    val ReviewId: Int,
    val StarPoint: String,
    val UserNickName: String
)
data class ProductStylingShot(
    val HouseWarmContentsId: Int,
    val Image: String,
    val ProfileImage: String,
    val UserNickName: String
)
data class ReviewPhoto(
    val id: Int,
    val imageUrl: String
)
data class SimilarProduct(
    val BrandName: String,
    val DelCostType: Any,
    val Discount: String,
    val Image: String,
    val ProductId: Int,
    val ProductName: String,
    val ReviewCount: Int,
    val SaleCost: String,
    val SpecialPrice: String,
    val StarGrade: String
)
data class TotalReview(
    val `1count`: Int,
    val `2count`: Int,
    val `3count`: Int,
    val `4count`: Int,
    val `5count`: Int
)