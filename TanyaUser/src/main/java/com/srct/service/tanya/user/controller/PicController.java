/**
 * Title: PicController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaUser
 * @Package: com.srct.service.tanya.user.controller
 * @author sharuopeng
 * @date 2019-02-20 13:28:57
 */
package com.srct.service.tanya.user.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.utils.log.Log;

import io.swagger.annotations.Api;

/**
 * @author sharuopeng
 *
 */
@Api(value = "PicController")
@RestController("PicController")
@CrossOrigin(origins = "*")
public class PicController {

    private static final String CONTENT =
        "感谢您对“国金所·天使守护计划”的捐助与支持，我们将遵照您的意愿，将您的捐助全部用于“国金所·天使守护计划”儿童救助事业，并对您的无私捐助和爱心表示诚挚的敬意!";

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "test/rewards.png", method = RequestMethod.GET)
    public void pic(@RequestParam(value = "name", required = true) String name, HttpServletResponse response)
        throws IOException {
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
        Graphics g = image.createGraphics();
        g.drawImage(input, 0, 0, imageW, imageH, null);
        g.setColor(new Color(122, 79, 27));
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        String character = new String();
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
            content = content.substring(1, content.length());

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

    private static Font hanSansFont = null;

    private static Font getHanSansFont(int fontStyle, float fontSize) {

        if (hanSansFont == null) {
            try {
                DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
                InputStream inputStream =
                    resourceLoader.getResource("classpath:/SourceHanSansCN-Regular.otf").getInputStream();
                hanSansFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            } catch (FontFormatException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return hanSansFont.deriveFont(fontSize);
    }

}
