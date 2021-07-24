package com.yhcy.aqc.controller.quiz;

import com.yhcy.aqc.service.quiz.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/problems")
public class QuizRestController {

    private final QuizService quizService;

    public QuizRestController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public int func() {
        return 1;
    }
}
