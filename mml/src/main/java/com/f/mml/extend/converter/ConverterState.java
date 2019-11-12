package com.f.mml.extend.converter;

import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConverterState implements WriteConverter {

    @Override
    public String convert(Object value) throws ExcelKitWriteConverterException {
        if (value != null) {
            String type = String.valueOf(value);
            if ( "1".equals(type) ) {
                return "被锁定";
            }  else if ( "2".equals(type) ) {
                return "已处理";
            }  else if ( "9".equals(type) ) {
                return "自动消警";
            }
        }
        return "未处理";
    }

}
