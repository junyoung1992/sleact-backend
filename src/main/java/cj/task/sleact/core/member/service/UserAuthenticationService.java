package cj.task.sleact.core.member.service;

import cj.task.sleact.core.member.controller.dto.response.MemberInfoRes;
import cj.task.sleact.core.member.mapper.MemberMapper;
import cj.task.sleact.entity.Member;
import cj.task.sleact.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findOneByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(member.getEmail(), member.getPassword(),
                true, true, true, true,
                new ArrayList<>());
    }

    public MemberInfoRes loadMemberInfoByUsername(String username) {
        Member member = memberRepository.findOneByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return MemberMapper.INSTANCE.fromEntity(member);
    }

}
