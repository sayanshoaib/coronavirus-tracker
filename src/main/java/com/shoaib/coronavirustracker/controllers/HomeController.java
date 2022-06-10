package com.shoaib.coronavirustracker.controllers;

import com.shoaib.coronavirustracker.models.LocationStats;
import com.shoaib.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String Home(Model model) throws IOException, InterruptedException {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPreviousDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("localDateTime", LocalDateTime.now());

        return "home";
    }
}
