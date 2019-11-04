package com.dimple.project.blog.service.impl;

import com.dimple.common.utils.ConvertUtils;
import com.dimple.common.utils.SecurityUtils;
import com.dimple.project.blog.domain.Blog;
import com.dimple.project.blog.domain.Comment;
import com.dimple.project.blog.mapper.BlogMapper;
import com.dimple.project.blog.mapper.CommentMapper;
import com.dimple.project.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @className: Blog
 * @description: 博客Service业务层处理
 * @author: Dimple
 * @date: 2019-10-28
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public Blog selectBlogById(Long id) {
        return blogMapper.selectBlogById(id);
    }

    @Override
    public List<Blog> selectBlogList(Blog blog) {
        List<Blog> blogList = blogMapper.selectBlogList(blog);
        if (blogList.isEmpty()) {
            return blogList;
        }
        List<Long> blogIdList = blogList.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<Comment> commentList = commentMapper.selectCommentListByPageId(blogIdList);
        for (Blog temp : blogList) {
            temp.setCommentList(commentList.stream().filter(e -> e.getPageId().equals(temp.getId())).collect(Collectors.toList()));
        }
        return blogList;
    }

    @Override
    public int insertBlog(Blog blog) {
        blog.setCreateBy(SecurityUtils.getUsername());
        return blogMapper.insertBlog(blog);
    }

    @Override
    public int updateBlog(Blog blog) {
        blog.setUpdateBy(SecurityUtils.getUsername());
        return blogMapper.updateBlog(blog);
    }

    @Override
    public int deleteBlogByIds(String ids) {
        String username = SecurityUtils.getUsername();
        return blogMapper.deleteBlogByIds(ConvertUtils.toStrArray(ids), username);
    }

    @Override
    public int deleteBlogById(Long id) {
        String username = SecurityUtils.getUsername();
        return blogMapper.deleteBlogById(id, username);
    }

    @Override
    public List<String> selectBlogTagList(String query) {
        List<String> tagList = blogMapper.selectBlogTagList(query);
        List<String> tags = new ArrayList<>();
        for (String s : tagList) {
            String[] strings = ConvertUtils.toStrArray(s);
            tags.addAll(Stream.of(strings).filter(e -> e.contains(query)).collect(Collectors.toList()));
        }
        return tags.stream().distinct().collect(Collectors.toList());
    }
}