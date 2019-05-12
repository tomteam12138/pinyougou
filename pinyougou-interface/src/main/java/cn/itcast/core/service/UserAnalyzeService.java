package cn.itcast.core.service;

import vo.AnalyzeDate;

import java.util.List;

public interface UserAnalyzeService {
    void userAnalyze(String username);

    AnalyzeDate findAll();
}
