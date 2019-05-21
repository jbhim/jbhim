package com.amcjt.jbhim.controller;

import com.amcjt.jbhim.config.UploadFilePathConfig;
import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.utils.enums.ResultEnum;
import com.amcjt.jbhim.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jbhim
 * @date 2019/5/21/021.
 */
@RestController
@RequestMapping("upload")
public class UploadController {
    @Resource
    private UploadFilePathConfig uploadFilePathConfig;

    @PostMapping("file")
    public ResultVO uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new JbhimException(ResultEnum.PARAM_ERROR);
        }
        String uploadFolder = uploadFilePathConfig.getUploadFolder();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        File target = new File(uploadFolder + format, file.getOriginalFilename());
        if (!target.getParentFile().exists()) {
            target.getParentFile().mkdirs();
        }
        try {
            file.transferTo(target);
        } catch (IOException e) {
            throw new JbhimException(ResultEnum.UPLOAD_FAIL);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("url", uploadFilePathConfig.getStaticAccessUrl() + format + "/" + file.getOriginalFilename());
        return ResultVO.success(result);
    }
}
