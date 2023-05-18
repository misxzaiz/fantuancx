package com.fantuancx.markdown.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("content")
public class MDController {

    @GetMapping("{name}")
    public String getContent(@PathVariable String name) {
        log.info("name:{}",name);
        // 获取Markdown文件的路径（示例）
        Path path = Paths.get("C:\\Users\\28409\\Desktop\\misxzaiz.github.io\\note\\"+name);
        // 读取Markdown文件内容
        byte[] contents = new byte[0];
        try {
            contents = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String markdown = new String(contents, StandardCharsets.UTF_8);

//        // 使用commonmark解析Markdown并转换成HTML
//        Parser parser = Parser.builder().build();
//        HtmlRenderer renderer = HtmlRenderer.builder().build();
//        String html = renderer.render(parser.parse(markdown));

        return markdown;
    }

}
