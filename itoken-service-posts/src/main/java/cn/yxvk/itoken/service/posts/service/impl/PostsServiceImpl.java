package cn.yxvk.itoken.service.posts.service.impl;

import cn.yxvk.itoken.common.domain.TbPostsPost;
import cn.yxvk.itoken.common.mapper.TbPostsPostMapper;
import cn.yxvk.itoken.common.service.impl.BaseServiceImpl;
import cn.yxvk.itoken.service.posts.service.PostsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostsServiceImpl extends BaseServiceImpl<TbPostsPost, TbPostsPostMapper> implements PostsService<TbPostsPost> {

}
