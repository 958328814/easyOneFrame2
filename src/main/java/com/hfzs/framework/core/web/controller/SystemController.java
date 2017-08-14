package com.hfzs.framework.core.web.controller;

import com.hfzs.framework.Constants;
import com.hfzs.framework.core.web.Servlets;
import com.hfzs.framework.domain.SysUser;
import com.hfzs.framework.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/sys")
public class SystemController {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @RequestMapping("/user/list")
    public ModelAndView userList(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request, org.springframework.ui.Model modelMap){
        Map<String, String[]> params = Servlets.getParamValuesMap(request, Constants.SEARCH_PREFIX);

        Page<SysUser> pagedList = sysUserService.findAll(params, pageable);
        ModelAndView modelAndView = new ModelAndView("/sys/user-list");
        modelAndView.addObject("pagedList",pagedList);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/user/list.json")
    public Page<SysUser> userList(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                 HttpServletRequest request){
        Map<String, String[]> params = null;
        Page<SysUser> pagedList = sysUserService.findAll(params, pageable);
        return pagedList;
    }

    @RequestMapping(value = "/user/{id:[0-9]+}")
    public ModelAndView views(@PathVariable Integer id, @RequestParam(defaultValue = "true") boolean isUpdate,
                      HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/sys/user").addObject("id",id);
    }
}
