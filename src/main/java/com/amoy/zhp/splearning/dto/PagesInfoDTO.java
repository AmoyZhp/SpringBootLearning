package com.amoy.zhp.splearning.dto;

import com.amoy.zhp.splearning.model.Question;
import com.amoy.zhp.splearning.model.User;
import lombok.Data;

import java.util.List;

@Data
public class PagesInfoDTO {
    private List<QuestionDto> questionList;
    private List<Integer> pages;
    private Integer currentPage;
    private Integer totalPagesCount;
}
