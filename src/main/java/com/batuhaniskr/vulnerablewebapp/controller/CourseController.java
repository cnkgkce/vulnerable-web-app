package com.batuhaniskr.vulnerablewebapp.controller;

import com.batuhaniskr.vulnerablewebapp.config.ApplicationProperties;
import com.batuhaniskr.vulnerablewebapp.controller.request.Lecture;
import com.batuhaniskr.vulnerablewebapp.model.Course;
import com.batuhaniskr.vulnerablewebapp.service.XmlService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final XmlService xmlService;

    private final ApplicationProperties applicationProperties;

    public CourseController(XmlService xmlService, ApplicationProperties applicationProperties) {
        this.xmlService = xmlService;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Test for spring4Shell vulnerability
     * @param lecture
     * @return
     */
    @PostMapping("/lecture")
    public ResponseEntity<String> saveCourse(@RequestBody Lecture lecture){
        return ResponseEntity.ok("test");
    }

    /**
     * Cross-site scripting issue
     * @param name
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<String> getCourse(@PathVariable("id") Integer id, @RequestParam("name") String name){
        return ResponseEntity.ok(name);
    }

    /**
     * xml external entity injection
     * @param name
     * @return
     */
    @GetMapping("/details")
    public ResponseEntity<String> createCourse(@RequestParam("url") String name){
        Course course = xmlService.getCourse(name);
        return ResponseEntity.ok(course.getTitle());
    }

    /**
     * Reading local xml file and transform to html
     * @return
     */
    @GetMapping("/generate-html")
    public ResponseEntity<String> generateHtml() {
        xmlService.toHtml();
        return ResponseEntity.ok("ok");
    }

    /**
     * Path Manipulation issue
     */
    @GetMapping("/learning-file")
    public ResponseEntity<String> getLearning(@RequestParam("path") String path) throws IOException {
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
