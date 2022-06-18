package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping("/reg")
//    @ResponseBody // 表示此方法响应结果以json格式响应给前端
    public JsonResult<Void> reg(User user) {
        userService.reg(user);

        return new JsonResult<>(OK);
    }

//    @CrossOrigin(origins = "http://localhost:8080/login")
    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User user = userService.login(username, password);

        session.setAttribute("uid", user.getUid());
        session.setAttribute("username", user.getUsername());

        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        return new JsonResult<>(OK, user);
    }

    @RequestMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        userService.changePassword(uid, username, oldPassword, newPassword);

        return new JsonResult<>(OK);
    }

    @RequestMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getUidFromSession(session));

        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        userService.changeInfo(uid, username, user);

        return new JsonResult<>(OK);
    }

    private static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    private static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/gif");
        AVATAR_TYPE.add("image/bmp");
    }

    @RequestMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }

        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件大小超出限制");
        }

        String contentType = file.getContentType();

        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }

        // 上传的文件目录结构 .../upload/文件.png
        String parent = session.getServletContext().getRealPath("upload");

        // File对象指向这个路径，判断是否存在
        File dir = new File(parent);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 获取到文件名称，UUID生成新文件名
        String originalFileName = file.getOriginalFilename();

        System.out.println("OriginalFileName = " + originalFileName);

        int index = originalFileName.lastIndexOf(".");
        String suffix = originalFileName.substring(index);
        String fileName = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, fileName);

        try {
            file.transferTo(dest);
        } catch (FileStateException e) {
            throw new FileSizeException("文件状态异常");
        } catch (IOException e) {
            throw new FileIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatarName = "/upload/" + fileName;

        userService.changeAvatar(uid, avatarName, username);

        return new JsonResult<>(OK, avatarName);
    }
}
