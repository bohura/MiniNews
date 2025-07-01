package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.TypeMapper;
import com.example.pojo.Type;
import com.example.service.TypeService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * タイプ情報関連のサービス実装クラス
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
        implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Result findAllTypes() {
        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);
    }
}
