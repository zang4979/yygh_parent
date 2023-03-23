package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.R;
import com.atguigu.yygh.common.handler.YyghException;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(produces = "医院设置接口")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "医院设置列表")
    @GetMapping("findAll")
    public R findAll() {
        try {
          //  int i = 10/0;
            List<HospitalSet> list = hospitalSetService.list();
            return R.ok().data("list", list);
        }catch (Exception e){
           throw new YyghException(20001,"系统异常");
        }

    }

    //删除
    @ApiOperation(value = "医院设置删除")
    @DeleteMapping("{id}")
    public R delHospSet(@PathVariable Long id) {
        boolean remove = hospitalSetService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "分页查询医院设置")
    @GetMapping("pageQuery/{page}/{limit}")
    public R page(@PathVariable Long page, @PathVariable Long limit) {
        Page<HospitalSet> pageParam = new Page<>(page, limit);
        hospitalSetService.page(pageParam);
        List<HospitalSet> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("records", records).data("total", total);
    }

    @ApiOperation(value = "根据id查询数据")
    @PostMapping("getHospSetById/{id}")
    public R getHospSetById(@PathVariable Long id) {
        HospitalSet byId = hospitalSetService.getById(id);
        return R.ok().data("byId", byId);
    }

    @ApiOperation(value = "修改设置")
    @PostMapping("updateHospSet")
    public R updateHospSet(@RequestBody HospitalSet hospitalSet) {
        boolean update = hospitalSetService.updateById(hospitalSet);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "批量删除")
    @DeleteMapping("batchRemove")
    public R batchRemove(@RequestBody List<Long> idList){
        boolean removeByIds = hospitalSetService.removeByIds(idList);
        if (removeByIds){
           return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation(value = "医院设置锁定和解锁")
    @PutMapping("lockHospSet/{id}/{status}")
    public R lockHospSet(@PathVariable Long id,
                         @PathVariable Integer status){
        //查询数据
        HospitalSet byId = hospitalSetService.getById(id);
        byId.setStatus(status);
        //修改数据
        boolean update = hospitalSetService.updateById(byId);
        if (update){
            return R.ok();
        }else {
            return R.error();
        }

    }
}
