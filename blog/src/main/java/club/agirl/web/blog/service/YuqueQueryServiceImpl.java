package club.agirl.web.blog.service;

import club.agirl.web.blog.bean.yuque.GroupBean;
import club.agirl.web.blog.bean.yuque.*;
import club.agirl.web.blog.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "yuque")
@Service
@Slf4j
public class YuqueQueryServiceImpl implements YuqueQueryService {

    private static final int DEFAULT_USER_ID = 236732;

    @Cacheable
    @Override
    public Optional<UserBean> getCurrentUser() {
        return HttpUtils.doGetOne(YuqueUrl.tokenUserUrl(), UserBean.class);
    }

    @Cacheable
    @Override
    public Integer getCurrentUserId() {
        return getCurrentUser().map(BaseBean::getId).orElse(DEFAULT_USER_ID);
    }

    @Cacheable
    @Override
    public List<GroupBean> findAllGroups(Integer userId) {
        return HttpUtils.doGetArray(YuqueUrl.findUserGroupUrl(userId), GroupBean.class);
    }

    @Cacheable
    @Override
    public List<GroupBean> findOpenGroups() {
        return HttpUtils.doGetArray(YuqueUrl.findOpenGroupUrl(), GroupBean.class);
    }

    @Cacheable
    @Override
    public List<BookBean> findUserBooks(Integer userId) {
        return HttpUtils.doGetArray(YuqueUrl.findUserBookUrl(userId), BookBean.class);
    }

    @Cacheable
    @Override
    public List<BookBean> findGroupBooks(Integer groupId) {
        return HttpUtils.doGetArray(YuqueUrl.findGroupBookUrl(groupId), BookBean.class);
    }

    @Cacheable
    @Override
    public List<TocBean> getBookToc(Integer bookId) {
        return HttpUtils.doGetArray(YuqueUrl.getBookTocUrl(bookId), TocBean.class);
    }

    @Cacheable
    @Override
    public List<DocBean> findBookAllDocs(Integer bookId) {
        return HttpUtils.doGetArray(YuqueUrl.findBookDocUrl(bookId), DocBean.class);
    }

    @Cacheable
    @Override
    public Optional<DocDetailBean> getDocDetail(Integer bookId, Integer docId) {
        return HttpUtils.doGetOne(YuqueUrl.getDocUrl(bookId, docId), DocDetailBean.class);
    }


}
