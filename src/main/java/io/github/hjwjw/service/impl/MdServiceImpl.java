package io.github.hjwjw.service.impl;

import io.github.hjwjw.entity.Article;
import io.github.hjwjw.service.IMdService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
        File dir = new File(saveUrl);
        if (!dir.exists()){
            dir.mkdirs();
        }
        if (articleList!=null && articleList.size() >0){
            for (Article a : articleList) {
                File newDir = new File(dir,a.getCategories());
                if (!newDir.exists()){
                    newDir.mkdirs();
                }
                File newFile = new File(newDir,a.getTitle()+".md");
                try(FileWriter fw = new FileWriter(newFile)) {
                    fw.write("---\n");
                    fw.write("title: " + a.getTitle() + "\n");
                    fw.write("date: "+a.getCreateDate() + "\n");
                    fw.write("tags: "+a.getTags() + "\n");
                    fw.write("categories: "+a.getCategories() + "\n");
                    fw.write("---\n\n");
                    fw.write(a.getMarkdownContent()+"\n");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else {
            success = false;
        }
        return success;
    }
}