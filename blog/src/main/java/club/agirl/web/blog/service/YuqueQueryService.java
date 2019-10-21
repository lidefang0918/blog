package club.agirl.web.blog.service;

import club.agirl.web.blog.bean.yuque.GroupBean;
import club.agirl.web.blog.bean.yuque.*;
import club.agirl.web.blog.controller.YuqueController;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface YuqueQueryService {

    Optional<UserBean> getCurrentUser();

    Integer getCurrentUserId();

    List<GroupBean> findAllGroups(Integer userId);

    List<GroupBean> findOpenGroups();

    List<BookBean> findUserBooks(Integer userId);

    List<BookBean> findGroupBooks(Integer groupId);

    List<TocBean> getBookToc(Integer bookId);

    List<DocBean> findBookAllDocs(Integer bookId);

    Optional<DocDetailBean> getDocDetail(Integer bookId, Integer docId);

    default List<BookBean> findGroupBooksSortByIdAcs(Integer groupId) {
        List<BookBean> groupBooks = findGroupBooks(groupId);
        // 重新按ID排序
        groupBooks.sort(Comparator.comparing(BaseBean::getId));
        return groupBooks;
    }
}
