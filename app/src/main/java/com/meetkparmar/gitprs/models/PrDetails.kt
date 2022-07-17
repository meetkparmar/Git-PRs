package com.meetkparmar.gitprs.models

data class PrDetails(
    var title: String? = "",
    var created_at: String? = "",
    var closed_at: String? = "",
    var html_url: String? = "",
    var number: Int,
    var user: User
)