package per.scrumgun.domain.model.exception

import per.scrumgun.core.exception.BusinessException

class GetMessageException(message: String? = null) :
    BusinessException("Get Message Not Found $message")
