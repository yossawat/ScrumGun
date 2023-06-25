package per.scrumgun.core.exception

/**
 * Base class for all exceptions in the domain layer.
 */
open class BusinessException : RuntimeException {
    constructor() : super()
    constructor(cause: String) : super(cause)
}
