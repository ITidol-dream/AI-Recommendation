package com.shanzhu.tourism.controller.comments;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanzhu.tourism.domain.Result;
import com.shanzhu.tourism.domain.SysComments;
import com.shanzhu.tourism.domain.SysCommentsHotels;
import com.shanzhu.tourism.domain.User;
import com.shanzhu.tourism.enums.ResultCode;
import com.shanzhu.tourism.service.SysCommentsService;
import com.shanzhu.tourism.service.SysHotelsCommentsService;
import com.shanzhu.tourism.service.UserService;
import com.shanzhu.tourism.utils.JwtUtil;
import com.shanzhu.tourism.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评论controller
 *
 * @author: ShanZhu
 * @date: 2024-08-10
 */
@Controller
@ResponseBody
@RequestMapping("comments")
public class SysCommentsController {

    @Resource
    private SysCommentsService sysCommentsService;
    @Resource
    private SysHotelsCommentsService sysHotelsCommentsService;
    @Resource
    private UserService userService;

    /** 分页获取评论 */
    @PostMapping("getSysCommentsPage")
    public Result getSysCommentsPage(@RequestBody SysComments sysComments) {
        Page<SysComments> page = new Page<>(sysComments.getPageNumber(),sysComments.getPageSize());
        QueryWrapper<SysComments> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(sysComments.getContent()),SysComments::getContent,sysComments.getContent())
                .eq(StringUtils.isNotBlank(sysComments.getAttractionsId()),SysComments::getAttractionsId,sysComments.getAttractionsId())
                .eq(StringUtils.isNotBlank(sysComments.getUserId()),SysComments::getUserId,sysComments.getUserId())
                .eq(StringUtils.isNotBlank(sysComments.getAvatar()),SysComments::getAvatar,sysComments.getAvatar())
                .eq(StringUtils.isNotBlank(sysComments.getCreateBy()),SysComments::getCreateBy,sysComments.getCreateBy())
                .eq(sysComments.getCreateTime() != null,SysComments::getCreateTime,sysComments.getCreateTime())
                .eq(StringUtils.isNotBlank(sysComments.getUpdateBy()),SysComments::getUpdateBy,sysComments.getUpdateBy())
                .eq(sysComments.getUpdateTime() != null,SysComments::getUpdateTime,sysComments.getUpdateTime());
        Page<SysComments> sysCommentsPage = sysCommentsService.page(page, queryWrapper);
        return Result.success(sysCommentsPage);
    }

    @PostMapping("getSysHotelsCommentsPage")
    public Result getSysHotelsCommentsPage(@RequestBody SysCommentsHotels sysCommentsHotels) {
        Page<SysCommentsHotels> page = new Page<>(sysCommentsHotels.getPageNumber(),sysCommentsHotels.getPageSize());
        QueryWrapper<SysCommentsHotels> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(sysCommentsHotels.getContent()),SysCommentsHotels::getContent,sysCommentsHotels.getContent())
                .eq(StringUtils.isNotBlank(sysCommentsHotels.getHotelsId()),SysCommentsHotels::getHotelsId,sysCommentsHotels.getHotelsId())
                .eq(StringUtils.isNotBlank(sysCommentsHotels.getUserId()),SysCommentsHotels::getUserId,sysCommentsHotels.getUserId())
                .eq(StringUtils.isNotBlank(sysCommentsHotels.getAvatar()),SysCommentsHotels::getAvatar,sysCommentsHotels.getAvatar())
                .eq(StringUtils.isNotBlank(sysCommentsHotels.getCreateBy()),SysCommentsHotels::getCreateBy,sysCommentsHotels.getCreateBy())
                .eq(sysCommentsHotels.getCreateTime() != null,SysCommentsHotels::getCreateTime,sysCommentsHotels.getCreateTime())
                .eq(StringUtils.isNotBlank(sysCommentsHotels.getUpdateBy()),SysCommentsHotels::getUpdateBy,sysCommentsHotels.getUpdateBy())
                .eq(sysCommentsHotels.getUpdateTime() != null,SysCommentsHotels::getUpdateTime,sysCommentsHotels.getUpdateTime());
        Page<SysCommentsHotels> sysHotelsCommentsPage = sysHotelsCommentsService.page(page, queryWrapper);
        return Result.success(sysHotelsCommentsPage);
    }

    /** 根据id获取评论 */
    @GetMapping("getSysCommentsById")
    public Result getSysCommentsById(@RequestParam("id")String id) {
        SysComments sysComments = sysCommentsService.getById(id);
        return Result.success(sysComments);
    }

    /** 保存评论 */
    @PostMapping("saveSysComments")
    public Result saveSysComments(@RequestBody SysComments sysComments) {
        User user = userService.getById(TokenUtils.getUserIdByToken());
        sysComments.setAvatar(user.getAvatar());
        boolean save = sysCommentsService.save(sysComments);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }
    @PostMapping("saveSysHotelsComments")
    public Result saveSysHotelsComments(@RequestBody SysCommentsHotels sysCommentsHotels) {
        User user = userService.getById(TokenUtils.getUserIdByToken());
        sysCommentsHotels.setAvatar(user.getAvatar());
        boolean save = sysHotelsCommentsService.save(sysCommentsHotels);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑评论 */
    @PostMapping("editSysComments")
    public Result editSysComments(@RequestBody SysComments sysComments) {
        boolean save = sysCommentsService.updateById(sysComments);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除评论 */
    @GetMapping("removeSysComments")
    public Result removeSysComments(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                sysCommentsService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("评论id不能为空！");
        }
    }

}