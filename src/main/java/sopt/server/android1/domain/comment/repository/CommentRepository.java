package sopt.server.android1.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.server.android1.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
