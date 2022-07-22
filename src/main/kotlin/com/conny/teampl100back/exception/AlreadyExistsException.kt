package com.conny.teampl100back.exception

import com.conny.teampl100back.infrastructure.model.exception.BaseException

class AlreadyExistsException(
    message: String? = ""
) : BaseException(message)