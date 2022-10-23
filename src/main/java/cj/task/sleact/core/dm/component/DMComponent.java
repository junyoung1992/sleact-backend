package cj.task.sleact.core.dm.component;

import cj.task.sleact.entity.Dm;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.repository.DMRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DMComponent {

    private final DMRepository dmRepository;

    public Dm post(Workspace workspace, User sender, User receiver, String content) {
        Dm post = Dm.createBuilder()
                .workspace(workspace)
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .build();
        dmRepository.save(post);
        return post;
    }

}
