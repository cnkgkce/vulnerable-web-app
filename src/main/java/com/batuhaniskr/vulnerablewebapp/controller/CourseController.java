package com.batuhaniskr.vulnerablewebapp.controller;

import com.batuhaniskr.vulnerablewebapp.model.Course;
import com.batuhaniskr.vulnerablewebapp.service.XmlService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourseController {

    private final XmlService xmlService;

    public CourseController(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    /**
     * Cross-site scripting issue
     * @param name
     * @return
     */
    @GetMapping("/name")
    public ResponseEntity<String> getCourse(@RequestParam("name") String name){
        return ResponseEntity.ok(name);
    }

    /**
     * xml external entity injection
     * @param name
     * @return
     */
    @GetMapping("/course-details")
    public ResponseEntity<String> createCourse(@RequestParam("url") String name){
        Course course = xmlService.parseCourse(name);
        return ResponseEntity.ok(course.getTitle());
    }


}
