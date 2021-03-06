/**
 * Title: LoginController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.controller
 * @author Sharp
 * @date 2019-02-07 22:52:49
 */
package com.srct.service.tanya.common.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.exception.ServiceException;
import com.srct.service.service.CaptchaService;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.tanya.common.bo.user.UserLoginRespBO;
import com.srct.service.tanya.common.bo.user.UserRegReqBO;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.exception.NoSuchUserException;
import com.srct.service.tanya.common.service.SessionService;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.tanya.common.vo.UserInfoVO;
import com.srct.service.tanya.common.vo.UserRegReqVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.utils.security.MD5Util;
import com.srct.service.vo.TokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.srct.service.config.annotation.Auth.AuthType.GUEST;

/**
 * @author Sharp
 */
@Api(value = "登录操作", tags = "登录操作")
@RestController("LoginController")
@RequestMapping(value = "")
@CrossOrigin(origins = "*")
public class LoginController {

    final static private String tokenItem = "AUTH_TOKEN";
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private RedisTokenOperateService tokenService;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Auth
    @ApiOperation(value = "用户单点登录", notes = "用户登入系统，获取session信息, wechatCode 登录时若尚未注册则自动注册")
    @RequestMapping(value = "/sso", method = RequestMethod.POST)
    public void sso(@RequestParam(value = "token") String token) {
        Log.i("**********sso**********");
        UserInfo info = (UserInfo) request.getAttribute("user");
        String authToken = sessionService.genToken(info.getGuid());
        userService.ssoLogin(token, authToken);
    }

    @ApiOperation(value = "用户单点登录", notes = "用户登入系统，获取session信息, wechatCode 登录时若尚未注册则自动注册")
    @RequestMapping(value = "/sso", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<TokenVO>.Resp> ssoLogin(@RequestParam(value = "token") String token) {
        Log.i("**********sso-login**********");
        String authToken = (String) tokenService.getToken(token, tokenItem);
        String userName = "";
        String name = "";
        Log.i("authToken: {}", authToken);
        if (StringUtils.hasText(authToken)) {
            String guid = sessionService.getGuidByToken(authToken);
            UserInfo userInfo = userService.getUserbyGuid(guid);
            userName = userInfo.getUsername();
            name = userInfo.getName();
        }
        TokenVO res = TokenVO.builder().Token(authToken).userName(userName).name(name).build();
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "用户登入", notes = "用户登入系统，获取session信息, wechatCode 登录时若尚未注册则自动注册")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<TokenVO>.Resp> login(
            @RequestParam(value = "operator", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "graphCode", required = false) String code,
            @RequestParam(value = "token", required = false) String token,
            @RequestParam(value = "wechatcode", required = false) String wechatAuthCode) {

        Log.i("**********login**********");
        String authToken;
        UserLoginRespBO bo;
        if (wechatAuthCode != null) {
            try {
                bo = userService.login(wechatAuthCode);
            } catch (NoSuchUserException e) {
                bo = userService.regbyOpenId(e.getMessage());
            }
            userLoginRespBOValidate(bo);
            authToken = sessionService.genWechatToken(bo.getGuid());
        } else if (username != null && password != null && code != null && token != null) {
            captchaService.validateCaptcha(token, code);
            bo = userService.login(username, password);
            userLoginRespBOValidate(bo);
            authToken = sessionService.genToken(bo.getGuid());
        } else {
            throw new ServiceException("登录信息不足");
        }
        TokenVO res = TokenVO.builder().Token(authToken).userName(username).name(bo.getName()).build();

        return TanyaExceptionHandler.generateResponse(res);
    }

    private void userLoginRespBOValidate(UserLoginRespBO bo) {
        if (bo == null || bo.getGuid() == null) {
            throw new NoSuchUserException("");
        }
    }

    @Auth
    @ApiOperation(value = "用户登出", notes = "用户登出系统token将失效")
    @RequestMapping(value = "/logoff", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<TokenVO>.Resp> logout() {
        Log.i("**********logout**********");
        UserInfo info = (UserInfo) request.getAttribute("user");
        TokenVO res = new TokenVO();
        res.setUserName(info.getUsername());
        sessionService.logoffByGuid(info.getGuid());
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "用户注册", notes = "用户注册入系统，获取session信息")
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<TokenVO>.Resp> reg(
            @RequestParam(value = "name", required = false) String username,
            @RequestParam(value = "pw", required = false) String password,
            @RequestParam(value = "wechatcode", required = false) String wechatAuthCode) {

        Log.i("**********register**********");
        Log.i("name: {} pw: {}, wechat: {}", username, password, wechatAuthCode);

        UserLoginRespBO bo;
        String token;
        if (wechatAuthCode != null) {
            bo = userService.reg(wechatAuthCode);
            userLoginRespBOValidate(bo);
            token = sessionService.genWechatToken(bo.getGuid());
        } else if (username != null && password != null) {
            String hashedPassword = MD5Util.generate(password);
            bo = userService.reg(username, hashedPassword);
            userLoginRespBOValidate(bo);
            token = sessionService.genToken(bo.getGuid());
        } else {
            throw new ServiceException("注册信息不足");
        }

        TokenVO res = TokenVO.builder().Token(token).build();
        return TanyaExceptionHandler.generateResponse(res);
    }

    @Auth
    @ApiOperation(value = "更新用户信息", notes = "输入用户详细信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<String>.Resp> update(@RequestBody UserRegReqVO vo) {
        Log.i("**********update**********");
        String guid = (String) request.getAttribute("guid");
        Log.i(guid);
        UserRegReqBO bo = new UserRegReqBO();
        bo.setGuid(guid);
        BeanUtil.copyProperties(vo, bo);
        if (bo.getPassword() != null) {
            bo.setPassword(MD5Util.generate(bo.getPassword()));
        }
        userService.updateUser(bo);
        return TanyaExceptionHandler.generateResponse("");
    }

    @Auth(role = GUEST)
    @ApiOperation(value = "获取用户信息", notes = "获取用户详细信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<UserInfoVO>.Resp> info() {
        Log.i("**********info**********");

        UserInfoVO vo = new UserInfoVO();

        UserInfo info = (UserInfo) request.getAttribute("user");
        String guid = info.getGuid();
        BeanUtil.copyProperties(info, vo);
        try {
            RoleInfo role = (RoleInfo) request.getAttribute("role");
            Log.i("guid {} role {}", guid, role.getId());
            // Only one role
            vo.setRoleId(role.getId());
        } catch (Exception e) {
            Log.i("guid {} ", guid);
            Log.i(e.getMessage());
        }

        return TanyaExceptionHandler.generateResponse(vo);
    }

    @Auth
    @ApiOperation(value = "重置密码", notes = "向用户邮箱发送重置密码链接")
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<String>.Resp> resetReq() {
        Log.i("**********reset**********");
        String guid = (String) request.getAttribute("guid");
        UserInfo info = userService.getUserbyGuid(guid);
        if (info.getEmail() != null) {
            throw new ServiceException("没有注册邮箱");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(info.getEmail());
        message.setSubject("谭雅系统重置登录密码");
        String token = sessionService.getResetPasswordToken(info.getGuid());
        message.setText("点击此连接 重设密码\n https://api.tanyakeji.com/reset?req=" + token);
        mailSender.send(message);
        return TanyaExceptionHandler.generateResponse("");
    }

    @Auth
    @ApiOperation(value = "重置密码", notes = "向用户邮箱发送重置密码链接")
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<String>.Resp> reset(@RequestParam(value = "req") String token) {
        Log.i("**********reset**********");
        Log.i(token);
        String guid = sessionService.getGuidByResetPasswordToken(token);
        UserInfo info = userService.getUserbyGuid(guid);
        userService.cleanUserPassword(info);
        return TanyaExceptionHandler.generateResponse("");
    }

    @ApiOperation(value = "获取用户GUID二维码", notes = "获取用户详细信息")
    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public void qrcode(@RequestParam(value = "token", required = false) String token, HttpServletResponse response)
            throws IOException {
        Log.i("**********QR CODE**********");
        String guid = (String) request.getAttribute("guid");
        if (guid == null && token != null) {
            guid = sessionService.getGuidByToken(token);
        }
        Log.i(guid);
        ServletOutputStream stream = response.getOutputStream();
        if (guid != null) {
            try {
                Map<EncodeHintType, Object> hints = new HashMap<>();
                // 编码
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                // 边框距
                hints.put(EncodeHintType.MARGIN, 0);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bm = qrCodeWriter.encode(guid, BarcodeFormat.QR_CODE, 200, 200, hints);
                // 设置相应类型,告诉浏览器输出的内容为图片
                response.setContentType("image/png");
                MatrixToImageWriter.writeToStream(bm, "png", stream);
            } catch (WriterException e) {
                Log.e(e);
            } finally {
                if (stream != null) {
                    stream.flush();
                    stream.close();
                }
            }
        }
    }

}
