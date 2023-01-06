package com.fineapple.everyanswerback.web.answerPosts;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPostsRepository;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPostsRepository;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnswerPostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnswerPostsRepository answerPostsRepository;

    @Autowired
    private DeptClassRepository deptClassRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private QuestionPostsRepository questionPostsRepository;


}