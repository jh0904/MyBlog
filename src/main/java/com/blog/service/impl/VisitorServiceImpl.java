package com.blog.service.impl;

import com.blog.mapper.VisitorMapper;
import com.blog.service.VisitorService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: i_jianghao
 * @Date: 2018/6/16 16:21
 * Describe: 访客实现类
 */
@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    VisitorMapper visitorMapper;

    @Override
    public void addVisitorNumByPageName(String pageName, HttpServletRequest request) {

        String visitor;
        if("visitorVolume".equals(pageName)){
            visitor = (String) request.getSession().getAttribute("visitor");
            if(visitor == null){
                visitorMapper.updateVisitorNumByTotalVisitorAndPageName(pageName);
                request.getSession().setAttribute("visitor","yes");
            }else {
                visitorMapper.updateVisitorNumByTotalVisitor();
            }
        } else {
            visitor = (String) request.getSession().getAttribute(pageName);
            if(visitor == null){
                visitorMapper.updateVisitorNumByTotalVisitorAndPageName(pageName);
                request.getSession().setAttribute(pageName, "yes");
            } else {
                visitorMapper.updateVisitorNumByTotalVisitor();
            }
        }

    }

    @Override
    public JSONObject getVisitorNumByPageName(String pageName) {

        long totalVisitor = visitorMapper.getVisitorNumByPageName("totalVisitor");
        long pageVisitor = visitorMapper.getVisitorNumByPageName(pageName);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalVisitor", totalVisitor);
        jsonObject.put("pageVisitor", pageVisitor);
        return jsonObject;
    }

    @Override
    public long getNumByPageName(String pageName) {
        return visitorMapper.getVisitorNumByPageName(pageName);
    }

    @Override
    public void insertVisitorArticlePage(long articleId) {
        String pageName="findArticle?articleId="+articleId;
        visitorMapper.insertVisitorArticlePage(pageName,articleId);
    }

    @Override
    public long getAllVisitor() {
        return visitorMapper.getAllVisitor();
    }

    @Override
    public void deleteVisitorByArticleId(long articleId) {
        visitorMapper.deleteVisitorByArticleId(articleId);

    }

}
