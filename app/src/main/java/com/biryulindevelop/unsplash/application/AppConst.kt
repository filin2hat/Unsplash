package com.biryulindevelop.unsplash.application

const val TOKEN_NAME = "name_token"
const val TOKEN_KEY = "key_token"
const val TOKEN_ENABLED_KEY = "token_enabled"
const val ONBOARDING_IS_SHOWN = "onboarding_is_shown"

const val ACCESS_KEY = "rq3GjG6SxyLwKaX7M6wD6GPgsasZA7jD265hag-DdfE"
const val SECRET_KEY = "z3MWIbbje6qPr_zndSkSwnMxH-5neuoEhmBs-Ojm8BQ"
const val REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob"
const val SCOPE =
    "public+read_user+" +
            "write_user+read_photos+" +
            "write_photos+" +
            "write_likes+" +
            "write_followers+" +
            "read_collections+" +
            "write_collections"

const val CALL =
    "https://unsplash.com/oauth/authorize" +
            "?client_id=" + ACCESS_KEY +
            "&redirect_uri=" + REDIRECT_URI +
            "&response_type=code" +
            "&scope=" + SCOPE