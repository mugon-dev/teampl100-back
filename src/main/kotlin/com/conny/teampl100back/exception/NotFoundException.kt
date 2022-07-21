package com.conny.teampl100back.exception

import com.conny.teampl100back.infrastructure.model.exception.BaseException

class NotFoundException(message: String? = "Not found!") : BaseException(message)