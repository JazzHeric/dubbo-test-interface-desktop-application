package service;

import java.io.Serializable;

/**
 * @author: Jazz.Heric
 * @date: created in 2021/5/8 16:39
 * @description:
 */
public class User implements Serializable {


    private static final long serialVersionUID = 4741417427705290705L;

    private String name;

    private int gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
