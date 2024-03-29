package app.trian.inventory.module.error

import io.grpc.Status
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Exception
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException


@GrpcAdvice
class ErrorService {
    @GrpcExceptionHandler
    fun handleInvalidArgument(e: IllegalArgumentException): Status {
        return Status.INVALID_ARGUMENT.withDescription("Your description").withCause(e)
    }
    @GrpcExceptionHandler(DataNotFound::class)
    fun handleDatNaNotFound(e: DataNotFound):Status{
        return Status.NOT_FOUND.withDescription(e.message).withCause(e)
    }
    @GrpcExceptionHandler(UnAuthorized::class)
    fun handleUnAuthorized(e: UnAuthorized):Status{
        return Status.UNAUTHENTICATED.withDescription(e.message).withCause(e)
    }
    @GrpcExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e:ConstraintViolationException):Status{
        return Status.INVALID_ARGUMENT.withDescription(e.message).withCause(e)
    }

    @GrpcExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(e: DataIntegrityViolationException):Status{
        return Status.ALREADY_EXISTS.withDescription(e.mostSpecificCause.message.orEmpty()).withCause(e)
    }

    @GrpcExceptionHandler(InvalidRequest::class)
    fun handleInvalidRequest(e: InvalidRequest):Status{
        return Status.ALREADY_EXISTS.withDescription(e.message.orEmpty()).withCause(e)
    }

    @GrpcExceptionHandler(
        Http2Exception::class
    )
    fun handleHttp2(e:Http2Exception):Status{
        return Status.INTERNAL.withDescription(e.message.orEmpty()).withCause(e)
    }
}