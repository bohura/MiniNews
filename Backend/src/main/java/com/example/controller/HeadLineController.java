package com.example.controller;

import com.example.pojo.Headline;
import com.example.service.HeadlineService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ヘッドライン管理コントローラー
 */

@RestController
@RequestMapping("headline")
@CrossOrigin
public class HeadLineController {

    @Autowired
    private HeadlineService headlineService;

    // ログイン後のみアクセス可能
    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline, @RequestHeader String token) {
        Result result = headlineService.publish(headline, token);
        return result;
    }

    // IDでヘッドラインを取得
    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid) {
        Headline headline = headlineService.getById(hid);
        Map data = new HashMap();
        data.put("headline", headline);
        return Result.ok(data);
    }

    // ヘッドライン更新
    @PostMapping("update")
    public Result update(@RequestBody Headline headline) {
        Result result = headlineService.updateData(headline);
        return result;
    }

    // IDで削除
    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid) {
        headlineService.removeById(hid);
        return Result.ok(null);
    }
}
