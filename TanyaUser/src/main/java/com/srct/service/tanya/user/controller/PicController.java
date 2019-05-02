/**
 * Title: PicController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaUser
 * @Package: com.srct.service.tanya.user.controller
 * @author sharuopeng
 * @date 2019-02-20 13:28:57
 */
package com.srct.service.tanya.user.controller;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.user.vo.UploadImgVO;
import com.srct.service.utils.HttpUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.utils.security.MD5Util;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sharuopeng
 */
@Api(value = "图片操作", tags = "图片操作")
@RestController("PicController")
@RequestMapping(value = "/img")
@CrossOrigin(origins = "*")
public class PicController {

    private static final String CONTENT =
            "感谢您对“国金所·天使守护计划”的捐助与支持，我们将遵照您的意愿，将您的捐助全部用于“国金所·天使守护计划”儿童救助事业，并对您的无私捐助和爱心表示诚挚的敬意!";
    private static String UPLOAD_PATH = "tanya/image";
    private static Font hanSansFont = null;
    @Autowired
    private HttpServletRequest request;

    private static Font getHanSansFont(int fontStyle, float fontSize) {

        if (hanSansFont == null) {
            try {
                DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
                InputStream inputStream =
                        resourceLoader.getResource("classpath:/SourceHanSansCN-Regular.otf").getInputStream();
                hanSansFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
        }
        return hanSansFont.deriveFont(fontSize);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<UploadImgVO>.Resp> uploadImage(MultipartFile image) {
        try {
            String name = image.getOriginalFilename();

            Log.i("file name before " + name);
            String fileName = MD5Util.MD5(name + HttpUtil.getRemoteIp(request) + (new Date()).toString());
            Log.i("file name after " + fileName);

            InputStream inputStream = image.getInputStream();
            Path directory = Paths.get(UPLOAD_PATH);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            long fileSize = Files.copy(inputStream, directory.resolve(fileName));
            UploadImgVO vo = new UploadImgVO();
            vo.setFileName(fileName);
            vo.setFileSize(fileSize);

            return TanyaExceptionHandler.generateResponse(vo);

        } catch (Exception e) {
            Log.e(e);
            throw new ServiceException("upload failed " + e.getMessage());
        }
    }

    // 使用流将图片输出
    @RequestMapping(value = "/{imageName}", method = RequestMethod.GET)
    public void getImage(HttpServletResponse response, @PathVariable("imageName") String imageName) throws IOException {
        response.setContentType("image/jpeg;charset=utf-8");
        response.setHeader("Content-Disposition", "inline; filename=girls.png");
        ServletOutputStream outputStream = response.getOutputStream();
        Log.i(Paths.get(UPLOAD_PATH).resolve(imageName).toString());
        outputStream.write(Files.readAllBytes(Paths.get(UPLOAD_PATH).resolve(imageName)));
        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping(value = "test/rewards.png", method = RequestMethod.GET)
    public void pic(@RequestParam(value = "name") String name, HttpServletResponse response) throws IOException {
        Log.i("**********PIC**********");
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        InputStream inputStream = resourceLoader.getResource("classpath:/rewards.png").getInputStream();

        ServletOutputStream stream = response.getOutputStream();

        try {
            BufferedImage image = ImageIO.read(inputStream);
            BufferedImage newImage = pressText(name, image);
            response.setContentType("image/png");
            ImageIO.write(newImage, "png", stream);
        } catch (Exception e) {
            Log.e(e);
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    private BufferedImage pressText(String pressText, BufferedImage input) {
        int imageW = input.getWidth();
        int imageH = input.getHeight();
        BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(input, 0, 0, imageW, imageH, null);
        g.setColor(new Color(122, 79, 27));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawTitle(pressText, 98, 412 + 37, g);
        // drawContentAutoNewline(CONTENT, 98 + 6, 482 + 33, g, 553);
        drawTimeAlignRight(new Date(), 99, 980 + 29, g, imageW);
        g.dispose();
        return image;
    }

    private void drawTitle(String name, int x, int y, Graphics g) {
        g.setColor(new Color(122, 79, 27));
        g.setFont(getHanSansFont(Font.PLAIN, 36));
        g.drawString(name + " 先生/女士", x, y);
    }

    private void drawContentAutoNewline(String content, int x, int y, Graphics g, int lineWidth) {
        String character;
        g.setFont(getHanSansFont(Font.PLAIN, 30));
        int characterWidth = g.getFontMetrics().stringWidth("中");
        int orgStringHeight = g.getFontMetrics().getHeight();
        int tempx = x + 2 * characterWidth;
        int tempy = y;
        while (content.length() > 0) {
            try {
                character = content.substring(0, 1);
            } catch (Exception e) {
                break;
            }
            content = content.substring(1);

            int delta = 0;
            if (g.getFontMetrics().stringWidth(character) < characterWidth) {
                delta = (characterWidth - g.getFontMetrics().stringWidth(character)) / 2;
            }
            tempx += delta;

            g.drawString(character, tempx, tempy);

            tempx = tempx + g.getFontMetrics().stringWidth(character) + delta;
            if (tempx >= lineWidth + x - characterWidth) {
                tempx = x;
                tempy = tempy + orgStringHeight + 17;
            }
        }
    }

    private void drawTimeAlignRight(Date date, int xx, int yy, Graphics g, int width) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String time = sdf.format(date);
        g.setColor(new Color(137, 102, 59));
        g.setFont(getHanSansFont(Font.PLAIN, 27));
        int x = width - g.getFontMetrics().stringWidth(time) - xx;
        int y = yy;
        g.drawString(time, x, y);
    }

}
