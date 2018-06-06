package com.zestinn.web.woolemon.wx;

import com.zestinn.web.woolemon.tools.Decript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping(value = "wechat")
public class WxServletAcceptController {

    private static final String TOKEN = "wasted_life";

    private static final String APP_ID = "wx99ef975a985504b0";

    private static final String APP_SECRET = "rxE49xZCySVcePdZ3TwttiRxMN6LEi0t3BRMGrgUueU";

    @RequestMapping(value = "/accept", method = RequestMethod.GET)
    @ResponseBody
    public void accept(
            HttpServletRequest request,
            HttpServletResponse response
    ){

        System.out.println("开始签名校验");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        ArrayList<String> array = new ArrayList<String>();
        array.add(signature);
        array.add(timestamp);
        array.add(nonce);

        //排序
        String sortString = sort(TOKEN, timestamp, nonce);
        //加密
        String mytoken = Decript.SHA1(sortString);
        //校验签名
        if (mytoken != null && mytoken != "" && mytoken.equals(signature)) {
            try {
                System.out.println("签名校验通过。");
                response.getWriter().println(echostr); //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("签名校验失败。");
        }
    }

    /**
     * 排序方法
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String sort(String token, String timestamp, String nonce) {
        String[] strArray = { token, timestamp, nonce };
        Arrays.sort(strArray);

        StringBuilder sbuilder = new StringBuilder();
        for (String str : strArray) {
            sbuilder.append(str);
        }

        return sbuilder.toString();
    }


}
