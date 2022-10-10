package cj.task.sleact.config.auth;

import cj.task.sleact.config.auth.dto.OAuthAttributes;
import cj.task.sleact.config.auth.dto.SessionUser;
import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.ChannelMember;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.entity.WorkspaceMember;
import cj.task.sleact.repository.ChannelMemberRepository;
import cj.task.sleact.repository.ChannelRepository;
import cj.task.sleact.repository.UserRepository;
import cj.task.sleact.repository.WorkspaceMemberRepository;
import cj.task.sleact.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;

    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분하는 코드. 여러 OAuth 서비스 연동시 사용.
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드값. 여러 OAuth 서비스 연동시 사용.
        String userNameAttribute = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담는 클래스.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttribute, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(
                new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        Optional<User> findUser = userRepository.findOneByEmail(attributes.getEmail())
                .map(u -> u.updateName(attributes.getName()));

        if (findUser.isPresent()) {
            return userRepository.save(findUser.get());
        }
        
        User user = attributes.toEntity();

        Workspace workspace = workspaceRepository.findById(1L).orElseThrow();
        WorkspaceMember defaultWorkspace = WorkspaceMember.createBuilder()
                .user(user)
                .workspace(workspace)
                .build();

        Channel channel = channelRepository.findById(2L).orElseThrow();
        ChannelMember defaultChannel = ChannelMember.createBuilder()
                .user(user)
                .channel(channel)
                .build();

        userRepository.save(user);
        workspaceMemberRepository.save(defaultWorkspace);
        channelMemberRepository.save(defaultChannel);

        return user;
    }

}
