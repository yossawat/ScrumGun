package per.scrumgun.domain.model.exception

import per.scrumgun.core.exception.BusinessException

class GetUserException(message: String? = null) : BusinessException("Get User Not Found $message")
