package app.trian.inventory.module.auth

import app.trian.inventory.v1.authentication.SignInRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {
    @GetMapping
    fun index() = "INI dari get"
    @PostMapping(
        value = [
            "/api/v1/sign-in-email"
        ],
        produces = [
            "application/json"
        ], consumes = [
            "application/json"
        ]
    )
    fun signInWithEmilAndPassword(
        @RequestBody signInRequest: SignInRequest
    ) = authenticationService.signInWithEmailAndPassword(signInRequest)

}