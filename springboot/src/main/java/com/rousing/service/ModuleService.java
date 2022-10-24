package com.rousing.service;

import com.rousing.mapper.ModuleMapper;
import com.rousing.pojo.Module;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ModuleService {
    @Resource
    ModuleMapper moduleMapper;

    public Module getOne(Module module) {
        List<Module> modules = moduleMapper.selectModule(module);
        if (modules.size() == 0) {
            return null;
        }
        return modules.get(0);
    }

    public List<Module> getRandom() {
        // 储存随机板块数据
        HashSet<Module> set = new HashSet<>();
        // 获取全部板块数据
        List<Module> modules = moduleMapper.selectModule(new Module());
        // 如果随机获取的板块数量不够10条则随机获取一条添加其中
        while (set.size() < 10 && set.size() != modules.size()) {
            int i = new Random().nextInt(modules.size());
            if (!modules.get(i).getModuleStatus().equals(Integer.getInteger(i + ""))) {
                set.add(modules.get(i));
            }
        }
        return new ArrayList<>(set);
    }

    public List<Module> getAll() {
        return moduleMapper.selectModule(new Module());
    }

    public int getCount() {
        return moduleMapper.selectModulesCount();
    }

    public List<Module> getPage(Map<String, Object> map) {
        return moduleMapper.selectModulePage(map);
    }

    public boolean moduleExist(String moduleName) {
        Module Module = new Module();
        Module.setModuleName(moduleName);
        return this.getOne(Module) != null;
    }

    public boolean addModule(Module module) {
        int i = moduleMapper.insertModule(module);
        return i >= 1;
    }

    public boolean updateModule(Module module) {
        int i = moduleMapper.updateModule(module);
        return i >= 1;
    }
}
