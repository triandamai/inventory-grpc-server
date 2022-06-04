package app.trian.grpclearn.module.error

import io.grpc.Status
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler




@GrpcAdvice
class ErrorService {
    @GrpcExceptionHandler
    fun handleInvalidArgument(e: IllegalArgumentException?): Status? {
        return Status.INVALID_ARGUMENT.withDescription("Your description").withCause(e)
    }
    @GrpcExceptionHandler(DataNotFound::class)
    fun handleDatNaNotFound(e:DataNotFound):Status{
        return Status.NOT_FOUND.withDescription(e.message).withCause(e)
    }
    @GrpcExceptionHandler(UnAuthorized::class)
    fun handleUnAuthorized(e:UnAuthorized):Status{
        return Status.UNAUTHENTICATED.withDescription(e.message).withCause(e)
    }
}