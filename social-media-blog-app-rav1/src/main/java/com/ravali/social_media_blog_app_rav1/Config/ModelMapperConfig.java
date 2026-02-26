package com.ravali.social_media_blog_app_rav1.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class ModelMapperConfig {

    /**
     * Creates a ModelMapper bean with the following configuration:
     * - setSkipNullEnabled(true): Skip null fields during mapping (PATCH requirement)
     * - setAmbiguityIgnored(true): Ignore ambiguous mappings
     *
     * @return Configured ModelMapper instance
     */

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setSkipNullEnabled(true) // Skip null fields during mapping (PATCH requirement)
                .setAmbiguityIgnored(true); // Ignore ambiguous mappings

        return  modelMapper;

    }
}
