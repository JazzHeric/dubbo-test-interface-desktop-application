package service;

/**
 * @author: Jazz.Heric
 * @date: created in 2021/5/8 16:39
 * @description:
 */
public class TestGenericServiceImpl implements TestGenericService{

    @Override
    public String singleParam(User user) {
        return null;
    }

    @Override
    public String multiParam(String id, User user) {
        return null;
    }

    @Override
    public String genericParam(PageReq<User> userReq) {
        return null;
    }
}
