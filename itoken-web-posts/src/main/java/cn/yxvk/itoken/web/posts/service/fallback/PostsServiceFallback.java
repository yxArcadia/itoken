package cn.yxvk.itoken.web.posts.service.fallback;

import cn.yxvk.itoken.common.hystrix.Fallback;
import cn.yxvk.itoken.web.posts.service.PostsService;
import org.springframework.stereotype.Component;

@Component
public class PostsServiceFallback implements PostsService {
    @Override
    public String page(int pageNum, int pageSize, String tbPostsPostJson) {
        return Fallback.badGetway();
    }

    @Override
    public String get(String postGuid) {
        return null;
    }

    @Override
    public String save(String tbPostsPostJson, String optsBy) {
        return Fallback.badGetway();
    }
}
