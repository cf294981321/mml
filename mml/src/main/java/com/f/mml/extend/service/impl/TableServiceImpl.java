package com.f.mml.extend.service.impl;

import com.f.mml.extend.dao.TableMapper;
import com.f.mml.extend.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class TableServiceImpl implements TableService {

    @Resource
    private TableMapper tableMapper;

    @Override
    public void selectAndInsert() {
        List<String> list = tableMapper.getTableNames();
        for (String tname:list){
            System.out.println(tname);
            List<String> list2 = tableMapper.getColumnNames(tname);
            for (String cname:list2){
                try {
                    Integer exist = tableMapper.queryValueForColumn(tname,cname);
                    if(exist != null && exist > 0){
                        System.out.println("-------------------"+cname);
                        try {
                            tableMapper.insertResult(tname,cname);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}

