package io.github.hjwjw.service.impl;

import io.github.hjwjw.entity.Article;
import io.github.hjwjw.service.IMdService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.SimpleTimeZone;

/**
 * The MdServiceImpl class.
 *
 * @author HJW
 * @date 2018/10/16
 */
@Service
public class MdServiceImpl implements IMdService {
    @Override
    public Boolean writerMd(String saveUrl, List<Article> articleList) {
        Boolean success = true;
        DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File dir = new File(saveUrl);
        if (!dir.exists()){
            dir.mkdirs();
        }
        if (articleList!=null && articleList.size() >0){
            for (Article a : articleList) {
                String[] tags = a.getTags().split(",");
                StringBuffer preTags = new StringBuffer();
                preTags.append("tags: \n");
                for (String s : tags) {
                    preTags.append("     - ");
                    preTags.append(s);
                    preTags.append("\n");
                }
                File newDir = new File(dir,a.getCategories());
                if (!newDir.exists()){
                    newDir.mkdirs();
                }
                File newFile = new File(newDir,a.getTitle()+".md");
                try(FileWriter fw = new FileWriter(newFile)) {
                    fw.write("---\n");
                    fw.write("title: " + a.getTitle() + "\n");
                    fw.write("date: "+df.format(dateFormat.parse(a.getCreateDate())) + "\n");
                    fw.write("categories: "+a.getCategories() + "\n");
//                    fw.write("header-img: \"images/16-8-new-bg.jpg\"\n");
                    fw.write(preTags.toString());
                    fw.write("---\n\n");
                    fw.write(a.getMarkdownContent()+"\n");
                }catch (IOException e){
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }else {
            success = false;
        }
        return success;
    }
}
