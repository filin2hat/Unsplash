package com.biryulindevelop.unsplash.data.state

enum class Requester(
    var param: String = ""
) {
    ALL_LIST,
    COLLECTIONS,
    PROFILE
}