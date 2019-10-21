package club.agirl.web.blog.bean.yuque;

public class YuqueUrl {
    private static final String ID = ":id";
    private static final String BOOK_ID = ":bookId";

    private static final String YUQUE_DOMAIN = "https://www.yuque.com/api/v2";

    private static final String GET_TOKEN_USER = YUQUE_DOMAIN + "/user";

    private static final String FIND_USER_GROUP = YUQUE_DOMAIN + "/users/" + ID + "/groups";
    private static final String FIND_OPEN_GROUP = YUQUE_DOMAIN + "/groups";

    private static final String FIND_USER_BOOK = YUQUE_DOMAIN + "/users/" + ID + "/repos";
    private static final String FIND_GROUP_BOOK = YUQUE_DOMAIN + "/groups/" + ID + "/repos";

    private static final String GET_BOOK_TOC = YUQUE_DOMAIN + "/repos/" + ID + "/toc";

    private static final String FIND_BOOK_DOC = YUQUE_DOMAIN + "/repos/" + ID + "/docs";
    private static final String GET_DOC = YUQUE_DOMAIN + "/repos/" + BOOK_ID + "/docs/" + ID;

    public static String tokenUserUrl() {
        return GET_TOKEN_USER;
    }

    public static String findUserGroupUrl(Integer userId) {
        return replaceId(userId, FIND_USER_GROUP);
    }

    public static String findOpenGroupUrl() {
        return FIND_OPEN_GROUP;
    }

    public static String findUserBookUrl(Integer userId) {
        return replaceId(userId, FIND_USER_BOOK);
    }

    public static String findGroupBookUrl(Integer groupId) {
        return replaceId(groupId, FIND_GROUP_BOOK);
    }

    public static String getBookTocUrl(Integer bookId) {
        return replaceId(bookId, GET_BOOK_TOC);
    }

    public static String findBookDocUrl(Integer bookId) {
        return replaceId(bookId, FIND_BOOK_DOC);
    }

    public static String getDocUrl(Integer bookId, Integer docId) {
        return replaceBookId(bookId, replaceId(docId, GET_DOC));
    }

    private static String replaceId(Integer id, String url) {
        return url.replace(ID, id.toString());
    }

    private static String replaceBookId(Integer bookId, String url) {
        return url.replace(BOOK_ID, bookId.toString());
    }
}
