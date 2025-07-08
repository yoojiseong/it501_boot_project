package com.busanit501.boot_project.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//서버가 '데이터만' 전달함. http, body에 데이터를 담아서 전달함
@RestController
@Log4j2
//컨트롤러 작업 더 하고나서 뒤에가서 설명함.
public class SampleRestController {

    @GetMapping("/hiStr")
    public String[] hiStr(){
        log.info("SampleRestController에서 작업중.");
        return new String[]{"aaa","유지성","이민준","조채은"};
    }
}
