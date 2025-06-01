package org.example.handandapi.config;

import org.example.handanddomain.domain.member.application.port.in.GetMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.ModifyMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.RegisterMemberUseCase;
import org.example.handanddomain.domain.post.application.port.in.ModifyPostUseCase;
import org.example.handanddomain.domain.post.application.port.in.RegisterPostUseCase;
import org.example.handanddomain.domain.post.application.port.in.RemovePostUseCase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@TestConfiguration
public class MockBeanConfig {

    @MockitoBean
    public RegisterMemberUseCase registerMemberUseCaseMockito;

    @MockitoBean
    public ModifyMemberUseCase modifyMemberUseCaseMockito;

    @MockitoBean
    public GetMemberUseCase getMemberUseCaseMockito;

    @MockitoBean
    public RegisterPostUseCase registerPostUseCaseMockito;

    @MockitoBean
    public ModifyPostUseCase modifyPostUseCaseMockito;

    @MockitoBean
    public RemovePostUseCase removePostUseCaseMockito;


    /*@Bean
    public RegisterMemberUseCase registerMemberUseCase() {
        return Mockito.mock(RegisterMemberUseCase.class);
    }

    @Bean
    public ModifyMemberUseCase modifyMemberUseCase() {
        return Mockito.mock(ModifyMemberUseCase.class);
    }

    @Bean
    public GetMemberUseCase getMemberUseCase() {
        return Mockito.mock(GetMemberUseCase.class);
    }

    @Bean
    public RegisterPostUseCase registerPostUseCase() {
        return Mockito.mock(RegisterPostUseCase.class);
    }

    @Bean
    public ModifyPostUseCase modifyPostUseCase() {
        return Mockito.mock(ModifyPostUseCase.class);
    }

    @Bean
    public RemovePostUseCase removePostUseCase() {
        return Mockito.mock(RemovePostUseCase.class);
    }
*/
}
