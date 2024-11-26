package com.example.focus.opencv;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebCamController {

    @Autowired
    private WebCamService webCamService;

    @GetMapping("/capture")
    public String captureImage() {
        return webCamService.captureImage();
    }
}
