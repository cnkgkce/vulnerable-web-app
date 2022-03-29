package com.batuhaniskr.vulnerablewebapp.controller;

import com.batuhaniskr.vulnerablewebapp.config.ApplicationProperties;
import com.batuhaniskr.vulnerablewebapp.model.Course;
import com.batuhaniskr.vulnerablewebapp.service.XmlService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class CourseController {

    private final XmlService xmlService;

    private final ApplicationProperties applicationProperties;

    public CourseController(XmlService xmlService, ApplicationProperties applicationProperties) {
        this.xmlService = xmlService;
        this.applicationProperties = applicationProperties;
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

    /**
     * Path Manipulation issue
     */
    @GetMapping("/learning-file")
    public ResponseEntity<String> getLearning(@RequestParam("path") String path) throws IOException {
        System.out.println(applicationProperties.getPath());
        if (!path.contains((applicationProperties.getPath()))) {
            return ResponseEntity.badRequest().body("Invalid path name");
        }
        String message = "";
        try (InputStream in = Files.newInputStream(Path.of(path));
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                message += line;
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        return ResponseEntity.ok(message);
    }
}
