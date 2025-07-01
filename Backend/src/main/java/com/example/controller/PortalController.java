package com.example.controller;

import com.example.pojo.vo.PortalVo;
import com.example.service.HeadlineService;
import com.example.service.TypeService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ポータル画面のコントローラー
 */

@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private HeadlineService headlineService;

    // 全てのカテゴリを取得
    @GetMapping("findAllTypes")
    public Result findAllTypes() {
        Result result = typeService.findAllTypes();
        return result;
    }

    // ニュース一覧（ページング）
    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo) {
        Result result = headlineService.findNewsPage(portalVo);
        return result;
    }

    // ニュース詳細取得
    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid) {
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }

}
