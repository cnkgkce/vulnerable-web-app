# vulnerable-web-app

This repo is developed for comparing codeql and fortify scan results.

## Vulnerable code results 
| Vulnerability Type | File:line | CodeQL | Fortify
| :---: | :---: | :---: | :---: | 
| XSS | CourseController:33 | :ballot_box_with_check: true positive | :x: false negative
| XXE | CourseController:43 | :ballot_box_with_check: true positive | :ballot_box_with_check: true positive
| XSS | hello.jsp:8 | :ballot_box_with_check: true negative |:ballot_box_with_check: true negative
| Path Manipulation | CourseController:67 | :ballot_box_with_check: true positive | :x: false negative
## Not vulnerable code results

| Vulnerability Type | File:line | CodeQL | Fortify |
| :---: | :---: | :---: | :---: |
| XXE | CourseController:54 | :ballot_box_with_check: true negative | :x: false positive


## Results 
<img width="678" alt="resim" src="https://user-images.githubusercontent.com/17202632/160772791-9694bcb4-2345-48fe-bfd2-a44345039bf1.png">

## Vulnerabilities

Cross-site scripting vulnerability due to user-provided value.

<img width="1047" alt="resim" src="https://user-images.githubusercontent.com/17202632/160774567-35964955-a6ae-4bbf-bb93-8c26bbb87fca.png">

Unsafe parsing of XML file from user input.

Parsing user-controlled XML reosurce

<img width="833" alt="resim" src="https://user-images.githubusercontent.com/17202632/160774850-f57e1c54-5d25-4c87-afda-af6f58cb2cd2.png">

<img width="767" alt="resim" src="https://user-images.githubusercontent.com/17202632/160774987-e3f41eed-475b-4742-9900-494c74751d04.png">

Cross-site scripting for jsp file

<img width="726" alt="resim" src="https://user-images.githubusercontent.com/17202632/160783737-3044ff8b-9cf2-47c6-8b60-64eb665be481.png">
<img width="446" alt="resim" src="https://user-images.githubusercontent.com/17202632/160783792-de4c94f0-1a84-424f-bdd7-55f888016e70.png">


