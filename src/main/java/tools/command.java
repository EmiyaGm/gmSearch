package tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by gm on 2017/4/20.
 */
public class command {
    public static void exeCmd(String commandStr) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        String commandStr = "scrapy run 常熟理工学院 cslg -p 1 10 -f thread_filter";
        //String commandStr = "ipconfig";
        command.exeCmd(commandStr);
    }
}
