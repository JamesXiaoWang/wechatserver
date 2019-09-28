package com.zhijia.wechatserver.src.deviceserver.executor;
import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @Author: WangJiaPeng
 * @Date: 2019/4/3 10:08
 * @Version 1.0
 */
public class RemoteShellExecutor {
    private Connection conn;
    /** 远程机器IP */
    private String ip;
    /** 用户名 */
    private String osUsername;
    /** 密码 */
    private String password;
    private String charset = Charset.defaultCharset().toString();

    private static final int TIME_OUT = 1000 * 5 * 60;

    /**
     27       * 构造函数
     28       * @param ip
     29       * @param usr
     30       * @param pasword
     31       */
    public RemoteShellExecutor(String ip, String usr, String pasword) {
        this.ip = ip;
        this.osUsername = usr;
        this.password = pasword;
    }


    /**
     40      * 登录
     41      * @return
     42      * @throws IOException
     43      */
    private boolean login() throws IOException {
        conn = new Connection(ip);
        conn.connect();
        return conn.authenticateWithPassword(osUsername, password);
    }

    /**
     51      * 执行脚本
     52      *
     53      * @param cmds
     54      * @return
     55      * @throws Exception
     56      */
    public synchronized int exec(String cmds) throws Exception { InputStream stdOut = null;
        InputStream stdErr = null;
        String outStr = "";
        String outErr = "";
        int ret = -1;
        try {
            if (login()) {
                // Open TestPcmConverWav new {@link Session} on this connection
                Session session = conn.openSession();
                // Execute TestPcmConverWav command on the remote machine.
                session.execCommand(cmds);

                stdOut = new StreamGobbler(session.getStdout());
                outStr = processStream(stdOut, charset);

                stdErr = new StreamGobbler(session.getStderr());
                outErr = processStream(stdErr, charset);

                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);

                System.out.println("outStr=" + outStr);
                System.out.println("outErr=" + outErr);

                ret = session.getExitStatus();
            } else {
                throw new Exception("登录远程机器失败" + ip); // 自定义异常类 实现略
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            IOUtils.closeQuietly(stdOut);
            IOUtils.closeQuietly(stdErr);
        }
        return ret;
    }

    /**
     96      * @param in
     97      * @param charset
     98      * @return
     99      * @throws IOException
     100      * @throws UnsupportedEncodingException
     101      */
    private String processStream(InputStream in, String charset) throws Exception {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }

    public static void main(String args[]) throws Exception {
        String name = "6z".trim();
        String pwd = "6888c54545454";
        RemoteShellExecutor executor = new RemoteShellExecutor("47.106.172.221", "root", "Cszj1608");
        // 执行myTest.sh 参数为java Know dummy
        //去空格

        String tempUserName = name.replaceAll("", "");
        String tempPassWord = pwd.replaceAll("","");
        String openId = "oDIhFwFt46JO4QLVMzR0_Wof_HAs";

        String cmd = "/home/syn/testA.sh "+ tempUserName +"\t"+tempPassWord + "\t" + openId;
        System.out.println("cmd>>>>>>>>>>" + cmd);
        executor.exec(cmd);

    }
}
