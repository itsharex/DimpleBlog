package com.dimple.blog.service.mapper;

import com.dimple.blog.service.entity.BlogComment;

import java.util.List;


/**
 * Mapper接口
 *
 * @author Dimple
 * @date 2023-02-13
 */
public interface BlogCommentMapper {
    /**
     * 查询
     *
     * @param id 主键
     * @return
     */
    BlogComment selectBlogCommentById(Long id);

    /**
     * 查询列表
     *
     * @param blogComment
     * @return 集合
     */
    List<BlogComment> selectBlogCommentList(BlogComment blogComment);

    /**
     * 修改
     *
     * @param blogComment
     * @return affected lines
     */
    int updateBlogComment(BlogComment blogComment);

    /**
     * 删除
     *
     * @param id 主键
     * @return affected lines
     */
    int deleteBlogCommentById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return affected lines
     */
    int deleteBlogCommentByIds(Long[] ids);
}
