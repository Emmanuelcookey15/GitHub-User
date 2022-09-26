package com.emiko.githubuser.utils

import com.emiko.githubuser.utils.Constants.ERROR_BEGIN_ARRAY
import com.emiko.githubuser.utils.Constants.ERROR_BEGIN_OBJECT
import com.emiko.githubuser.utils.Constants.ERROR_FAILED_ON_CONNECTING
import com.emiko.githubuser.utils.Constants.ERROR_FAILED_TO_CONNECT
import com.emiko.githubuser.utils.Constants.ERROR_HANDSHAKE
import com.emiko.githubuser.utils.Constants.ERROR_MALFORMED_RESPONSE
import com.emiko.githubuser.utils.Constants.ERROR_SSL
import com.emiko.githubuser.utils.Constants.ERROR_UNKNOWN

fun userFriendlyResponse(error: Throwable): Throwable {
    return error.message?.let {
        if (it.contains(ERROR_BEGIN_OBJECT) || it.contains(ERROR_BEGIN_ARRAY)) {
            Throwable(ERROR_MALFORMED_RESPONSE)
        }else if (it.contains(ERROR_SSL, true)) {
            Throwable(ERROR_FAILED_TO_CONNECT)
        }else if (it.contains(ERROR_HANDSHAKE, true)) {
            Throwable(ERROR_FAILED_ON_CONNECTING)
        }else {
            error
        }
    } ?: run { Throwable(ERROR_UNKNOWN) }
}