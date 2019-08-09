package cn.yxvk.itoken.service.posts.controller;

import cn.yxvk.itoken.common.domain.TbPostsPost;
import cn.yxvk.itoken.common.dto.BaseResult;
import cn.yxvk.itoken.common.utils.MapperUtils;
import cn.yxvk.itoken.service.posts.service.PostsService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/posts")
public class PostsController {
    private static final Logger logger= LoggerFactory.getLogger(PostsController.class);
    @Autowired
    private PostsService<TbPostsPost> postPostsService;

    /**
     * 根据id查询
     * @param postGuid
     * @return
     */
    @RequestMapping(value = "{postGuid}",method = RequestMethod.GET)
    public BaseResult get(@PathVariable String postGuid){
        TbPostsPost tbPostsPost=new TbPostsPost();
        tbPostsPost.setPostGuid(postGuid);
        tbPostsPost = postPostsService.selectOne(tbPostsPost);
        return BaseResult.ok(tbPostsPost);
    }

    @RequestMapping(method = RequestMethod.POST)
    public BaseResult save(@RequestParam String tbPostsPostJson, @RequestParam String createBy){
        int result=0;
        TbPostsPost tbPostsPost=null;
        try {
            tbPostsPost= MapperUtils.json2pojo(tbPostsPostJson,TbPostsPost.class);
        } catch (Exception e) {
            logger.warn("JSON格式错误",e.getMessage());
            return BaseResult.notOk(Lists.newArrayList(new BaseResult.Error("保存文章失败","JSON格式错误")));
        }
        if (tbPostsPost!=null){
            //主键不为空，则修改
            if (StringUtils.isNotBlank(tbPostsPost.getPostGuid())){
                result= postPostsService.update(tbPostsPost,createBy);
            }else {
                //主键为空则插入
                result=postPostsService.insert(tbPostsPost,createBy);
            }
            if (result>0){
                return BaseResult.ok("保存文章成功");
            }
        }
        return BaseResult.ok("保存文章失败");
    }

    @ApiOperation("文章服务分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码" ,required = true,dataType = "int",paramType ="path" ),
            @ApiImplicitParam(name = "pageSize",value = "查询调试" ,required = true,dataType = "int",paramType ="path" ),
            @ApiImplicitParam(name = "tbPostsPostJson",value = "josn格式TbPostsPost对象" ,required = false,dataTypeClass = String.class,paramType ="json" )
    })
    @RequestMapping(value = "page/{pageNum}/{pageSize}",method = RequestMethod.GET)
    public BaseResult page(@PathVariable(required = true) int pageNum, @PathVariable(required = true) int pageSize,@RequestParam(required = false) String tbPostsPostJson){

        TbPostsPost tbPostsPost=null;
        if (StringUtils.isNotBlank(tbPostsPostJson)){
            try {
                tbPostsPost=MapperUtils.json2pojo(tbPostsPostJson,TbPostsPost.class);
            } catch (Exception e) {
                logger.warn("JSON格式错误",e.getMessage());
                return BaseResult.notOk(Lists.newArrayList(new BaseResult.Error("保存文章失败","JSON格式错误")));
            }
        }
        PageInfo pageInfo=postPostsService.page(pageNum,pageSize,tbPostsPost);
        List<TbPostsPost> TbPostsPostList=pageInfo.getList();

        BaseResult.Cursor cursor=new BaseResult.Cursor();
        cursor.setTotal((int)pageInfo.getTotal());
        cursor.setOffset(pageInfo.getPageNum());
        cursor.setLimit(pageInfo.getPageSize());
        return BaseResult.ok(TbPostsPostList,cursor);
    }
}
