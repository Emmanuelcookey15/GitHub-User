package com.emiko.githubuser.utils

object Constants {
    const val TIME_OUT = 10L
    const val QUERY_PAGE_SIZE = 30
    const val FIRST_PAGE = 1
    const val POST_PER_PAGE = 30
    const val PAGE_PREFETCH_DISTANCE = 30 // triggers when to fetch a page


    const val ERROR_BEGIN_OBJECT = "BEGIN_OBJECT"
    const val ERROR_BEGIN_ARRAY = "BEGIN_ARRAY"
    const val ERROR_SSL = "ssl"
    const val ERROR_HANDSHAKE = "handshake"
    const val ERROR_MALFORMED_RESPONSE = "Malformed Response"
    const val ERROR_SERVER_ERROR = "Server Error"
    const val ERROR_UNKNOWN = "Unknown Response"
    const val ERROR_FAILED_TO_CONNECT = "Failed to Connect to host \n Please try again"
    const val ERROR_FAILED_ON_CONNECTING = "Failed on Connecting to host \n Please try again"
    const val DOCUMENT = "document"
    const val ACCOUNT = "account"
    const val EMAIL = "email"
    const val PHONE = "phone"
    const val IMAGE = "image"
}
