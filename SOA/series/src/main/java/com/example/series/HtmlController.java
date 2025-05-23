package com.example.series;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HtmlController {

    @Autowired
    private SeriesRepository repository;

    @GetMapping("/")
    public String home() {
        return "home"; // home.html
    }

    @GetMapping("/series/add")
    public String showAddForm() {
        return "add"; // add.html
    }

    @PostMapping("/series")
    public String addSeriesFromForm(@ModelAttribute Series series) {
        repository.save(series);
        return "redirect:/series";
    }

    @GetMapping("/series")
    public String showSeriesList(Model model) {
        model.addAttribute("seriesList", repository.findAll());
        return "list"; // list.html
    }
}
