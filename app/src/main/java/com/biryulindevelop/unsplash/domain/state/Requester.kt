package com.biryulindevelop.unsplash.domain.state

enum class Requester(
    var param: String = ""
) {
    ALL_LIST,
    COLLECTIONS,
    PROFILE
}