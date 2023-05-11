package com.ltp.gradesubmission;

import java.util.ArrayList;
// import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GradeController {

    // List<Grade> studentGrades = Arrays.asList(
    //     new Grade("Harry", "Potions", "C-"),
    //     new Grade("Hermoine", "ad", "C-"),
    //     new Grade("Neville", "fd", "a-")
    // );

    List<Grade> studentGrades = new ArrayList<>();
    
    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) String id) {
        // Grade grade;
        // if(getGradeIndex(name) == -1000){
        //     grade = new Grade();
        // } else {
        //     grade = studentGrades.get(getGradeIndex(name));
        // }
        model.addAttribute("grade", getGradeIndex(id) == Constants.NOT_FOUND ? new Grade() : studentGrades.get(getGradeIndex(id)));
        return "form";
    }

    @PostMapping("/handleSubmit")
    public String submitForm(@Valid Grade grade, BindingResult result){
        System.out.println("Has errors ? : "+ result.hasErrors());
        if(result.hasErrors()) return "form";
        int index = getGradeIndex(grade.getId());
        if(index == Constants.NOT_FOUND){
            studentGrades.add(grade);
        } else {
            studentGrades.set(index, grade);
        }
        return "redirect:/grades";
    }

    @GetMapping("/grades")
    public String getGrades(Model model) {
        model.addAttribute("grades", studentGrades);
        return "grades";
    }


    public Integer getGradeIndex(String id) {
        for(int i=0; i<studentGrades.size(); i++){
            if(studentGrades.get(i).getId().equals(id)) return i;
        }
        return Constants.NOT_FOUND;
    }
}
