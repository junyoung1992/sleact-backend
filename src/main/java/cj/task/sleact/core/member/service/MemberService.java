package cj.task.sleact.core.member.service;

import cj.task.sleact.common.enums.MemberRole;
import cj.task.sleact.core.member.controller.dto.request.SignUpReq;
import cj.task.sleact.entity.Member;
import cj.task.sleact.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void registerMember(SignUpReq request) {
        Optional<Member> findUser = memberRepository.findOneByEmail(request.getEmail());
        if (findUser.isPresent()) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        Member createdMember = Member.createBuilder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(MemberRole.user)
                .build();

        memberRepository.save(createdMember);
    }

}
