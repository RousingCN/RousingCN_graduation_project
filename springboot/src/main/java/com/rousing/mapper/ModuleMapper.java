package com.rousing.mapper;

import com.rousing.pojo.Module;
import com.rousing.pojo.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ModuleMapper {

    List<Module> selectModule(Module module);

    int selectModulesCount();
    List<Module> selectModulePage(Map<String, Object> map);

    int insertModule(Module module);

    int updateModule(Module module);
}
