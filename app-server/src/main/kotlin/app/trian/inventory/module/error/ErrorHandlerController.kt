package app.trian.inventory.module.error

import org.hibernate.exception.ConstraintViolationException
import org.hibernate.exception.DataException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException

/**
 * Error handler controller
 * this controller for catch every error throw by defaul or custom
 * */
@RestControllerAdvice
class ErrorHandlerController {
    @ExceptionHandler(
        value = [ConstraintViolationException::class]
    )
    @ResponseStatus(
        HttpStatus.BAD_REQUEST,
    )
    fun validationError(
        error:ConstraintViolationException
    )= BaseResponse<List<Any>>(
        code = HttpStatus.BAD_REQUEST.value(),
        data = listOf(),
        message = error.message.toString()
    )

    @ExceptionHandler(
        value = [BadRequestException::class]
    )
    @ResponseStatus(
        HttpStatus.BAD_REQUEST
    )
    fun badRequest(
        error: BadRequestException
    )= BaseResponse<List<Any>>(
        code = HttpStatus.BAD_REQUEST.value(),
        data = listOf(),
        message = error.message.toString()
    )

    @ExceptionHandler(
        value = [DataNotFound::class]
    )
    @ResponseStatus(
        HttpStatus.NOT_FOUND
    )
    fun dataNotFound(
        error: DataNotFound
    )= BaseResponse<List<Any>>(
        code = HttpStatus.NOT_FOUND.value(),
        data = listOf(),
        message = error.message.toString()
    )

    @ExceptionHandler(
        value = [DataExist::class],
    )
    @ResponseStatus(
        HttpStatus.BAD_REQUEST
    )
    fun datExist(
        error:DataExist
    ) = BaseResponse<List<Any>>(
        code = HttpStatus.BAD_REQUEST.value(),
        data = listOf(),
        message = error.message.orEmpty()
    )

    @ExceptionHandler(
        value = [UnAuthorized::class]
    )
    @ResponseStatus(
        HttpStatus.UNAUTHORIZED
    )
    fun unAuthorized(
        error: UnAuthorized
    )= BaseResponse<List<Any>>(
        code = HttpStatus.UNAUTHORIZED.value(),
        data = listOf(),
        message = error.message.toString()
    )

    @ExceptionHandler(
        value = [HttpMediaTypeNotSupportedException::class]
    )
    @ResponseStatus(
        HttpStatus.BAD_REQUEST
    )
    fun mediaTypeNotSupported(
        error:HttpMediaTypeNotSupportedException
    )= BaseResponse<List<Any>>(
        code = HttpStatus.BAD_REQUEST.value(),
        data = listOf(),
        message = "${error.message}, Supported type = ${error.supportedMediaTypes}"
    )

    @ExceptionHandler(
        value = [HttpMessageNotReadableException::class]
    )
    @ResponseStatus(
        HttpStatus.BAD_REQUEST
    )
    fun mediaTypeJsonInvalid(
        error:HttpMessageNotReadableException
    )= BaseResponse<List<Any>>(
        code = HttpStatus.BAD_REQUEST.value(),
        data = listOf(),
        message = "Data given not valid",
        errorCode = 1
    )

    @ExceptionHandler(
        value = [HttpRequestMethodNotSupportedException::class]
    )
    @ResponseStatus(
        HttpStatus.METHOD_NOT_ALLOWED
    )
    fun methodNotAllowed(
        error:HttpRequestMethodNotSupportedException
    )= BaseResponse<List<Any>>(
        code = HttpStatus.METHOD_NOT_ALLOWED.value(),
        data = listOf(),
        message = "${error.message}",
        errorCode = 2
    )

    @ExceptionHandler(
        value = [MaxUploadSizeExceededException::class]
    )
    @ResponseStatus(
        HttpStatus.BAD_REQUEST
    )
    fun maximumFileUpload(
        error:MaxUploadSizeExceededException
    )= BaseResponse<List<Any>>(
        code = HttpStatus.BAD_REQUEST.value(),
        data = listOf(),
        message = error.message.toString(),
        errorCode = 90
    )
    @ExceptionHandler(
        value = [DataIntegrityViolationException::class]
    )
    @ResponseStatus(
        HttpStatus.BAD_REQUEST
    )
    fun sqlError(
        error:DataIntegrityViolationException
    )=BaseResponse<List<Any>>(
        code = HttpStatus.BAD_REQUEST.value(),
        data = listOf(),
        message = "${error.mostSpecificCause.message}",
        errorCode = 103
    )

    @ExceptionHandler(
        value = [DataException::class]
    )
    @ResponseStatus(
        HttpStatus.BAD_REQUEST
    )
    fun sqlError(
        error:DataException
    )=BaseResponse<List<Any>>(
        code = HttpStatus.BAD_REQUEST.value(),
        data = listOf(),
        message = error.message.toString(),
        errorCode = 104
    )


    @ExceptionHandler(
        value = [DuplicateException::class],
    )
    @ResponseStatus(
        HttpStatus.BAD_REQUEST
    )
    fun sqlError(
        error:DuplicateException
    )=BaseResponse<List<Any>>(
        code = HttpStatus.BAD_REQUEST.value(),
        data = listOf(),
        message = error.message.toString(),
        errorCode = 101
    )

    @ExceptionHandler(
        value = [NullPointerException::class]
    )
    @ResponseStatus(
        HttpStatus.BAD_REQUEST
    )
    fun nullPointer(e:NullPointerException)=
        BaseResponse<List<Any>>(
            code = HttpStatus.BAD_REQUEST.value(),
            data = listOf(),
            message = e.message.toString(),
            errorCode = 100
        )
}