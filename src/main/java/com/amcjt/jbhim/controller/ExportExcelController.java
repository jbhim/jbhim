package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/api/huaao")
public class ExportExcelController extends BaseController {
    @Autowired
    private DataService dataService;

    @GetMapping("/export")
    public ResponseEntity<?> exportExcel(String tableId, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode("表信息", "UTF-8"));
        response.getOutputStream().write(dataService.getTableData(tableId).toByteArray());
        return ResponseEntity.ok().build();
    }
}
