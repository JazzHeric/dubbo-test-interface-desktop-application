package service;

/**
 * @author: Jazz.Heric
 * @date: created in 2021/5/8 16:39
 * @description:
 */
public interface TestGenericService {


    /**
     * 单参数入参
     */
    String singleParam(User user);


    /**
     * 多参数入参
     */
    String multiParam(String id, User user);


    /**
     * 包含泛型类的入参
     */
    String genericParam(PageReq<User> userReq);

}
