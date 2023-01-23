package app.trian.inventory.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter

@Configuration
class ApiConfig {
    @Bean
    fun protobufConverter():ProtobufJsonFormatHttpMessageConverter{
        return ProtobufJsonFormatHttpMessageConverter()
    }
}